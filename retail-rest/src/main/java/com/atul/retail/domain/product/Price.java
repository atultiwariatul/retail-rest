package com.atul.retail.domain.product;

/**
 * Product Pricing information and calculation will be represented by pricing
 * Created by atiwa00 on 6/4/16.
 */
public class Price {

    /** List price of the product */
    private float listPrice;

    /** The discount on which this product is currently listing */
    private Integer discountPercentage;

    /** Currency in which this price is listed */
    private CURRENCY currency;

    /** current price of the product after all calculation */
    private float currentPrice;

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public CURRENCY getCurrency() {
        return currency;
    }

    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public enum CURRENCY{
        USD,EURO,INR,YEN;
    }

    @Override
    public String toString() {
        return "\n Price{" +
                "listPrice=" + listPrice +
                ", discountPercentage=" + discountPercentage +
                ", currency=" + currency +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
