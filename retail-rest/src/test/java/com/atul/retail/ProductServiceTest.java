package com.atul.retail;

import com.atul.retail.configs.MongoConfig;
import com.atul.retail.domain.ListView;
import com.atul.retail.domain.SortOrder;
import com.atul.retail.domain.product.Product;
import com.atul.retail.exceptions.ServiceException;
import com.atul.retail.service.ProductService;
import com.mongodb.DuplicateKeyException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
@SpringBootTest
public class ProductServiceTest {
    /**
     * Declaration of logger for Class.
     */
    static private final XLogger logger = XLoggerFactory
            .getXLogger(ProductServiceTest.class);

    @Autowired
    ProductService productService;

    private final String productId = "5754fe043004f645d6e95db7";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.info("Coming in setup before class");
    }

    @Test
    public void testFindProductById() {
        Product product = null;
        try {
            product = productService.findById(productId);
            logger.info("Product Object before setting Merchant {} ",product);
            Assert.notNull(product);
        } catch (ServiceException e) {
            logger.catching(e);
        }
    }

    @Test
    public void testFindProductById_RestClientException() {
        try {
            productService.findById(productId);
            throw new RestClientException("afdasf");
        } catch (ServiceException e) {
            logger.catching(e);
        }
    }

    @Test
    public void testSaveProduct() {
        logger.entry();
        try {
            Product product = new ProductDataFactory().getProduct();
            productService.saveProduct(product);
        }catch (DuplicateKeyException exception){
            logger.error("This product is already saved {} ",exception.getErrorMessage());
        }
        logger.exit();
    }

    @Test
    public void testFindAllProducts() {
        List<Product> items = null;
        try {
            items = productService.findAllProducts();
            Assert.notNull(items);
        } catch (ServiceException e) {
            logger.catching(e);
        }
        logger.info("Printing all the Documents {} ", items);
    }

    /**
     * This method is made to query Products from mongodb by some custom criteria.
     */
    //TODO: Not properly implemented due to time constraint.
    @Test
    public void testQueryByCriteria() {
        logger.debug("Going to get all the Query by Criteria");
        Map<String, String> paramsToQuery = new HashMap<>();
        //        paramsToQuery.put("price.discount", "10");
        String sortKey = "productId";
        ListView<Product> list = null;
        try {
            list = productService.queryByCriteria(
                    paramsToQuery, sortKey, SortOrder.DESC);
            logger.info("Got the list size after query by criteria {}", list.getTotalCount());
            Assert.notNull(list);

            logger.info("Count is:" + list.getTotalCount());
            logger.info("Current list size is:" + list.getCurrentResults().size());
        } catch (ServiceException e) {
            logger.catching(e);
        }
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateProduct() {
        throw new UnsupportedOperationException();
    }
}
