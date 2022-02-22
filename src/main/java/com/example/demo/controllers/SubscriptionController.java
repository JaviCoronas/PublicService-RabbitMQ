package com.example.demo.controllers;

import com.example.demo.config.RabbitConfiguration;
import com.example.demo.models.Subscription;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SubscriptionController {

    @Autowired
    PublishController publisher;

    @PostMapping(value = "/subscription", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Subscription> saveSubscription(@RequestBody Subscription subscription) {
        Subscription subsToSend = new Subscription();
        subsToSend.setId(UUID.randomUUID());
        subsToSend.setName(subscription.getName());
        subsToSend.setEmail(subscription.getEmail());
        String sub = new Gson().toJson(subsToSend);
        System.out.println(sub);
        publisher.doSendMessageToRabbitMQ(sub);

        return ResponseEntity.ok(subsToSend);
    }
}
