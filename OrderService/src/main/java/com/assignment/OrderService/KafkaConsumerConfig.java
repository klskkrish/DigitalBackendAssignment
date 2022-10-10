package com.assignment.OrderService;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sliyanag
 * @created 10/10/2022 - 12:28 PM
 * @project EurekaServer
 */
@Configuration
public class KafkaConsumerConfig {


    @Bean
    public NewTopic invTopic(){
        return TopicBuilder.name("inventryData").build();
    }
    @Bean
    public NewTopic accountTopic(){
        return TopicBuilder.name("accountData").build();
    }
}
