package com.atul.retail.domain.product;

import com.atul.merchant.domain.Merchant;
import com.atul.retail.domain.AbstractDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Product Entity, which will represent an unique product in Retail system.
 * It will be the Centric Entity to Store/Retrieve Product information.
 * Created by atiwa00 on 6/4/16.
 */
//@CompoundIndexes({
//        @CompoundIndex(name = "productId", def = "{'merchantId': 1, 'displayName': -1}")
//})

public class Product extends AbstractDocument {
    public static final String COLLECTION_LINE_ITEM_SUFFIX = "products";
    /** Unique Id/Primary key of this entity */
    @Id
    private String productId;
////    /** product Id a unique identifier  */
//    @Indexed(unique = true)
//    private Integer productId;
    /** Name to be displayed while showing them */
    private String displayName;
    /** Merchant Id who listed/selling this product */
    private String merchantId;
    /** Merchant Id of the Merchant who listed this product */
    private Merchant merchant;
    /** Product Details */
    private Details details;
    /** Product Shipping Information  */
    private Shipping shipping;
    /** Product Pricing information and calculation will be represented by pricing */
    private Price pricing;
    /** Product Physical attributes representation */
    private Dimension dimension;
    /** Tag used to search a product. */
    @Indexed
    private String[] tags;
    /** Product Category to which product belongs e.g. Sports, Cloths etc.  */
    private Category category;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Price getPricing() {
        return pricing;
    }

    public void setPricing(Price pricing) {
        this.pricing = pricing;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productId=" + productId +
                ", displayName='" + displayName + '\'' +
                ", merchant='" + merchant + '\'' +
                ", category='" + category + '\'' +
                ", details=" + details +
                ", shipping=" + shipping +
                ", pricing=" + pricing +
                '}';
    }
}
