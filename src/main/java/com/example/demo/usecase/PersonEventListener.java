package com.example.demo.usecase;

import com.example.demo.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.api.handlers.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log
public class PersonEventListener implements EventHandler<Person> {

    private final DomainEventBus eventBus;

    @Override
    public Mono<Void> handle(DomainEvent<Person> message) {
        return Mono.empty().then();
    }
}
