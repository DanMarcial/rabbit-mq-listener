package com.example.rabbitmq_publisher;

import java.util.logging.Logger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableRabbit
public class Listener {

	private static final Logger logger = Logger.getLogger("Listener");

	private Long timestamp;

	public static void main(String[] args) {
		SpringApplication.run(Listener.class, args);
	}

	@Value("${app.queue-name}")
	private String queueName;

	@RabbitListener(queues = "${app.queue-name}")
	public void onMessage(String order) {

		if (timestamp == null)
			timestamp = System.currentTimeMillis();
		logger.info((System.currentTimeMillis() - timestamp) + " ms : " + order);
	}

	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}

}