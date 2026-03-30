package com.example._19_ty_dine_pvh_spring_homework003.service.impl;

import com.example._19_ty_dine_pvh_spring_homework003.exception.BadRequestException;
import com.example._19_ty_dine_pvh_spring_homework003.exception.ConflictException;
import com.example._19_ty_dine_pvh_spring_homework003.exception.NotFoundException;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Event;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Venue;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.EventRequest;
import com.example._19_ty_dine_pvh_spring_homework003.repository.AttendeeRepository;
import com.example._19_ty_dine_pvh_spring_homework003.repository.EventAttendeeRepository;
import com.example._19_ty_dine_pvh_spring_homework003.repository.EventRepository;
import com.example._19_ty_dine_pvh_spring_homework003.repository.VenueRepository;
import com.example._19_ty_dine_pvh_spring_homework003.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventAttendeeRepository eventAttendeeRepository;
    private final AttendeeRepository attendeeRepository;
    private final VenueRepository venueRepository;
    @Override
    public List<Event> getAllEvents(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        return eventRepository.getAllEvents(offset, size);
    }

    @Override
    public Event getEventById(Long eventId) {
        Event event = eventRepository.getEventById(eventId);
        if (event == null) {
            throw new NotFoundException("Event with ID " + eventId + " not found");
        }
        return event;
    }

    @Override
    public Event addEvent(EventRequest eventRequest) {
        if (eventRequest.getEventDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Event date must be in the future");
        }
        boolean exists = eventRepository.existsByNameAndDate(eventRequest.getEventName(), eventRequest.getEventDate());

        if(exists) {
            throw new ConflictException("Event name already exists on this date");
        }

        Venue venue = venueRepository.getVenueById(eventRequest.getVenueId());
        if (venue == null) {
            throw new NotFoundException("Venue with id " + eventRequest.getVenueId() + " not found");
        }

        for (Long attendeeId : eventRequest.getAttendees()) {
            Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
            if (attendee == null) {
                throw new NotFoundException("Attendee with id " + attendeeId + " not found");
            }
        }

        Event event = eventRepository.addEvent(eventRequest);
        for (Long attendeeId : eventRequest.getAttendees()) {
            eventAttendeeRepository.addAttendeeAndEvent(attendeeId, event.getEventId());
        }

        return eventRepository.getEventById(event.getEventId());
    }

    @Override
    public Event updateEventById(Long eventId, EventRequest eventRequest) {
        Event existing = eventRepository.getEventById(eventId);

        if(existing == null) {
            throw new NotFoundException("Event with id " + eventId + " not found");
        }

        Venue venue = venueRepository.getVenueById(eventRequest.getVenueId());
        if (venue == null) {
            throw new NotFoundException("Venue with id " + eventRequest.getVenueId() + " not found");
        }

        if (eventRequest.getEventDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Event date must be in the future");
        }

        boolean exists = eventRepository.existsByNameAndDateNoId(
                eventRequest.getEventName(), eventRequest.getEventDate(), eventId
        );

        if (exists) {
            throw new ConflictException("Event name already exists on this date");
        }

        for (Long attendeeId : eventRequest.getAttendees()) {
            Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
            if (attendee == null) {
                throw new NotFoundException("Attendee with id " + attendeeId + " not found");
            }
        }

        eventRepository.updateEventById(eventId, eventRequest);
        eventAttendeeRepository.deleteEventAttendeeById(eventId);
        for (Long attendeeId : eventRequest.getAttendees()) {
            eventAttendeeRepository.addAttendeeAndEvent(attendeeId, eventId);
        }

        return eventRepository.getEventById(eventId);
    }

    @Override
    public boolean deleteEventById(Long eventId) {
        Event event = eventRepository.getEventById(eventId);
        if(event == null) {
            throw new NotFoundException("Event with id " + eventId + " not found");
        }
        eventAttendeeRepository.deleteEventAttendeeById(eventId);
        return eventRepository.deleteEventById(eventId);
    }
}