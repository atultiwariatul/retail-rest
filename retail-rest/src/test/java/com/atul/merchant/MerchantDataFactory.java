package com.atul.merchant;

import com.atul.merchant.domain.Address;
import com.atul.merchant.domain.Merchant;
import org.json.JSONObject;

/**
 * Created by atiwa00 on 6/4/16.
 */
public class MerchantDataFactory {
    public Address getAddress(){
        Address address = new Address();
        address.setApartment("Springhouse");
        address.setCity("Pleasanton");
        address.setCountry("US");
        address.setState("CA");
        address.setZip(94588);
        return address;
    }

    public Merchant getMerchant(){
        Merchant merchant = new Merchant();
        merchant.setMerchantAddress(getAddress());
        merchant.setMerchantName("MyRetail Merchant 3");
        merchant.setProductId("1234567");
        return merchant;
    }

    public String getProductJSON(){
        return new JSONObject(getMerchant()).toString();
    }

    public static void main(String[] args) {
        MerchantDataFactory factory = new MerchantDataFactory();
        System.out.println(factory.getProductJSON());
    }
}
