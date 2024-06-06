package com.example.ping_pong.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@EnableRabbit
public class PingPongListener {
    private static final Logger logger = LoggerFactory.getLogger(PingPongListener.class);

    @Value("${app.rabbit.ping-queue:}")
    private String pingQueue;
    @Value("${app.rabbit.pong-queue:}")
    private String pongQueue;

    private final List<Consumer<String>> pingMessageHandlers = new ArrayList<>();

    private final List<Consumer<String>> pongMessageHandlers = new ArrayList<>();

    public void addPingMessageHandler(Consumer<String> handler) {
        pingMessageHandlers.add(handler);
    }

    public void addPongMessageHandler(Consumer<String> handler) {
        pongMessageHandlers.add(handler);
    }

    @Bean
    SimpleMessageListenerContainer pingContainer(ConnectionFactory connectionFactory) {
        return getSimpleMessageListenerContainer(pingQueue, pingMessageHandlers, connectionFactory);
    }

    @Bean
    SimpleMessageListenerContainer pongContainer(ConnectionFactory connectionFactory) {
        return getSimpleMessageListenerContainer(pongQueue, pongMessageHandlers, connectionFactory);
    }

    private SimpleMessageListenerContainer getSimpleMessageListenerContainer(String queueName, List<Consumer<String>> messageHandlers, ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(message -> {
            for (Consumer<String> messageHandler : messageHandlers) {
                messageHandler.accept(new String(message.getBody()));
            }
        });
        return container;
    }
}
