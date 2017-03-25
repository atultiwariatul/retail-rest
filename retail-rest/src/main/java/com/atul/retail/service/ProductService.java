package com.atul.retail.service;

import com.atul.retail.domain.ListView;
import com.atul.retail.domain.SortOrder;
import com.atul.retail.domain.product.Product;
import com.atul.retail.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by atiwa00 on 6/5/16.
 */
public interface ProductService {

    Product findById(String productId) throws ServiceException;

    void saveProducts(List<Product> products) throws ServiceException;

    void saveProduct(Product product) throws ServiceException;

    List<Product> findAllProducts() throws ServiceException;

    ListView<Product> queryByCriteria(Map<String, String> paramsToQuery,
                                      String sortKey, SortOrder sortOrder) throws ServiceException;

    void updateProduct(Product product) throws ServiceException;
}
