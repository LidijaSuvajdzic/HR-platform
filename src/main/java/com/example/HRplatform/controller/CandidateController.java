package com.example.HRplatform.controller;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.NewCandidateDto;
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
    public ResponseEntity<?> addCandidate(@RequestBody NewCandidateDto newCandidateDto) {
        if (newCandidateDto != null){
            boolean isExists = candidateService.save(newCandidateDto);
            if (isExists) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else
                return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCandidate(@RequestBody CandidateDto candidateDto) {
        if (candidateDto != null){
                candidateService.updateCandidate(candidateDto);
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
        List<NewCandidateDto> candidatesDto = candidateService.findByFirstname(firstname);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> find(@RequestBody CandidateNameDto candidateNameDto) {
        NewCandidateDto newCandidateDto = candidateService.findByFirstnameAndLastname(candidateNameDto.getFirstName(), candidateNameDto.getLastName());
        return new ResponseEntity<>(newCandidateDto, HttpStatus.OK);
    }
    @GetMapping("/skillName/{skillName}")
    public ResponseEntity<?> findCandidatesBySkillName(@PathVariable String skillName) {
        List<NewCandidateDto> candidatesDto = candidateService.findBySkillName(skillName);
        return new ResponseEntity<>(candidatesDto, HttpStatus.OK);
    }
}
