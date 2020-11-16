package com.practice.demoqpicker.service;

import com.practice.demoqpicker.model.museum.Museum;
import com.practice.demoqpicker.model.museum.MuseumRepository;
import com.practice.demoqpicker.model.position.Position;
import com.practice.demoqpicker.web.dto.MuseumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MuseumService {

    private final MuseumRepository museumRepository;

    @Transactional(readOnly = true)
    public List<MuseumResponseDto> findAllMuseumOrderByDistance(Position userLocation) {
        return museumRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(museum -> calculateDistance(museum, userLocation))) // 거리 측정하는 방법
                .map(MuseumResponseDto::new)
                .collect(Collectors.toList());
    }

    private double calculateDistance(Museum museum, Position position) {
        double latitudeGap = Math.abs(museum.getLatitude() - position.getLatitude());
        double longitudeGap = Math.abs(museum.getLongitude() - position.getLongitude());

        return Math.sqrt(latitudeGap + longitudeGap);
    }
}
