package com.example._19_ty_dine_pvh_spring_homework003.controller;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Event;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.EventRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.response.ApiResponse;
import com.example._19_ty_dine_pvh_spring_homework003.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvents(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Event> events = eventService.getAllEvents(page, size);
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .timestamp(Instant.now())
                .message("Retrieved events successfully")
                .status(HttpStatus.OK)
                .payload(events)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{event-id}")
    public ResponseEntity<ApiResponse<Event>> getEventById(@PathVariable("event-id") @Positive Long eventId) {
        Event event = eventService.getEventById(eventId);
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .timestamp(Instant.now())
                .message("Retrieved event with id " + eventId + " successfully")
                .status(HttpStatus.OK)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Event>> addEvent(@Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.addEvent(eventRequest);
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .timestamp(Instant.now())
                .message("Created event successfully")
                .status(HttpStatus.CREATED)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{event-id}")
    public ResponseEntity<ApiResponse<Event>> updateEventById(@PathVariable("event-id") @Positive Long eventId, @Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.updateEventById(eventId, eventRequest);
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .timestamp(Instant.now())
                .message("Updated event with Id " + eventId + " successfully")
                .status(HttpStatus.OK)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity<?> deleteEventById(@PathVariable("event-id") @Positive Long eventId) {
        boolean deleted = eventService.deleteEventById(eventId);
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .timestamp(Instant.now())
                .message("Deleted event with id " + eventId + "successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
