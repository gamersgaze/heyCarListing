package com.heycar.challenge.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "listing")
public class ListingEntity extends BaseEntity {

    private String code;
    private String make;
    private String model;
    private Long kW;
    private Long year;
    private String color;

    // price should not be long because there could be decimal value. but in your examples, you have only shown non-decimal prices. so I take it long
    private Long price;

    /*
     * Ideally dealer should have separate entity with 'one to many' relationship with listing entity
     * but since there is no dealer REST API, I am just adding a dealerId column , assuming dealerId is numeric
     */
    private Long dealerId;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
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
