package com.eventmanagement.service;

import com.eventmanagement.entity.Event;
import com.eventmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.eventmanagement.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        existingEvent.setName(event.getName());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setEventDate(event.getEventDate());
        existingEvent.setDescription(event.getDescription());

        return eventRepository.save(existingEvent);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }
}

