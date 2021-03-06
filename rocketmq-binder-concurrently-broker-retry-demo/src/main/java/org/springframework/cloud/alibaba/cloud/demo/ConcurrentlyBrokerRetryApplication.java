package org.springframework.cloud.alibaba.cloud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * @author wut
 */
@SpringBootApplication
@EnableBinding({ Source.class, Sink.class })
public class ConcurrentlyBrokerRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrentlyBrokerRetryApplication.class, args);
	}

	@Bean
	public CustomRunner customRunner() {
		return new CustomRunner();
	}

	public static class CustomRunner implements CommandLineRunner {

		@Autowired
		private Source source;

		@Override
		public void run(String... args) throws Exception {
			int count = 1;
			for (int index = 1; index <= count; index++) {
				Message<String> message = MessageBuilder.withPayload("msg-" + index)
						.setHeader("index", index).build();
				System.out.println(message);
				source.output().send(message);
			}
		}
	}

}
