package com.example._19_ty_dine_pvh_spring_homework003.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeRequest {
    @NotBlank(message = "Attendee name cannot be blank")
    @Size(min = 1, max = 50, message = "Attendee name must be between 1 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "Attendee name must contain only letters and spaces"
    )
    @Schema(defaultValue = "Josuke")
    private String attendeeName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid format (e.g., abc@gmail.com)")
    @Schema(defaultValue = "abc@gmail.com")
    private String email;

}