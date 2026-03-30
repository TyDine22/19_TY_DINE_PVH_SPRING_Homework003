package com.example._19_ty_dine_pvh_spring_homework003.service.impl;

import com.example._19_ty_dine_pvh_spring_homework003.exception.DuplicateException;
import com.example._19_ty_dine_pvh_spring_homework003.exception.NotFoundException;
import com.example._19_ty_dine_pvh_spring_homework003.exception.OperationNotAllowedException;
import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Venue;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.VenueRequest;
import com.example._19_ty_dine_pvh_spring_homework003.repository.EventRepository;
import com.example._19_ty_dine_pvh_spring_homework003.repository.VenueRepository;
import com.example._19_ty_dine_pvh_spring_homework003.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Venue> getAllVenues(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        return venueRepository.getAllVenues(offset, size);
    }

    @Override
    public Venue getVenueById(Long venueId) {
        Venue venue = venueRepository.getVenueById(venueId);
        if (venue == null) {
            throw new NotFoundException("Venue with id " + venueId + " not found");
        }
        return venue;
    }

    @Override
    public Venue addVenue(VenueRequest venueRequest) {
        try {
            return venueRepository.addVenue(venueRequest);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException("Venue name already exists");
        }
    }

    @Override
    public Venue updateVenueById(Long venueId, VenueRequest venueRequest) {
        Venue venue = venueRepository.updateVenueById(venueId, venueRequest);
        if (venue == null) {
            throw new NotFoundException("Venue with id " + venueId + " not found");
        }
        return venue;
    }

    @Override
    public boolean deleteVenueById(Long venueId) {
        Venue venue = venueRepository.getVenueById(venueId);
        if (venue == null) {
            throw new NotFoundException("Venue with id " + venueId + " not found");
        }
        if (eventRepository.existsByVenueId(venueId)) {
            throw new OperationNotAllowedException("Some events still use this venue. Update or delete those events first.");
        }
        return venueRepository.deleteVenueById(venueId);
    }
}