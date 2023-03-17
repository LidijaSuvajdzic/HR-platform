package com.example.HRplatform.controller;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
