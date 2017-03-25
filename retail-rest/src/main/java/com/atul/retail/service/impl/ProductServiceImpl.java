package com.atul.retail.service.impl;

import com.atul.merchant.domain.Merchant;
import com.atul.retail.dao.ProductDAO;
import com.atul.retail.domain.ListView;
import com.atul.retail.domain.SortOrder;
import com.atul.retail.domain.product.Product;
import com.atul.retail.exceptions.ErrorCode;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;
import com.atul.retail.service.ProductService;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by atiwa00 on 6/5/16.
 */
@Service
public class ProductServiceImpl implements ProductService {

    static private final XLogger logger = XLoggerFactory
            .getXLogger(ProductServiceImpl.class);
    @Autowired
    private ProductDAO productDAO;

    private RestTemplate restTemplate =new RestTemplate();

    @Value("${rest.merchant.url}")
    private String merchantServiceUrl;

    @Retryable(value={RestClientException.class},maxAttempts = 5, backoff = @Backoff(delay = 2000))
    @Override
    public Product findById(String productId) throws ServiceException,RestClientException {
        logger.info("Going to get product id{}",productId);
        Product product = null;
        try {
            product = productDAO.findById(productId);
        }catch (ResourceNotFoundException e){
            logger.error("Resource not found caught:",e.errorResponse.additionalInfo);
            throw e;
        }

        if (product.getMerchantId()!=null) {
            Merchant merchant = getMerchantFromRestService(product.getMerchantId());
            product.setMerchant(merchant);
        }
        return product;
    }

    private Merchant getMerchantFromRestService(String merchantId) throws RestClientException{
        Merchant merchant = null;
        try {
            merchant =
                    restTemplate.getForObject(merchantServiceUrl + merchantId, Merchant.class);
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(merchantId);
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.MERCHANT_SERVICE_UNAVAILABLE.getCode(),ErrorCode.MERCHANT_SERVICE_UNAVAILABLE.getDescription());
        }
        return merchant;
    }

    @Override
    public void saveProducts(List<Product> products) throws ServiceException {
        productDAO.saveProducts(products);
    }

    @Override
    public void saveProduct(Product product) throws ServiceException {
        productDAO.saveProduct(product);
    }

    @Override
    public List<Product> findAllProducts() throws ServiceException {
        return productDAO.findAllProducts();
    }

    @Override
    public ListView<Product> queryByCriteria( Map<String, String> paramsToQuery, String sortKey, SortOrder sortOrder) throws ServiceException {
        return productDAO.queryByCriteria(paramsToQuery,sortKey,sortOrder);
    }

    @Override
    public void updateProduct(Product product) throws ServiceException {
        productDAO.updateProduct(product);
    }
}
