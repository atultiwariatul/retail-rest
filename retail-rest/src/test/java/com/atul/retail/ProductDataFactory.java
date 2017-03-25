package com.atul.retail;

import com.atul.retail.domain.product.*;
import org.json.JSONObject;

/**
 * Created by atiwa00 on 6/4/16.
 */
public class ProductDataFactory {

    public Category getCategory(){
        Category category = new Category();
        category.setDescription("New Category");
        category.setCategoryId(1);
        return category;
    }

    public Details getDetails(){
        Details details = new Details();
        details.setImageUrl("http://xyzabc.com/image1.png");
        details.setDescription("This is image1 description");
        return details;
    }

    public Shipping getShipping(){
        Shipping shipping = new Shipping();
        shipping.setCapacity("capacity1");
        return shipping;
    };
    public Price getPricing(){
        Price pricing = new Price();
        pricing.setCurrency(Price.CURRENCY.USD);
        pricing.setCurrentPrice(18.0f);
        pricing.setDiscountPercentage(10);
        pricing.setListPrice(20f);
        return pricing;
    }

    public Dimension getDimension(){
        Dimension dimension = new Dimension();
        dimension.setHeight(15);
        dimension.setLength(15);
        dimension.setWeight(15);
        dimension.setWidth(15);
        dimension.setUnit(Dimension.UNIT.INCH);
        return dimension;
    }
    public Product getProduct(){
        Product product = new Product();
        product.setDisplayName("Product Display Name1");
        product.setDetails(getDetails());
        product.setDimension(getDimension());
//        product.setMerchantId("123456");
        product.setShipping(getShipping());
        product.setCategory(getCategory());
        return product;
    }

    public String getProductJSON(){
        return new JSONObject(getProduct()).toString();
    }

    public static void main(String[] args) {
        ProductDataFactory factory = new ProductDataFactory();
        System.out.println(factory.getProductJSON());
    }
}
