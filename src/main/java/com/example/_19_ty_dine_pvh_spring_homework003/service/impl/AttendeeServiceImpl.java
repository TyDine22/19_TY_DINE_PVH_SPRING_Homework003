package com.example._19_ty_dine_pvh_spring_homework003.service.impl;

import com.example._19_ty_dine_pvh_spring_homework003.exception.NotFoundException;
import com.example._19_ty_dine_pvh_spring_homework003.exception.OperationNotAllowedException;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.AttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.UpdateAttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.repository.AttendeeRepository;
import com.example._19_ty_dine_pvh_spring_homework003.repository.EventAttendeeRepository;
import com.example._19_ty_dine_pvh_spring_homework003.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventAttendeeRepository eventAttendeeRepository;
    @Override
    public List<Attendee> getAllInstructors(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        return attendeeRepository.getAllAttendees(offset, size);
    }

    @Override
    public Attendee getAttendeeById(Long attendeeId) {
        Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
        if (attendee == null) {
            throw new NotFoundException("Attendee with id " + attendeeId + " not found");
        }
        return attendee;
    }

    @Override
    public Attendee addAttendee(AttendeeRequest attendeeRequest) {
        return attendeeRepository.addAttendee(attendeeRequest);
    }

    @Override
    public Attendee updateAttendeeById(Long attendeeId, UpdateAttendeeRequest attendeeRequest) {
        Attendee attendee = attendeeRepository.updateAttendeeById(attendeeId, attendeeRequest);
        if (attendee == null) {
            throw new NotFoundException("Attendee with id " + attendeeId + " not found");
        }
        return attendee;
    }

    @Override
    public boolean deleteAttendeeById(Long attendeeId) {
        Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
        if (attendee == null) {
            throw new NotFoundException("Attendee with id " + attendeeId + " not found");
        }
        if (eventAttendeeRepository.existsByAttendeeId(attendeeId)) {
            throw new OperationNotAllowedException("This attendee is still on some events. Remove from those events first.");
        }
        return attendeeRepository.deleteAttendeeById(attendeeId);
    }
}