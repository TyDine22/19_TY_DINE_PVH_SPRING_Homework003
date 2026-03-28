package com.example._19_ty_dine_pvh_spring_homework003.service;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.AttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.UpdateAttendeeRequest;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.List;
public interface AttendeeService {
    List<Attendee> getAllInstructors(Integer page, Integer size);

    Attendee getAttendeeById(Long attendeeId);

    Attendee addAttendee(AttendeeRequest attendeeRequest);

    Attendee updateAttendeeById(Long attendeeId, UpdateAttendeeRequest attendeeRequest);

    boolean deleteAttendeeById(Long attendeeId);
}
