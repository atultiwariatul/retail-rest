package com.atul.retail.domain.product;

/**
 * Physical dimension of Product.
 * Created by atiwa00 on 6/4/16.
 */
public class Dimension {
    /** length (if applied) of this product */
    private float length;
    /** height (if applied) of this product */
    private float height;
    /** width (if applied) of this product */
    private float width;
    /** weight (if applied) of this product */
    private float weight;
    /** unit (if applied) of this product */
    private UNIT unit;

    public enum UNIT {
        INCH,CENTIMETER, GALLON,METER,POUND,GRAMS;
    }

    public UNIT getUnit() {
        return unit;
    }

    public void setUnit(UNIT unit) {
        this.unit = unit;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
