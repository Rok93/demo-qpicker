package com.practice.demoqpicker.model.position;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private double latitude;
    private double longitude;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
