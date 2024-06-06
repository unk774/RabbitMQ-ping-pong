package com.example.ping_pong.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rabbit {
    private static final Logger logger = LoggerFactory.getLogger(Rabbit.class);

    @Value("${spring.rabbitmq.host:}")
    private String host;

    @Value("${spring.rabbitmq.port:}")
    private Integer port;

    @Value("${spring.rabbitmq.username:}")
    private String username;

    @Value("${spring.rabbitmq.password:}")
    private String password;
    @Value("${app.rabbit.exchange:}")
    private String exchange;
    @Value("${app.rabbit.ping-queue:}")
    private String pingQueue;
    @Value("${app.rabbit.pong-queue:}")
    private String pongQueue;
    @Value("${app.rabbit.ping-route:}")
    private String pingRoute;
    @Value("${app.rabbit.pong-route:}")
    private String pongRoute;


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setHost(host);
        ccf.setPort(port);
        ccf.setUsername(username);
        ccf.setPassword(password);
        logger.info(String.format("host: %s port: %s", host, port));
        return ccf;
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    Queue pingQueue(){
        return new Queue(pingQueue);
    }

    @Bean
    Queue pongQueue(){
        return new Queue(pongQueue);
    }

    @Bean
    DirectExchange pingPongTopic(){
        return new DirectExchange(exchange);
    }

    @Bean
    Binding bindingDirectPing() {
        return BindingBuilder.bind(pingQueue()).to(pingPongTopic()).with(pingRoute);
    }

    @Bean
    Binding bindingDirectPong() {
        return BindingBuilder.bind(pongQueue()).to(pingPongTopic()).with(pongRoute);
    }
}
