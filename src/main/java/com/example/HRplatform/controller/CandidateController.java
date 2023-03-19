package com.example.HRplatform.controller;
import com.example.HRplatform.dto.*;
import com.example.HRplatform.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CandidateDto candidateDto) {
        candidateService.create(candidateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCandidate(@RequestBody UpdatedCandidateDto updatedCandidateDto) {
       candidateService.updateCandidate(updatedCandidateDto);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable String email) {
        candidateService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> removeSkillFromCandidate(@RequestBody RemoveSkillRequestDto removeSkillRequestDto) {
        candidateService.removeSkillFromCandidate(removeSkillRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{firstname}")
    public ResponseEntity<?> find(@PathVariable String firstname) {
        List<CandidateDto> candidatesDto = candidateService.findByFirstname(firstname);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> find(@RequestBody CandidateNameDto candidateNameDto) {
        CandidateDto newCandidateDto = candidateService.findByFirstnameAndLastname(candidateNameDto.getFirstName(), candidateNameDto.getLastName());
        return new ResponseEntity<>(newCandidateDto, HttpStatus.OK);
    }

    @GetMapping("/skill/")
    public ResponseEntity<?> findCandidatesBySkillNames(@RequestBody SkillsDto skillsDto) {
        List<CandidateDto> candidatesDto = candidateService.findBySkillsName(skillsDto);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }
}
