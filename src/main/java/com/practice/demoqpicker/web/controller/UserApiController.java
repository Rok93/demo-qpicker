package com.practice.demoqpicker.web.controller;

import com.practice.demoqpicker.service.UserService;
import com.practice.demoqpicker.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity join(@RequestBody UserSaveRequestDto requestDto) {
        try {
            return ResponseEntity.ok(userService.join(requestDto));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
