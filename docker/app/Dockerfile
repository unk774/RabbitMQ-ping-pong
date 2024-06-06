FROM openjdk:17

ENV spring.rabbitmq.host rabbitmq
ENV spring.rabbitmq.port 5672
ENV spring.rabbitmq.username rmuser
ENV spring.rabbitmq.password rmpassword

RUN mkdir /opt/app
COPY ping-pong.jar /opt/app/ping-pong.jar
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "ping-pong.jar"]