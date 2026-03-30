package com.example._19_ty_dine_pvh_spring_homework003.controller;

import com.example._19_ty_dine_pvh_spring_homework003.exception.NotFoundException;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.AttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.UpdateAttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.response.ApiResponse;
import com.example._19_ty_dine_pvh_spring_homework003.service.AttendeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendees")
@RequiredArgsConstructor
@Validated
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendee>>> getAllAttendees(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Attendee> attendees = attendeeService.getAllInstructors(page, size);
        ApiResponse<List<Attendee>> response = ApiResponse.<List<Attendee>>builder()
                .timestamp(Instant.now())
                .message("Retrieved attendees successfully")
                .status(HttpStatus.OK).payload(attendees).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> getAttendeeById(@PathVariable("attendee-id") @Positive Long attendeeId) {
        Attendee attendee = attendeeService.getAttendeeById(attendeeId);
        ApiResponse<Attendee> response = ApiResponse.<Attendee>builder()
                .timestamp(Instant.now())
                .message("Retrieved attendee with id " + attendeeId + " successfully")
                .status(HttpStatus.OK)
                .payload(attendee).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Attendee>> addAttendee(@Valid @RequestBody AttendeeRequest attendeeRequest) {
        Attendee attendee = attendeeService.addAttendee(attendeeRequest);
        ApiResponse<Attendee> response = ApiResponse.<Attendee>builder()
                .timestamp(Instant.now())
                .message("Created attendee successfully")
                .status(HttpStatus.CREATED).
                payload(attendee).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> updateAttendeeById(@PathVariable("attendee-id") @Positive Long attendeeId, @Valid @RequestBody UpdateAttendeeRequest attendeeRequest) {
        Attendee attendee = attendeeService.updateAttendeeById(attendeeId, attendeeRequest);
        ApiResponse<Attendee> response = ApiResponse.<Attendee>builder()
                .timestamp(Instant.now())
                .message("Updated attendee with id " + attendeeId + " successfully")
                .status(HttpStatus.OK)
                .payload(attendee).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendeeById(@PathVariable("attendee-id") @Positive Long attendeeId) {
        boolean deleted = attendeeService.deleteAttendeeById(attendeeId);
            ApiResponse<Void> response =ApiResponse.<Void>builder()
                    .timestamp(Instant.now())
                    .message("Deleted attendee with id " + attendeeId + " successfully")
                    .status(HttpStatus.OK)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}