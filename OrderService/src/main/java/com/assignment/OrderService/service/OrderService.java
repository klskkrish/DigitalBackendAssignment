package com.assignment.OrderService.service;

import com.assignment.OrderService.KafkaProduserConfig;
import com.assignment.OrderService.controller.OrderController;
import com.assignment.OrderService.entity.Order;
import com.assignment.OrderService.repository.OrderRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sliyanag
 * @created 10/10/2022 - 12:26 PM
 * @project EurekaServer
 */
@Service
@Transactional
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;
    Map<Long, Order> orderManagerMap = new HashMap<>();
    @Autowired
    private KafkaProduserConfig producer;

    public JsonObject manageOrder(Long id, Integer qty) {
        Order order = new Order(0L, 0L, 0L, 0.0, 0);
        orderRepository.save(order);
        orderManagerMap.put(order.id, order);
        JsonObject obj = new JsonObject();
        obj.addProperty("oid", order.id);
        obj.addProperty("id", id);
        obj.addProperty("qty", qty);
        obj.addProperty("type", "availabilityInv");
        sendMessage(obj);
        LOGGER.info("check inventory", obj);
        return obj;
    }

    @KafkaListener(topics = "inventryData", groupId = "group")
    public void consume(String message) {
        JsonElement jsonElement = JsonParser.parseString(message);
        JsonObject orderMsg = jsonElement.getAsJsonObject();
        System.out.println(orderMsg);
        switch (orderMsg.get("status").getAsString()) {
            case "200": {
                if ("availabilityInv".equals(orderMsg.get("type")) || "availabilityAcc".equals(orderMsg.get("type"))) {
                    Order order = orderManagerMap.get(orderMsg.get("oid").getAsLong());
                    if ("availabilityAcc".equals(orderMsg.get("type"))) {
                        order.setAccId(orderMsg.get("id").getAsLong());
                    } else {
                        order.setTotal(orderMsg.get("total").getAsDouble());
                        order.setInvId(orderMsg.get("id").getAsLong());
                        orderMsg.remove("type");
                        orderMsg.addProperty("type", "availabilityAcc");
                        sendMessage(orderMsg);
                        LOGGER.info("check account", orderMsg);
                    }
                    if (order.invId > 0 && order.accId > 0) {
                        JsonObject obj = new JsonObject();
                        obj.addProperty("oid", orderMsg.get("oid").getAsLong());
                        obj.addProperty("id", orderMsg.get("id").getAsLong());
                        obj.addProperty("qty", orderMsg.get("qty").getAsInt());
                        obj.addProperty("type", "update");
                        sendMessage(obj);
                        LOGGER.info("update inventory and account", obj);
                    } else {
                        orderManagerMap.put(order.id, order);
                    }
                } else if ("updateInv".equals(orderMsg.get("type")) || "updateAcc".equals(orderMsg.get("type"))) {
                    Order order = orderManagerMap.get(orderMsg.get("oid").getAsLong());
                    order.setStatus(order.getStatus() + 1);
                    orderRepository.save(order);
                    orderManagerMap.remove(order.id);
                    LOGGER.info("TO be shipped", order.toString());
                }
            }
            default:{
                Order order = orderManagerMap.get(orderMsg.get("oid").getAsLong());
                order.setStatus(0);
                order.setAccId(0L);
                order.setInvId(0L);
                orderRepository.save(order);
                orderManagerMap.remove(order.id);
                LOGGER.error(orderMsg.get("status").getAsString(), orderMsg.get("msg").getAsString());
            }
        }
    }

    public void sendMessage(JsonObject obj) {
        producer.sendMessage(obj);
    }
}
