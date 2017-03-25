package com.atul.retail.configs;

import com.atul.retail.rest.ProductCXFRestController;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.spring.JaxRsConfig;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Main Configuration class which will work for both SpringBoot starting point as well as a
 * single configuration class to add all your Configuration classes at one places, so that
 * This application become very easy to configure.
 * Created by atiwa00 on 6/4/16.
 */
@SpringBootApplication
@Configuration
@EnableMongoRepositories(basePackages = {"com.atul.retail"})
@EnableRetry
@Import(value = {JaxRsConfig.class, MongoConfig.class})
public class RetailApplicationConfig extends SpringBootServletInitializer {

    private final String CXF_BASE_PATH = "/cxf/*";
    public static void main(String[] args) {
        SpringApplication.run(RetailApplicationConfig.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RetailApplicationConfig.class);
    }

    /**
     *
     * NOT USED
     *
     * Injection of CXFServlet which is an Apache Implementation of JAX-RS implementation
     * so that all the exposed services will be handled by CXF.
     * CXFServlet will handle all request which will match pattern cxf/*
     * @param context
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        return new ServletRegistrationBean(new CXFServlet(), CXF_BASE_PATH);
    }

    /**
     *
     * NOT USED
     * Register all your CXF Implementation of JAX-RS here which we want to expose as a service.
     *
     * @return
     */
    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setServiceBean(new ProductCXFRestController());
//        endpoint.se
        endpoint.setServiceBeanObjects(new ProductCXFRestController());
        endpoint.setAddress("/");
        return endpoint.create();
    }
}