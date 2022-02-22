package com.example.demo.controllers;

import com.example.demo.config.RabbitConfiguration;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PublishController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @Retryable(value = {AmqpException.class}, maxAttemptsExpression = "#{${server.retry.policy.max.attempts:3}}", backoff = @Backoff(delayExpression = "#{${server.retry.policy.delay:36000}}", multiplierExpression = "#{${server.retry.policy.multiplier:2}}", maxDelayExpression = "#{${server.retry.policy.max.delay:252000}}"))
    public void doSendMessageToRabbitMQ(String subscriptionJson) throws AmqpException {

        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME, subscriptionJson);

    }
}
