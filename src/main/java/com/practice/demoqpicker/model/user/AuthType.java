package com.practice.demoqpicker.model.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthType {
    FACEBOOK("facebook"), KAKAO("kakao"), EMAIL("email");

    private final String key;
}
