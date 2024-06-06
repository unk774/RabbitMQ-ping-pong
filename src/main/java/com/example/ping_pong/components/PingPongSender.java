package com.example.ping_pong.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PingPongSender {
    private static final Logger logger = LoggerFactory.getLogger(PingPongSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${app.rabbit.exchange:}")
    private String exchange;
    @Value("${app.rabbit.ping-route:}")
    private String pingRoute;
    @Value("${app.rabbit.pong-route:}")
    private String pongRoute;

    public void sendPing(String player) {
        rabbitTemplate.convertAndSend(exchange, pingRoute, "ping - " + player);
    }

    public void sendPong(String player) {
        rabbitTemplate.convertAndSend(exchange, pongRoute, "pong - " + player);
    }
}
