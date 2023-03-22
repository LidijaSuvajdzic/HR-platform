package com.example.HRplatform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCandidateDto {

    @NotBlank
    private String name;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;
}
