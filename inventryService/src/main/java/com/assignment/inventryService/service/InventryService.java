package com.assignment.inventryService.service;

import com.assignment.inventryService.KafkaProduserConfig;
import com.assignment.inventryService.Response;
import com.assignment.inventryService.entity.Inventry;
import com.assignment.inventryService.repository.InventryRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:18 AM
 * @project EurekaServer
 */
@Service
@Transactional
public class InventryService {

    @Autowired
    private InventryRepository inventryRepository;
    @Autowired
    private KafkaProduserConfig producer;

    public boolean getInventryItem(Long id, Integer qty) {
        Inventry inv = inventryRepository.getReferenceById(id);
        return inv.getQty() > qty ? true : false;
    }

    public boolean updateInventryItem(Long id, Integer qty) {
        Inventry inv = inventryRepository.getReferenceById(id);
        if (inv.getQty() > qty) {
            inv.setQty(inv.getQty() - qty);
            inventryRepository.saveAndFlush(inv);
            return true;
        }
        return false;
    }

    @KafkaListener(topics = "orderData", groupId = "group")
    public void consume(String message) {
        JsonElement jsonElement = JsonParser.parseString(message);
        JsonObject orderMsg = jsonElement.getAsJsonObject();
        System.out.println("inventry service" + orderMsg);

        switch (orderMsg.get("type").getAsString()) {
            case "availability": {
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "availabilityInv");
                obj.addProperty("oid",orderMsg.get("oid").getAsLong());
                if (getInventryItem(orderMsg.get("id").getAsLong(), orderMsg.get("qty").getAsInt())) {

                    obj.addProperty("status", HttpStatus.OK.value());
                    obj.addProperty("msg", "");
                    sendMessage(obj);
                } else {
                    obj.addProperty("status", "451");
                    obj.addProperty("msg", "stcok not enough");

                    sendMessage(obj);
                }
            }
            break;
            case "update": {
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "updateInv");
                obj.addProperty("oid",orderMsg.get("oid").getAsLong());
                if (updateInventryItem(orderMsg.get("id").getAsLong(), orderMsg.get("qty").getAsInt())) {

                    obj.addProperty("status", HttpStatus.OK.value());
                    obj.addProperty("msg", "");
                    sendMessage(obj);
                } else {
                    obj.addProperty("status", "451");
                    obj.addProperty("msg", "stcok not enough");

                    sendMessage(obj);
                }
            }
        }
    }

    public void sendMessage(JsonObject obj) {
        producer.sendMessage(obj);
    }
}
