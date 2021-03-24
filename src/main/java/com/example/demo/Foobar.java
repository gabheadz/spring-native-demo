package com.example.demo;

import com.example.demo.model.Person;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.api.AsyncQuery;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Foobar {

    @Autowired
    private DirectAsyncGateway directAsyncGateway;

    private final String target = "MyAppName";

    @GetMapping("/hello")
    public Mono<Person> hello() {
        AsyncQuery<?> query = new AsyncQuery<>("query1", "123");
        return directAsyncGateway.requestReply(query, target, Person.class);
    }

    @PostMapping("/hello")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<String> create() {
        Command<Person> createPersonCommand = new Command<>("person.create", "0001",
                new Person("1", "Jhon", "Doe"));
        return directAsyncGateway.sendCommand(createPersonCommand, target)
                .then(Mono.just("OK"));
    }

}