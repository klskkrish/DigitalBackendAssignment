package com.assignment.OrderService.controller;

import com.assignment.OrderService.KafkaProduserConfig;
import com.assignment.OrderService.service.OrderService;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sliyanag
 * @created 10/10/2022 - 8:48 AM
 * @project EurekaServer
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/place-order/{id}/{qty}")
    public HttpStatus placeOrder(@PathVariable(required = true) Long id, @PathVariable(required = true) Integer qty)
    {
        System.out.println(id+" "+qty);
        if(id ==null && qty ==null){
            return HttpStatus.BAD_REQUEST;
        }else{
            JsonObject obj = orderService.manageOrder(id, qty);
            if(null != obj){
                LOGGER.info("Order Processing", obj);
            }
        }
        return HttpStatus.OK;
    }
}
