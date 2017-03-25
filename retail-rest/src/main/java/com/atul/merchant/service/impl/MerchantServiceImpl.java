package com.atul.merchant.service.impl;

import com.atul.merchant.dao.MerchantDAO;
import com.atul.merchant.domain.Merchant;
import com.atul.merchant.service.MerchantService;
import com.atul.retail.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by atiwa00 on 6/5/16.
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantDAO merchantDAO;

    @Override
    public Merchant getMerchantDetails(String merchantId) throws ServiceException {
        return merchantDAO.getMerchantDetails(merchantId);
    }

    @Override
    public void saveMerchant(Merchant merchant) throws ServiceException {
        merchantDAO.saveMerchant(merchant);
    }
}
