package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.usecase.PersonEventListener;
import com.example.demo.usecase.PersonReceiver;
import lombok.extern.java.Log;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.reactivecommons.async.api.HandlerRegistry;
import org.reactivecommons.async.impl.config.annotations.EnableDirectAsyncGateway;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableMessageListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static reactor.core.publisher.Mono.just;

@SpringBootApplication
@EnableMessageListeners
@EnableDomainEventBus
@EnableDirectAsyncGateway
@Log
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	PersonReceiver personReceiver;

	@Autowired
	PersonEventListener personEventListener;

	@Bean
	public HandlerRegistry handlerRegistrySubs(DirectAsyncGateway gateway) {
		return HandlerRegistry.register()
				.serveQuery("query1", message -> just(new Person(message, "Jhon", "Smith")), String.class)
				.serveQuery("query2", (from, message) -> gateway.reply(new Person(message, "Jane", "Doe"), from).then(), String.class)
				.handleCommand("person.create", personReceiver)
				.listenEvent("person.created.success", personEventListener);
	}
}
