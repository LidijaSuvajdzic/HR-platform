package com.example.HRplatform.controller;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.CandidateNameDto;
import com.example.HRplatform.dto.RemoveSkillRequestDto;
import com.example.HRplatform.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CandidateDto candidateDto) {
        if (candidateDto != null){
            boolean isExists = candidateService.save(candidateDto);
            if (isExists) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else
                return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{firstname}/{lastname}")
    public ResponseEntity<?> delete(@PathVariable String firstname, @PathVariable String lastname) {
        if (candidateService.isExists(firstname,lastname)) {
            candidateService.delete(firstname,lastname);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestBody RemoveSkillRequestDto removeSkillRequestDto) {
        if (removeSkillRequestDto != null) {
            candidateService.removeSkillFromCandidate(removeSkillRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{firstname}")
    public ResponseEntity<?> find(@PathVariable String firstname) {
        List<CandidateDto> candidatesDto = candidateService.findByFirstname(firstname);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> find(@RequestBody CandidateNameDto candidateNameDto) {
        CandidateDto candidateDto = candidateService.findByFirstnameAndLastname(candidateNameDto.getFirstName(), candidateNameDto.getLastName());
        return new ResponseEntity<>(candidateDto, HttpStatus.OK);
    }
    @GetMapping("/skillName/{skillName}")
    public ResponseEntity<?> findCandidatesBySkillName(@PathVariable String skillName) {
        List<CandidateDto> candidatesDto = candidateService.findBySkillName(skillName);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }
}
