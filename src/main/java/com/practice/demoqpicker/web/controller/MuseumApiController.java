package com.practice.demoqpicker.web.controller;

import com.practice.demoqpicker.service.MuseumService;
import com.practice.demoqpicker.model.position.Position;
import com.practice.demoqpicker.web.dto.MuseumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MuseumApiController {

    private final MuseumService museumService;

    @GetMapping("/museums")
    public ResponseEntity<List<MuseumResponseDto>> findAllMuseums(@RequestBody Position position) {
        List<MuseumResponseDto> museums = museumService.findAllMuseumOrderByDistance(position);
        return ResponseEntity.ok(museums);
    }
}
