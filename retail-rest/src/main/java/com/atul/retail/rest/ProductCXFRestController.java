package com.atul.retail.rest;

import com.atul.retail.domain.product.Product;
import com.atul.retail.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;

@Path("/cxfproducts")
@Api(value = "InitiateCTProcessor", basePath = "/initiateCTProcessing")
public class ProductCXFRestController {
    private XLogger logger = XLoggerFactory.getXLogger(ProductCXFRestController.class);
    @Autowired
    private ProductService productService;
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "The identifier of the contract with which to track and bill the request.",
                    name = "ContractId",
                    paramType = "header",
                    dataType = "string",
                    required = true
            ),
            @ApiImplicitParam(
                    name = "RequestId",
                    value = "A globally unique identifier (preferably a random UUID) for the request.",
                    paramType = "header",
                    dataType = "string",
                    required = true
            ),
            @ApiImplicitParam(
                    name = "PreviousAttempts",
                    value = "The number of times this request has been attempted unsuccessfully.",
                    paramType = "header",
                    dataType = "integer",
                    required = false,
                    defaultValue = "0"
            )
    })
    public List<Product> listProducts(@PathParam("input") String input) {
        logger.info("Parameter passed from query string, {}",input);
        return productService.findAllProducts();
    }
}

