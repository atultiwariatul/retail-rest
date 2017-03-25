package com.atul.retail.rest;

import com.atul.retail.domain.product.Product;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;
import com.atul.retail.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by atiwa00 on 6/4/16.
 */
@RestController
@RequestMapping(value="/products")
public class RetailRestController {
    private static final XLogger logger = XLoggerFactory.getXLogger(RetailRestController.class);
    @Autowired
    ProductService productService;

    @RequestMapping(value="/{productId}", method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getProduct(@PathVariable String productId) {
        logger.info("Coming into getProduct {} ", productId);
        Product product = null;
        try {
            product = productService.findById(productId);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        }catch (RestClientException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        logger.info("Product {} ",product);

        return ResponseEntity.ok(product);
    }


    @ApiOperation(
            value = "Get all Products",
            response = Product.class
    )
    @RequestMapping(method= RequestMethod.GET,produces = {MediaType.APPLICATION_JSON})
     public ResponseEntity getProducts() {
        logger.info("Coming here in getProducts()");
        List<Product> products = null;
        try {
            products = productService.findAllProducts();
            logger.info("Products are :{} ",products);

        } catch (ServiceException e) {
            logger.error("Exception came",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        logger.info("Products are {}",products);
        return ResponseEntity.ok(products);
    }

    @RequestMapping(method=RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON})
    public ResponseEntity saveProduct(@RequestBody Product product) {
        logger.info("Coming here in saveProduct() {} ", product);
        try {
            productService.saveProduct(product);
        }catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);

        }

        logger.info("saved data successfully");
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value="/{productId}",method=RequestMethod.PUT,consumes = {MediaType.APPLICATION_JSON})
    public ResponseEntity updateProduct(@RequestBody Product product) {
        logger.info("Coming here in updateProduct() {} ",product);
        try {
            productService.updateProduct(product);
        } catch (ServiceException e) {
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        logger.info("saved data successfully");
        return ResponseEntity.ok(null);
    }
}