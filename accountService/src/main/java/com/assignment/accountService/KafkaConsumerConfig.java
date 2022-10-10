package com.assignment.accountService;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author sliyanag
 * @created 10/10/2022 - 12:28 PM
 * @project EurekaServer
 */
@Configuration
public class KafkaConsumerConfig {


    @Bean
    public NewTopic invTopic(){
        return TopicBuilder.name("orderData").build();
    }
}
