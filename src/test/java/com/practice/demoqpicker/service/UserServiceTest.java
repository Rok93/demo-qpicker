package com.practice.demoqpicker.service;

import com.practice.demoqpicker.model.user.AuthType;
import com.practice.demoqpicker.model.user.User;
import com.practice.demoqpicker.model.user.UserRepository;
import com.practice.demoqpicker.web.dto.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("이메일로 인증받아 회원가입하는 기능을 테스트한다 ")
    @Test
    void testJoinToEmailTypeUser() {
        //given
        String name = "name";
        String email = "email@naver.com";
        String userName = "userName";
        String password = "password";
        AuthType authType = AuthType.EMAIL;
        String platform = "platform";
        UserSaveRequestDto userInfo = UserSaveRequestDto.builder()
                .name(name)
                .email(email)
                .userName(userName)
                .password(password)
                .authType(authType)
                .platform(platform)
                .build();

        //when
        User joinedUser = userService.join(userInfo);

        //then
        assertAll(
                () -> assertThat(joinedUser.getName()).isEqualTo(name),
                () -> assertThat(joinedUser.getEmail()).isEqualTo(email),
                () -> assertThat(joinedUser.getUserName()).isEqualTo(userName),
                () -> assertThat(joinedUser.getPassword()).isEqualTo(password),
                () -> assertThat(joinedUser.getAuthType()).isEqualTo(authType),
                () -> assertThat(joinedUser.getUid()).isNotNull(),
                () -> assertThat(joinedUser.getPlatform()).isEqualTo(platform)
        );
    }

    @DisplayName("이메일이 아닌 다른 방식으로 인증받아 회원가입하는 기능을 테스트한다 ")
    @Test
    void testJoinToNotEmailTypeUser() {
        //given
        String name = "name";
        String email = "email@naver.com";
        String platform = "platform";
        String thirdPartyUserId = "thirdPartyUserId";
        String thirdPartyAccessToken = "thirdPartyAccessToken";
        AuthType authType = AuthType.FACEBOOK;

        UserSaveRequestDto userInfo = UserSaveRequestDto.builder()
                .name(name)
                .email(email)
                .authType(authType)
                .platform(platform)
                .thirdPartyUserId(thirdPartyUserId)
                .thirdPartyAccessToken(thirdPartyAccessToken)
                .build();

        //when
        User joinedUser = userService.join(userInfo);

        //then
        assertAll(
                () -> assertThat(joinedUser.getName()).isEqualTo(name),
                () -> assertThat(joinedUser.getUserName()).isEqualTo(thirdPartyUserId),
                () -> assertThat(joinedUser.getAuthType()).isEqualTo(authType),
                () -> assertThat(joinedUser.getPlatform()).isEqualTo(platform),
                () -> assertThat(joinedUser.getUid()).isNotNull(),
                () -> assertThat(joinedUser.getThirdPartyUserId()).isEqualTo(thirdPartyUserId),
                () -> assertThat(joinedUser.getThirdPartyAccessToken()).isEqualTo(thirdPartyAccessToken)
        );
    }

    @DisplayName("이미 존재하는 userName을 입력받아 회원가입하면 예외가 발생한다 ")
    @Test
    void testJoinIfRequestDuplicatedUserName() {
        //given
        String duplicatedUserName = "userName";
        User savedUser = User.builder()
                .userName(duplicatedUserName)
                .build();
        userRepository.save(savedUser);

        UserSaveRequestDto userInfo = UserSaveRequestDto.builder()
                .name("name")
                .email("email@naver.com")
                .userName(duplicatedUserName)
                .password("password")
                .authType(AuthType.EMAIL)
                .platform("platform")
                .build();

        //when //then
        assertThatThrownBy(() -> userService.join(userInfo))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("탈퇴 후 정해진 기간 이후에 재가입을 할 때, 회원가입기능을 테스트한다 ")
    @Test
    void testJoinIfReJoin() {
        //given
        String name = "name";
        String email = "email@naver.com";
        String userName = "userName";
        String password = "password";
        AuthType authType = AuthType.EMAIL;
        String platform = "platform";

        User savedUser = User.builder().userName(userName).build();
        savedUser.setLeaved(true);
        savedUser.setLeavedAt(LocalDateTime.now().minusDays(30L));

        userRepository.save(savedUser);

        UserSaveRequestDto userInfo = UserSaveRequestDto.builder()
                .name(name)
                .email(email)
                .userName(userName)
                .password(password)
                .authType(authType)
                .platform(platform)
                .build();

        //when
        User joinedUser = userService.join(userInfo);

        //then
        assertAll(
                () -> assertThat(joinedUser.getName()).isEqualTo(name),
                () -> assertThat(joinedUser.getEmail()).isEqualTo(email),
                () -> assertThat(joinedUser.getUserName()).isEqualTo(userName),
                () -> assertThat(joinedUser.getPassword()).isEqualTo(password),
                () -> assertThat(joinedUser.getAuthType()).isEqualTo(authType),
                () -> assertThat(joinedUser.getUid()).isNotNull(),
                () -> assertThat(joinedUser.getPlatform()).isEqualTo(platform)
        );
    }

    @DisplayName("탈퇴 후 정해진 기간 이전에 재가입을 하면 예외가 발생한다 ")
    @Test
    void testJoinIfRejoinIfNotSatisfiedRejoinDuration() {
        //given
        String name = "name";
        String email = "email@naver.com";
        String userName = "userName";
        String password = "password";
        AuthType authType = AuthType.EMAIL;
        String platform = "platform";

        User savedUser = User.builder().userName(userName).build();
        savedUser.setLeaved(true);
        savedUser.setLeavedAt(LocalDateTime.now().minusDays(29L));

        userRepository.save(savedUser);

        UserSaveRequestDto userInfo = UserSaveRequestDto.builder()
                .name(name)
                .email(email)
                .userName(userName)
                .password(password)
                .authType(authType)
                .platform(platform)
                .build();

        //when //then
        assertThatThrownBy(() -> userService.join(userInfo))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
