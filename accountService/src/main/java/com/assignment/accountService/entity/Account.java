package com.assignment.accountService.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author sliyanag
 * @created 10/10/2022 - 9:32 AM
 * @project EurekaServer
 */
@Entity
public class Account {
    @Id
    public Long id;
    public String name;
    public Double amount;


    public Account(Long id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
    public Account() {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}