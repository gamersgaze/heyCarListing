package com.heycar.challenge.models;

public class ListingDTO extends BaseDTO {

    private String code;
    private String make;
    private String model;
    private Long kW;
    private Long year;
    private String color;
    private Long price;

    public ListingDTO() {

    }

    public ListingDTO(String code, String make, String model, Long kW, Long year, String color, Long price) {
        this.code = code;
        this.make = make;
        this.model = model;
        this.kW = kW;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getkW() {
        return kW;
    }

    public void setkW(Long kW) {
        this.kW = kW;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
