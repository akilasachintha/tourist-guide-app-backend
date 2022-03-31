package com.datapirates.touristguideapp.controller;

import com.datapirates.touristguideapp.model.user.Guide;
import com.datapirates.touristguideapp.service.interfaces.GuideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api")
public class GuideController {
    private final GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PostMapping("/guide")
    public ResponseEntity<Guide> saveGuide(@Validated @RequestBody Guide guide){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/guide/save").toUriString());
        return ResponseEntity.created(uri).body(guideService.saveGuide(guide));
    }

    @GetMapping("/guide")
    public ResponseEntity<Map<String, List<Guide>>> getGuides(){
        log.info("Get guides");
        Map<String, List<Guide>> response = new HashMap<>();
        response.put("guides", guideService.getGuides());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/guide/update")
    public ResponseEntity<Guide> updateGuide(@RequestBody Guide guide){
        log.info("Successfully Updated Guide => " + guide);
        return ResponseEntity.ok().body(guideService.updateDriver(guide));
    }

    @DeleteMapping("/guide/delete/{id}")
    public String deleteGuide(@PathVariable Long id){
        log.info("Successfully deleted" + id);
        return guideService.deleteGuide(id);
    }
}
