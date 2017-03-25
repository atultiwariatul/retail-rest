package com.atul.merchant.dao.impl;

import com.atul.merchant.dao.MerchantDAO;
import com.atul.retail.dao.impl.ProductDaoImpl;
import com.atul.merchant.domain.Merchant;
import com.atul.retail.exceptions.ErrorCode;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by atiwa00 on 6/5/16.
 */
@Repository
public class MerchantDaoImpl implements MerchantDAO{
    static private final XLogger logger = XLoggerFactory.getXLogger(ProductDaoImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Merchant getMerchantDetails(String merchantId) throws ServiceException {
        logger.entry(merchantId);
        Merchant merchant = null;
        try {
            merchant = mongoTemplate.findById(merchantId, Merchant.class);
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        if (merchant == null) {
            throw new ResourceNotFoundException(merchantId);
        }
        logger.exit(merchant);
        return merchant;
    }

    @Override
    public void saveMerchant(Merchant merchant) throws ServiceException {
        logger.entry(merchant);
        try {
            mongoTemplate.save(merchant);
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        logger.exit();
    }
}
