package com.atul.retail.domain;

import java.util.List;

/**
 * ListView class to handle custom View of all the data returned from back-end.
 * It will be used in custom pagination of result.
 * Created by atiwa00 on 6/4/16.
 * @param <E>
 */
public class ListView<E>{

    /**
     * The number of entities in the full result set.
     */
    private int totalCount;

    /**
     * The list of entities in this view on the result set.
     */
    private List<E> currentResults;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<E> getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(List<E> currentResults) {
        this.currentResults = currentResults;
    }
}
