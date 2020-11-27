package com.practice.demoqpicker.web.dto;

import com.practice.demoqpicker.model.user.AuthType;
import com.practice.demoqpicker.model.user.User;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String address;
    @Enumerated(EnumType.STRING)
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
    private boolean leaved;
    private LocalDateTime leavedAt;
    @Column(columnDefinition = "TEXT")
    private String memo;
    private String platform;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAccessAt;
    @Column(columnDefinition = "TEXT")
    private String thirdPartyAccessToken;
    private String thirdPartyUserId;
    private String authKey;
    private Long roleId;
    private boolean cellPhoneVerified;
    private boolean emailVerified;
    private String deviceToken;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.address = user.getAddress();
        this.authType = user.getAuthType();
        this.cellphone = user.getCellphone();
        this.createAt = user.getCreateAt();
        this.email = user.getEmail();;
        this.message = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.profileImage = user.getProfileImage();
        this.uid = user.getUid();
        this.userName = user.getUserName();
        this.lastAccessed = user.getLastAccessed();
        this.lastAccessedAt = user.getLastAccessedAt();
        this.leaved = user.isLeaved();
        this.leavedAt = user.getLeavedAt();
        this.memo = user.getMemo();
        this.platform = user.getPlatform();
        this.updatedAt = user.getUpdatedAt();
        this.lastAccessAt = user.getLastAccessAt();
        this.thirdPartyAccessToken = user.getThirdPartyAccessToken();
        this.thirdPartyUserId = user.getThirdPartyUserId();
        this.authKey = user.getAuthKey();
        this.roleId = user.getRoleId();
        this.cellPhoneVerified = user.isCellPhoneVerified();
        this.emailVerified = user.isEmailVerified();
        this.deviceToken = user.getDeviceToken();
    }
}
