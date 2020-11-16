package com.practice.demoqpicker.web.dto;

import com.practice.demoqpicker.model.museum.Museum;
import lombok.Getter;

@Getter
public class MuseumResponseDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    public MuseumResponseDto(Museum entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
