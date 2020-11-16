package com.practice.demoqpicker.web.dto;

import com.practice.demoqpicker.model.user.AuthType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String address;
    private AuthType authType;
    private String cellphone;
    private LocalDateTime createAt;
    private String email;
    private String message;
    private String name;
    private String password;
    private String profileImage;
    private String uid;
    private String userName;
    private Long lastAccessed;
    private LocalDateTime lastAccessedAt;
    private String memo;
    private String platform;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAccessAt;
    private String thirdPartyAccessToken;
    private String thirdPartyUserId;
    private String authKey;
    private Long roleId;
    private String deviceToken;

    @Builder
    public UserSaveRequestDto(AuthType authType, String platform, Long roleId, String uid, String name, String email,
            String userName, String password, String thirdPartyAccessToken, String thirdPartyUserId) {
        this.authType = authType;
        this.platform = platform;
        this.roleId = roleId;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.thirdPartyAccessToken = thirdPartyAccessToken;
        this.thirdPartyUserId = thirdPartyUserId;
    }
}

