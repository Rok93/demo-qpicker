package com.practice.demoqpicker.service;

import com.practice.demoqpicker.model.museum.Museum;
import com.practice.demoqpicker.model.museum.MuseumRepository;
import com.practice.demoqpicker.model.position.Position;
import com.practice.demoqpicker.web.dto.MuseumResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MuseumServiceTest {

    @Autowired
    MuseumRepository museumRepository;

    @Autowired
    MuseumService museumService;

    @AfterEach
    void tearDown() {
        museumRepository.deleteAll();
    }

    @DisplayName("현재 위치에서 가장 가까운 박물관 순서로 나열하는 기능을 테스트한다 ")
    @Test
    void testFindAllMuseumOrderByDistance() {
        //given
        Museum museumA = museumRepository.save(Museum.builder().name("museumA").latitude(1.0).longitude(1.0).build());

        Museum museumB = museumRepository.save(Museum.builder().name("museumB").latitude(2.0).longitude(2.0).build());

        Position positionNearByMuseumA = new Position(0.0, 0.0);
        Position positionNearByMuseumB = new Position(2.0, 2.0);

        //when
        List<MuseumResponseDto> resultsNearByMuseumA =
                museumService.findAllMuseumOrderByDistance(positionNearByMuseumA);
        List<MuseumResponseDto> resultsNearByMuseumB =
                museumService.findAllMuseumOrderByDistance(positionNearByMuseumB);

        //then
        assertThat(resultsNearByMuseumA).usingFieldByFieldElementComparator()
                .containsExactly(new MuseumResponseDto(museumA), new MuseumResponseDto(museumB));

        assertThat(resultsNearByMuseumB).usingFieldByFieldElementComparator()
                .containsExactly(new MuseumResponseDto(museumB), new MuseumResponseDto(museumA));
    }
}
