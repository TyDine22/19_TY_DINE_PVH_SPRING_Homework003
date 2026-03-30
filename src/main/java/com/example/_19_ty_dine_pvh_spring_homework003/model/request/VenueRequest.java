package com.example._19_ty_dine_pvh_spring_homework003.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {
    @NotBlank(message = "Venue name cannot be blank")
    @Schema(defaultValue = "KHSRD")
    private String venueName;

    @NotBlank(message = "Location can not be blank")
    @Schema(defaultValue = "Boeng Kok II")
    private String location;
}