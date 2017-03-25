package com.atul.retail.dao.impl;

import com.atul.retail.dao.ProductDAO;
import com.atul.retail.domain.ListView;
import com.atul.retail.domain.SortOrder;
import com.atul.retail.domain.product.Product;
import com.atul.retail.exceptions.ErrorCode;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of ProductDAO.
 * Created by atiwa00 on 6/4/16.
 * TODO: Exception Wrapping is not looking good write now. See If I can do something with-in the time limit.
 */
@Repository
public class ProductDaoImpl implements ProductDAO {


    private static final String PRODUCT_COLLECTION = Product.COLLECTION_LINE_ITEM_SUFFIX;

    static private final XLogger logger = XLoggerFactory
            .getXLogger(ProductDaoImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Product findById(String productId) throws ServiceException {
        logger.entry(productId);
        Product product = null;
        try {
            product = mongoTemplate.findById(productId, Product.class, PRODUCT_COLLECTION);
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        if (product == null) {
            throw new ResourceNotFoundException(productId);
        }
        logger.exit(product);
        return product;
    }

    /**
     * @param products
     * @throws ServiceException
     */
    @Override
    public void saveProducts(List<Product> products) throws ServiceException {
        logger.entry(products);

        try {
            mongoTemplate.insert(products, PRODUCT_COLLECTION);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage(),e);
            throw new DuplicateKeyException(ErrorCode.PRODUCT_ALREADY_EXISTS.getCode(), e);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        logger.exit();
    }

    @Override
    public void saveProduct(Product product) throws ServiceException {
        logger.entry(product);
        try {
            mongoTemplate.insert(product, PRODUCT_COLLECTION);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.PRODUCT_ALREADY_EXISTS.getCode(),ErrorCode.PRODUCT_ALREADY_EXISTS.getDescription());
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        logger.exit();
    }

    @Override
    public List<Product> findAllProducts() throws ServiceException {
        List<Product> list = null;
        logger.entry();

        try {
            list = mongoTemplate.findAll(Product.class, PRODUCT_COLLECTION);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        logger.debug("Printing all the Documents {} ", list);
        logger.exit(list);
        return list;
    }

    @Override
    public ListView<Product> queryByCriteria(Map<String, String> paramsToQuery, String sortKey, SortOrder sortOrder) throws ServiceException {
        logger.entry(paramsToQuery,sortKey,sortOrder);
        Query query = createBasicQuery(paramsToQuery, sortKey, sortOrder);
        ListView<Product> listToReturn = new ListView<>();
        listToReturn.setTotalCount(countWithCriteria(query).intValue());
        listToReturn.setCurrentResults(findWithCriteria(query));
        logger.exit(listToReturn);
        return listToReturn;
    }

    @Override
    public void updateProduct(Product product) throws ServiceException {
        logger.entry(product);
        Product productToUpdate = findById(product.getProductId());
        logger.debug("Fetched Product by Id {}",product);
        Product verifiedProduct = new ProductObjectConvert(product,productToUpdate).checkTopLevelNull().checkFirstLevelNull();
        logger.debug("Product after Null check verification {} ",verifiedProduct);
        try {
            mongoTemplate.save(verifiedProduct, PRODUCT_COLLECTION);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        logger.exit();
    }

    private static class ProductObjectConvert{
        private Product request;
        private Product dbObject;

        public ProductObjectConvert(Product request, Product dbObject) {
            this.request = request;
            this.dbObject = dbObject;
        }

        public ProductObjectConvert checkTopLevelNull(){
            if (dbObject.getCategory()==null ) dbObject.setCategory(request.getCategory());
            if (dbObject.getDetails()==null ) dbObject.setDetails(request.getDetails());
            if (dbObject.getDimension()==null) dbObject.setDimension(request.getDimension());
            if (dbObject.getMerchantId()==null) dbObject.setMerchantId(request.getMerchantId());
            if (dbObject.getTags()==null ) dbObject.setTags(request.getTags());
            if (dbObject.getShipping()==null ) dbObject.setShipping(request.getShipping());
            if (dbObject.getDisplayName()==null ) dbObject.setDisplayName(request.getDisplayName());
            if (dbObject.getPricing()==null ) dbObject.setPricing(request.getPricing());
            return this;
        }

        public Product checkFirstLevelNull(){

            if (dbObject.getCategory()!=null ) {
                if(request.getCategory().getCategoryId()!=null){
                    dbObject.getCategory().setCategoryId(request.getCategory().getCategoryId());
                }
                if(request.getCategory().getCategoryName()!=null){
                    dbObject.getCategory().setCategoryName(request.getCategory().getCategoryName());
                }
                if(request.getCategory().getDescription()!=null){
                    dbObject.getCategory().setDescription(request.getCategory().getDescription());
                }

            }
            if (dbObject.getDetails()!=null ) {
                if (request.getDetails().getImageUrl()!=null){
                    dbObject.getDetails().setImageUrl(request.getDetails().getImageUrl());
                }
                if (request.getDetails().getDescription()!=null){
                    dbObject.getDetails().setDescription(request.getDetails().getDescription());
                }
            }
            if (dbObject.getDimension()!=null) {
                if (request.getDimension().getHeight()<0){
                    dbObject.getDimension().setHeight(request.getDimension().getHeight());
                }
                if (request.getDimension().getLength()<0){
                    dbObject.getDimension().setLength(request.getDimension().getLength());
                }
                if (request.getDimension().getHeight()<0){
                    dbObject.getDimension().setHeight(request.getDimension().getHeight());
                }
                if (request.getDimension().getWidth()<0){
                    dbObject.getDimension().setWidth(request.getDimension().getWidth());
                }
                if (request.getDimension().getUnit()!=null){
                    dbObject.getDimension().setUnit(request.getDimension().getUnit());
                }
                if (request.getDimension().getWeight()<0){
                    dbObject.getDimension().setWeight(request.getDimension().getWeight());
                }
            }

            if (dbObject.getShipping()!=null ) {
                if (request.getShipping().getCapacity()!=null){
                    dbObject.getShipping().setCapacity(request.getShipping().getCapacity());
                }
            }

            if (dbObject.getPricing()!=null ) {
                if (request.getPricing().getCurrency()!=null){
                    dbObject.getPricing().setCurrency(request.getPricing().getCurrency());
                }
                if (request.getPricing().getCurrentPrice()<0){
                    dbObject.getPricing().setCurrentPrice(request.getPricing().getCurrentPrice());
                }
                if (request.getPricing().getDiscountPercentage()!=null){
                    dbObject.getPricing().setDiscountPercentage(request.getPricing().getDiscountPercentage());
                }
                if (request.getPricing().getListPrice()<0){
                    dbObject.getPricing().setListPrice(request.getPricing().getListPrice());
                }
            }

            return dbObject;
        }
    }

    private List<Product> findWithCriteria(Query query) throws ServiceException {

        try {
            return mongoTemplate.find(query, Product.class, PRODUCT_COLLECTION);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
    }

    private Long countWithCriteria(Query query) throws ServiceException {
        logger.entry(query);
        try {
            return mongoTemplate.count(query, Product.class, PRODUCT_COLLECTION);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
    }

    private Query createBasicQuery(Map<String, String> paramsToQuery, String sortKey,
                                   SortOrder sortOrder) {
        Query query = new Query();
        for (Map.Entry<String, String> criteria : paramsToQuery.entrySet()) {
            query.addCriteria(Criteria.where(criteria.getKey()).in(criteria.getValue()));
        }
        addSorting(sortKey, sortOrder, query);
        return query;
    }

    public void addSorting(String sortKey, SortOrder sortOrder, Query query) {
        if ((sortKey != null) && (sortKey.trim().length() > 0)) {
            query.with(new Sort(SortOrder.ASC == sortOrder ? Sort.Direction.ASC : Sort.Direction.DESC, sortKey));
        }
    }
}
