package com.example._19_ty_dine_pvh_spring_homework003.controller;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Venue;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.VenueRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.response.ApiResponse;
import com.example._19_ty_dine_pvh_spring_homework003.service.VenueService;
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
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
@Validated
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Venue>>> getAllVenues(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Venue> venues = venueService.getAllVenues(page, size);
        ApiResponse<List<Venue>> response = ApiResponse.<List<Venue>>builder()
                .timestamp(Instant.now())
                .message("Retrieved venues successfully")
                .status(HttpStatus.OK)
                .payload(venues)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{venue-id}")
    public ResponseEntity<ApiResponse<Venue>> getVenueById(@PathVariable("venue-id") @Positive Long venueId) {
        Venue venue = venueService.getVenueById(venueId);
        ApiResponse<Venue> response = ApiResponse.<Venue>builder()
                .timestamp(Instant.now())
                .message("Retrieved venue with " + venueId + " successfully")
                .status(HttpStatus.OK)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Venue>> addVenue(@Valid @RequestBody VenueRequest venueRequest) {
        Venue venue = venueService.addVenue(venueRequest);
        ApiResponse<Venue> response = ApiResponse.<Venue>builder()
                .timestamp(Instant.now())
                .message("Created venue successfully")
                .status(HttpStatus.CREATED)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{venue-id}")
    public ResponseEntity<ApiResponse<Venue>> updateVenueById(@PathVariable("venue-id") @Positive Long venueId, @Valid @RequestBody VenueRequest venueRequest) {
        Venue venue = venueService.updateVenueById(venueId, venueRequest);
        ApiResponse<Venue> response = ApiResponse.<Venue>builder()
                .timestamp(Instant.now())
                .message("Updated venue with id " + venueId + " successfully")
                .status(HttpStatus.OK)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{venue-id}")
    public ResponseEntity<?> deleteVenueById(@PathVariable("venue-id") Long venueId) {
        boolean deleted = venueService.deleteVenueById(venueId);
        ApiResponse<Venue> response = ApiResponse.<Venue>builder()
                .timestamp(Instant.now())
                .message("Delete venue with id " + venueId + " successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
