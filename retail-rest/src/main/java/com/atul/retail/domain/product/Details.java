package com.atul.retail.domain.product;

/**
 * To Capture the Details of Product. Ideally we should be placing all the attributes which
 * Can help describing the product.
 * Created by atiwa00 on 6/4/16.
 */
public class Details {
    /** Product image URL. we can also have and specific class to handle Images for this product
     *  because a product will have more than one images and with different sizes.
     */
    private String imageUrl;
    /** More Description of this product  */
    private String description;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nDetails{" +
                "imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
