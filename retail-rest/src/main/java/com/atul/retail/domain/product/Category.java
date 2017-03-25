package com.atul.retail.domain.product;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * A category to which the Product belongs.
 * Created by atiwa00 on 6/4/16.
 */
public class Category {

    /** Unique Id to identify this category. */
    @Indexed
    private Integer categoryId;

    /** Category name of this Category under which the product listed */
    private String categoryName;

    /** Category Description */
    private String description;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nCategory{" +
                ", categoryId=" + categoryId +
                ", categoryName=" + categoryName +
                ", description='" + description + '\'' +
                '}';
    }
}
