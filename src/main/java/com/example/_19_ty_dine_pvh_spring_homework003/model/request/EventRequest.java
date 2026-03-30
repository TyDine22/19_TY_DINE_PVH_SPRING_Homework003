package com.example._19_ty_dine_pvh_spring_homework003.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event name cannot be blank")
    @Schema(defaultValue = "HRD Party")
    private String eventName;

    @NotNull(message = "Event date cannot be null")
    @Future(message = "Event date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @NotNull(message = "Venue Id cannot be null")
    @Positive(message = "Venue Id must be greater than 0")
    private Long venueId;

    @NotEmpty(message = "Attendee list cannot be empty")
    private List<@NotNull(message = "Attendee Id cannot be null") Long> attendees;

}