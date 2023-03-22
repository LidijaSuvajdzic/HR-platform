package com.example.HRplatform.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {

    @NotBlank
    private String name;

}
