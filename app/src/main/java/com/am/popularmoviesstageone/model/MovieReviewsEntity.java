package com.am.popularmoviesstageone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewsEntity {

    @SerializedName("id")
    private Integer id;
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<MovieReviewEntity> reviewList = null;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieReviewEntity> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<MovieReviewEntity> reviewList) {
        this.reviewList = reviewList;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
