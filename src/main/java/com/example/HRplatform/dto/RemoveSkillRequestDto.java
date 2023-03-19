package com.example.HRplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RemoveSkillRequestDto {

    @NotNull
    private UUID candidateUuid;
    @NotBlank
    private String skillName;
}
