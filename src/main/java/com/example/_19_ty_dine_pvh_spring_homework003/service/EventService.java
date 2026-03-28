package com.example._19_ty_dine_pvh_spring_homework003.service;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Event;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.EventRequest;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents(Integer page, Integer size);

    Event getEventById(Long eventId);

    Event addEvent(EventRequest eventRequest);

    Event updateEventById(Long eventId, EventRequest eventRequest);

    boolean deleteEventById(Long eventId);
}
