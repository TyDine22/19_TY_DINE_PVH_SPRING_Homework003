package com.example._19_ty_dine_pvh_spring_homework003.service;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Venue;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.VenueRequest;

import java.util.List;

public interface VenueService {
    List<Venue> getAllVenues(Integer page, Integer size);

    Venue getVenueById(Long venueId);

    Venue addVenue(VenueRequest venueRequest);

    Venue updateVenueById(Long venueId, VenueRequest venueRequest);

    boolean deleteVenueById(Long venueId);
}

