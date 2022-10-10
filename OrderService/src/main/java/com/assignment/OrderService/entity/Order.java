package com.assignment.OrderService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author sliyanag
 * @created 10/10/2022 - 9:32 AM
 * @project EurekaServer
 */
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public Long invId;
    public Long accId;
    public Double total;
    public int status;


    public Order(Long id, Long invId, Long accId, Double total, int status) {
        this.id = id;
        this.invId = invId;
        this.accId = accId;
        this.total = total;
        this.status = status;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}