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

    // 기존 회원가입 로직
    @PostMapping("/users")
    public ResponseEntity join(@RequestBody UserSaveRequestDto requestDto) {
        try {
            return ResponseEntity.ok(userService.join(requestDto));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build(); // todo: 예외처리
        }
    }

    //    @DeleteMapping("/users/me") todo: uri 개선..하는게 낫지 않을까? /users 만해도 될 것 같다는 생각이 드는데...
    //    public ResponseEntity leave() {
    //        return ResponseEntity.ok(userService.);
    //    }
    //}

}
