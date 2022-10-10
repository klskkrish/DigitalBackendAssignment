package com.assignment.inventryService.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author sliyanag
 * @created 10/10/2022 - 9:32 AM
 * @project EurekaServer
 */
@Entity
public class Inventry {
    @Id
    public Long id;
    public String name;
    public Integer qty;
    public Double price;


    public Inventry(Long id, String name, Integer qty, Double price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
    public Inventry() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return price;
    }

    public void setUnitPrice(Double price) {
        this.price = price;
    }
}