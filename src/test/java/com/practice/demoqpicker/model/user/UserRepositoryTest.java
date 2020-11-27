package com.practice.demoqpicker.model.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("특정 이메일로 인증을 받은 사용자 계정을 조회하는 기능을 테스트한다 ")
    @Test
    void testFindByEmailAndEmailVerifiedIsTrue() {
        //given
        User user = User.builder()
                .name("name")
                .userName("userName")
                .authType(AuthType.EMAIL)
                .email("email@naver.com")
                .password("password")
                .platform("plat")
                .uid("uid")
                .build();
        user.setEmailVerified(true);

        User savedUser = userRepository.save(user);

        //when
        User findEmail = userRepository.findByEmailAndEmailVerifiedIsTrue(user.getEmail()).get();

        //then
        assertThat(findEmail).isEqualTo(savedUser);
    }

    @DisplayName("특정 이메일로 인증 받은 계정이 존재하지 않는 경우 특정 이메일로 인증을 받은 사용자 계정을 조회하는 기능을 테스트한다")
    @Test
    void testFindByEmailAndEmailVerifiedIsTrueIfVerifiedEmailIsNotExist() {
        //given
        User user = User.builder()
                .name("name")
                .userName("userName")
                .authType(AuthType.EMAIL)
                .email("email@naver.com")
                .password("password")
                .platform("plat")
                .uid("uid")
                .build();
        userRepository.save(user);

        //when
        boolean foundVerifiedUser = userRepository.findByEmailAndEmailVerifiedIsTrue(user.getEmail()).isPresent();

        //then
        assertThat(foundVerifiedUser).isFalse();
    }

}
