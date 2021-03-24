package com.example.demo.usecase;

import com.example.demo.model.AddPersonCommand;
import com.example.demo.model.PersonCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.api.handlers.CommandHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class PersonReceiver implements CommandHandler<AddPersonCommand> {

    private final DomainEventBus eventBus;

    @Override
    public Mono<Void> handle(Command<AddPersonCommand> message) {
        return Mono.from(eventBus.emit(new DomainEvent<>("person.created.success", "342",
                new PersonCreatedEvent(UUID.randomUUID().toString(), message.getData().getPerson()))));
    }

}
