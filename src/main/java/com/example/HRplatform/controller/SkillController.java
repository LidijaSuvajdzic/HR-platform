package com.example.HRplatform.controller;

import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.mappers.SkillMapper;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @PostMapping("/")
    public ResponseEntity<SkillDto> create(@RequestBody @Valid SkillDto skillDto){
        Skill newSkill = skillService.save(skillMapper.map(skillDto));
        final String getLocation = "/api/skill/" + newSkill.getName();
        return ResponseEntity.created(URI.create(getLocation)).body(skillMapper.map(newSkill));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<SkillDto> delete(@PathVariable String name) {
        return ResponseEntity.ok(skillMapper.map(skillService.delete(name)));
    }

    @GetMapping("/")
    public ResponseEntity<List<SkillDto>> findAll() {
        return ResponseEntity.ok(skillService.findAll()
                                                 .stream()
                                                 .map(skillMapper::map)
                                                 .toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<SkillDto> getByName(@PathVariable String name) {
        return ResponseEntity.ok(skillMapper.map(skillService.getByName(name)));
    }
}
