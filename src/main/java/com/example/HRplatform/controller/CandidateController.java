package com.example.HRplatform.controller;
import com.example.HRplatform.dto.*;
import com.example.HRplatform.mappers.CandidateMapper;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping(value="api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    @PostMapping("/")
    public ResponseEntity<CandidateDto> create(@RequestBody @Valid CreateCandidateDto createCandidateDto){
        final Candidate candidate = candidateService.create(candidateMapper.map(createCandidateDto));
        final String getLocation = "/api/candidates/" + candidate.getUuid();
        return ResponseEntity.created(URI.create(getLocation)).body(candidateMapper.map(candidate));
    }

    @PutMapping("/")
    public ResponseEntity<CandidateDto> update(@RequestBody @Valid CandidateDto candidateDto) {
        return ResponseEntity.ok(candidateMapper.map(candidateService.update(candidateDto)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<CandidateDto> delete(@PathVariable UUID uuid) {
        return ResponseEntity.ok(candidateMapper.map(candidateService.delete(uuid)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CandidateDto> getByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(candidateMapper.map(candidateService.getByUuid(uuid)));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<CandidateDto>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(candidateService.findAllByName(name)
                .stream()
                .map(candidateMapper::map)
                .toList());
    }

    @GetMapping("/")
    public ResponseEntity<List<CandidateDto>> findAll() {
        return ResponseEntity.ok(candidateService.findAll()
                .stream()
                .map(candidateMapper::map)
                .toList());
    }

    @GetMapping("/bySkills/")
    public ResponseEntity<List<CandidateDto>> findAllBySkills(@RequestBody List<String> skillNames) {
        return ResponseEntity.ok(candidateService.findAllBySkills(skillNames)
                                .stream()
                                .map(candidateMapper::map)
                                .toList());
    }

}
