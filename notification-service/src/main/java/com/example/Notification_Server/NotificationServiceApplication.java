package com.example.Notification_Server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic", groupId = "notificationId")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent)
    {
        //send out and email notification
//        log.info("hi");
        String s="";
        log.info("Received notification for Order -{} ", s);



    }


}
