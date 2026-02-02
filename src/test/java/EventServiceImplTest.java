import com.eventmanagement.entity.Event;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.service.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1L);
        event.setName("Tech Conference");
        event.setLocation("Bangalore");
        event.setEventDate(LocalDate.of(2026, 5, 10));
        event.setDescription("Spring Boot Event");
    }

    @Test
    void testCreateEvent() {
        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventService.createEvent(event);

        assertNotNull(savedEvent);
        assertEquals("Tech Conference", savedEvent.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testGetEventById_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event foundEvent = eventService.getEventById(1L);

        assertEquals(1L, foundEvent.getId());
        assertEquals("Bangalore", foundEvent.getLocation());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEventById_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEventById(1L));
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = Arrays.asList(event, new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> eventList = eventService.getAllEvents();

        assertEquals(2, eventList.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEvent_Success() {
        Event updatedEvent = new Event();
        updatedEvent.setName("Updated Event");
        updatedEvent.setLocation("Hyderabad");
        updatedEvent.setEventDate(LocalDate.of(2026, 6, 15));
        updatedEvent.setDescription("Updated Description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.updateEvent(1L, updatedEvent);

        assertEquals("Updated Event", result.getName());
        assertEquals("Hyderabad", result.getLocation());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testUpdateEvent_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> eventService.updateEvent(1L, event));
    }

    @Test
    void testDeleteEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(event);

        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).delete(event);
    }
}