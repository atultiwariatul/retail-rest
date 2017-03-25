package com.atul.retail.exceptions;

/**
 *
 */
public enum ErrorCode {
    DATA_ACCESS_RESOURCE_FAILURE("data.access.resource.failure", "Failure in Database connection"),
    RESOURCE_NOT_FOUND("resource.not.found","The Resource you are trying to access is not found:"),
    PRODUCT_ALREADY_EXISTS("product.already.exists","The product you are trying to save is already exists."),
    MERCHANT_SERVICE_UNAVAILABLE("merchant.service.unavailable","Please verify that merchant is up and running or merchant service URL is correct");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}