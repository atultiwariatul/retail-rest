package com.atul.merchant.service;

import com.atul.merchant.domain.Merchant;
import com.atul.retail.exceptions.ServiceException;

/**
 * Created by atiwa00 on 6/5/16.
 */
public interface MerchantService {

    Merchant getMerchantDetails(String merchantId) throws ServiceException;

    void saveMerchant(Merchant merchant) throws ServiceException;

}
