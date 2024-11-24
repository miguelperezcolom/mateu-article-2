package io.mateu.article2.shared.domain;

import io.mateu.article2.shared.events.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {

    private final List<DomainEvent> events = new ArrayList<>();

    public void addEvent(DomainEvent domainEvent) {
        events.add(domainEvent);
    }

    public List<DomainEvent> popEvents() {
        var accumulatedEvents = new ArrayList<>(events);;
        events.clear();
        return accumulatedEvents;
    }

}
