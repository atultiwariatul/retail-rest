package com.atul.retail.configs;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;


/**
 * Main Configuration class for all MongoDB related configuration. One place to have all
 * the configuration customization to Mongo
 *
 * @author atiwa00
 */
@Configuration
@PropertySource({"classpath:mongo-config.properties"})
@EnableAspectJAutoProxy(
        proxyTargetClass = true
)
@EnableMongoAuditing
@EnableMongoRepositories(basePackages={"com.atul.retail"})
@ComponentScan(basePackages = {"com.atul.retail","com.atul.merchant"})
public class MongoConfig extends AbstractMongoConfiguration {

    static private final Logger logger = Logger.getLogger(MongoConfig.class);
    /** Database name which we will keep flexible. Default will be Retail
     *  The reason to have this a separate property because this Configuration is
     *  Customized to used in a complex system where dynamic T
     */
    @Value("${db.name:Retail}")
    private String DB_NAME ;

    @Value("${mongouri}")
    private String mongodbUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Creating it manually to customize the MongoDBFactory. e.g. for this specific scenario
     * we want to set the Write Concern to FSYNCED because we want the ack of write only
     * after the write is written to disk.
     */
    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClient mongo = null;
        MongoClientURI connectionString = new MongoClientURI(mongodbUrl);
        try {
            mongo = new MongoClient(connectionString);
        } catch (UnknownHostException e) {
            throw new BeanCreationException(MongoConfig.class.getName(), "Host not found");
        }

        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, DB_NAME);
        factory.setWriteConcern(WriteConcern.SAFE);
        return factory;
    }

    public
    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(),
                mongoConverter());
        return mongoTemplate;
    }

    @Bean
    public MappingMongoConverter mongoConverter() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver,
                mappingContext);
        mongoConverter.setCustomConversions(customConversions());
        mongoConverter.afterPropertiesSet();
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mongoConverter;
    }

    /** For future use. If we want to write our custom mapping of DBObjects */
//    @Bean
//    public CustomConversions customConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
////		converters.add(new FutureReadConverter(encryptionProvider));
////		converters.add(new FutureWriteConverter(encryptionProvider));
//        return new CustomConversions(converters);
//    }

    /**
     * There was a bug in spring-mongodb due to which it was not able to pick the DAO classes
     * and hence we have to define the base package where we are defining all our DAOs.
     */
    @Override
    protected String getMappingBasePackage() {
        return "com.atul.retail";
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    /**
     * This method should never be used to get a MongoDatabase Factory, because
     * factory is already obtained.
     */
    @Override
    public Mongo mongo() throws Exception {
        MongoClientURI connectionString = new MongoClientURI(mongodbUrl);
        return new MongoClient(connectionString);
    }
}
