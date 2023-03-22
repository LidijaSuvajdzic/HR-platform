package com.example.HRplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDto {

    private UUID uuid;

    @NotBlank
    private String name;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private List<SkillDto> skills;

}
