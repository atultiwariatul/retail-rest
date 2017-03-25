package com.atul.merchant.rest;

import com.atul.merchant.domain.Merchant;
import com.atul.merchant.service.MerchantService;
import com.atul.retail.exceptions.ResourceNotFoundException;
import com.atul.retail.exceptions.ServiceException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

/**
 * This Merchant Rest Controller will pretend Another Web-Service which is hosted by
 * http://merchant.api.target.com/merchant/{Id} because It only expose function to get the Merchant
 * Detail because no External API/World should interact Category API to modify Merchant.
 * For Any Modification or New Merchant Creation there will be a Merchant Management System.
 *
 * Created by atiwa00 on 6/4/16.
 */
@RestController
@RequestMapping(value="/merchants")
public class MerchantRestController {
    private static final XLogger logger = XLoggerFactory.getXLogger(MerchantRestController.class);
    @Autowired
    MerchantService merchantService;

    @RequestMapping(value="/{merchantId}",method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getProduct(@PathVariable String merchantId) {
        logger.info("Going to fetch the Merchant Details for merchant Id {} ", merchantId);
        Merchant merchant = null;
        try {
            merchant = merchantService.getMerchantDetails(merchantId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        logger.info("Merchant {} ", merchant);
        return ResponseEntity.ok(merchant);
    }
}