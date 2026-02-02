package com.eventmanagement.service;

import com.eventmanagement.entity.Event;

import java.util.List;

public interface EventService {

    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    Event getEventById(Long id);
    List<Event> getAllEvents();
    void deleteEvent(Long id);
}
