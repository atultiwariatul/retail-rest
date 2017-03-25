package com.atul.merchant.dao;

import com.atul.merchant.domain.Merchant;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;

/**
 * Interface to expose merchant related operations.
 * Created by atiwa00 on 6/5/16.
 */
public interface MerchantDAO {

    Merchant getMerchantDetails(String merchantId) throws ResourceNotFoundException,ServiceException;

    void saveMerchant(Merchant merchant) throws ServiceException;

}
