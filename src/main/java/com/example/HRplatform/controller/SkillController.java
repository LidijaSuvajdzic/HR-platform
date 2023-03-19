package com.example.HRplatform.controller;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody SkillDto skillDto){
         skillService.save(skillDto);
         return new ResponseEntity<>(HttpStatus.OK);
    }

}
