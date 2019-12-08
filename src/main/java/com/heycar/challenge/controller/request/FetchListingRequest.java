package com.heycar.challenge.controller.request;

public class FetchListingRequest {
    private String make;
    private String model;
    private Long year;
    private String color;
    private Integer startAt;
    private Integer maxResults;


    public FetchListingRequest(String make, String model, Long year, String color, Integer startAt, Integer maxResults) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.startAt = startAt;
        this.maxResults = maxResults;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}
