package com.assignment.accountService.service;

import com.assignment.accountService.KafkaProduserConfig;
import com.assignment.accountService.entity.Account;
import com.assignment.accountService.repository.AccountRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:18 AM
 * @project AccountService
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private KafkaProduserConfig producer;

    public boolean getAmount(Long id, Double amount) {
        Account account = accountRepository.getReferenceById(id);
        return account.getAmount() >= amount ? true : false;
    }

    public boolean updateAmount(Long id, Double amount) {
        Account account = accountRepository.getReferenceById(id);
        if (account.getAmount() >= amount) {
            account.setAmount(account.getAmount() - amount);
            accountRepository.saveAndFlush(account);
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
            case "availabilityAcc": {
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "availabilityAcc");
                obj.addProperty("oid",orderMsg.get("oid").getAsLong());
                if (getAmount(orderMsg.get("id").getAsLong(), orderMsg.get("total").getAsDouble())) {
                    obj.addProperty("status", HttpStatus.OK.value());
                    obj.addProperty("msg", "");
                    sendMessage(obj);
                } else {
                    obj.addProperty("status", "451");
                    obj.addProperty("msg", "inefficient balance");
                    sendMessage(obj);
                }
            }
            break;
            case "update": {
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "updateAcc");
                obj.addProperty("oid",orderMsg.get("oid").getAsLong());
                if (updateAmount(orderMsg.get("id").getAsLong(), orderMsg.get("total").getAsDouble())) {

                    obj.addProperty("status", HttpStatus.OK.value());
                    obj.addProperty("msg", "");
                    sendMessage(obj);
                } else {
                    obj.addProperty("status", "451");
                    obj.addProperty("msg", "inefficient balance");
                    sendMessage(obj);
                }
            }
        }
    }

    public void sendMessage(JsonObject obj) {
        producer.sendMessage(obj);
    }
}
