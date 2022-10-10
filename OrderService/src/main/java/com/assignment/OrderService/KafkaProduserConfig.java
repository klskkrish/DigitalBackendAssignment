package com.assignment.OrderService;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sliyanag
 * @created 10/10/2022 - 12:34 PM
 * @project EurekaServer
 */
@Configuration
public class KafkaProduserConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProduserConfig.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(JsonObject message){
        LOGGER.info(String.format("Message sent -> %s", message));
        kafkaTemplate.send("orderData", message.toString());
    }
}
