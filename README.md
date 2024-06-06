# RabbitMQ-ping-pong
Rabbit mq Direct exchange demo
1. build - mvn clean install
2. move ./target/ping-pong-0.0.1-SNAPSHOT.jar to ./docker/app/ping-pong.jar
3. ./docker/docker-compose up -d
4. wait for containers state - running
5. Check container log for any player-n. Log should show be messages like:<br />
2024-06-06 14:35:38 2024-06-06T10:35:38.806Z  INFO 1 --- [ping-pong] [pingContainer-1] c.e.p.components.PingPongListener        : ping - player2<br />
2024-06-06 14:35:38 2024-06-06T10:35:38.808Z  INFO 1 --- [ping-pong] [pongContainer-1] c.e.p.components.PingPongListener        : pong - player1<br />
2024-06-06 14:35:42 2024-06-06T10:35:42.808Z  INFO 1 --- [ping-pong] [pingContainer-1] c.e.p.components.PingPongListener        : ping - player2<br />
2024-06-06 14:35:45 2024-06-06T10:35:45.809Z  INFO 1 --- [ping-pong] [pongContainer-1] c.e.p.components.PingPongListener        : pong - player1<br />
2024-06-06 14:35:47 2024-06-06T10:35:47.830Z  INFO 1 --- [ping-pong] [pingContainer-1] c.e.p.components.PingPongListener        : ping - player3<br />
2024-06-06 14:35:48 2024-06-06T10:35:48.810Z  INFO 1 --- [ping-pong] [pongContainer-1] c.e.p.components.PingPongListener        : pong - player2<br />