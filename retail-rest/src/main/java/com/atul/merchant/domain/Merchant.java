package com.atul.merchant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Domain class to represent a merchant in Retail System.
 * Created by atiwa00 on 6/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "merchants")
public class Merchant {
    @Id
    private String merchantId;
    @Indexed
    private String merchantName;
    private Address merchantAddress;
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Address getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(Address merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    @Override
    public String toString() {
        return "\nMerchant{" +
                "merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAddress=" + merchantAddress +
                ", productId='" + productId + '\'' +
                '}';
    }
}
