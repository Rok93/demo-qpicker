package com.practice.demoqpicker.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    public static final long REJOIN_DURATION = 30L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private String cellphone;
    private LocalDateTime createAt = LocalDateTime.now();
    private String email;
    private String message;
    private String name;
    private String password;
    private String profileImage;
    private String uid;
    private String userName;
    private Long lastAccessed;
    private LocalDateTime lastAccessedAt;
    private boolean leaved = false;
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
    private boolean cellPhoneVerified = false;
    private boolean emailVerified = false;
    private String deviceToken;

    @Builder
    public User(AuthType authType, String platform, Long roleId, String uid, String name, String email, String userName,
            String password, String thirdPartyUserId, String thirdPartyAccessToken) {
        this.authType = authType;
        this.platform = platform;
        this.roleId = roleId;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.thirdPartyUserId = thirdPartyUserId;
        this.thirdPartyAccessToken = thirdPartyAccessToken;
    }
}
