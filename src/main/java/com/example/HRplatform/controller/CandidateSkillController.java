package com.example.HRplatform.controller;

import com.example.HRplatform.dto.RemoveSkillRequestDto;
import com.example.HRplatform.service.CandidateSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="api/candidateSkills")
public class CandidateSkillController {

    private final CandidateSkillService candidateSkillService;

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@RequestBody @Valid RemoveSkillRequestDto removeSkillRequestDto) {
        candidateSkillService.removeSkillFromCandidate(removeSkillRequestDto);
        return ResponseEntity.ok().build();
    }
}
