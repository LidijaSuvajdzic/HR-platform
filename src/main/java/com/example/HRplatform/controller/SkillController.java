package com.example.HRplatform.controller;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/skill")
public class SkillController {
    private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Skill> save(@RequestBody SkillDto skillDto){
        if (skillDto != null){
            skillService.save(skillDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
