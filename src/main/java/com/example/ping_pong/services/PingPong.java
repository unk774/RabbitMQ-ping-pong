package com.example.ping_pong.services;

import com.example.ping_pong.components.PingPongListener;
import com.example.ping_pong.components.PingPongSender;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PingPong {
    private static final Logger logger = LoggerFactory.getLogger(PingPongListener.class);

    @Autowired
    private PingPongSender pingPongSender;
    @Autowired
    private PingPongListener pingPongListener;

    @Value("${app.player:}")
    private String player;
    @Value("${app.start:false}")
    private boolean start;

    private final Random random = new Random();

    @PostConstruct
    private void init() {
        this.pingPongListener.addPingMessageHandler(this::onPing);
        this.pingPongListener.addPongMessageHandler(this::onPong);
        if (start) {
            start();
        }
    }

    private void start() {
        pingPongSender.sendPing(player);
    }

    private void onPing(String message) {
        logger.info(message);
        pingPongSender.sendPong(player);
        sleepRandom();
    }

    private void onPong(String message) {
        logger.info(message);
        pingPongSender.sendPing(player);
        sleepRandom();
    }

    private void sleepRandom() {
        int rnd = random.nextInt(9) + 1;
        try {
            Thread.sleep(1000 * rnd);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
