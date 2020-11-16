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
    private LocalDateTime createAt;
    private String email;
    private String message;
    private String name;
    private String password;
    private String profileImage;

    private String uid;

    @Column(unique = true)
    private String userName;
    private Long lastAccessed; // todo: 풀 네임 모르겠음 (마지막 접근 국가 식벽자)
    private LocalDateTime lastAccessedAt;

    private boolean leaved = false; // 탈퇴 여부
    private LocalDateTime leavedAt; // 탈퇴 시각

    @Column(columnDefinition = "TEXT")
    private String memo; // 메모
    private String platform; // 마지막 로그인 플랫폼 구분 ...?
    private LocalDateTime updatedAt; // 마지막 수정 시간
    private LocalDateTime lastAccessAt; // 마지막 접근 시간

    @Column(columnDefinition = "TEXT")
    private String thirdPartyAccessToken; // thirdparty로 가입했을 경우 토큰 (페이스북, 카카오톡)
    private String thirdPartyUserId; // thirdparty로 가입했을 경우 식별자
    private String authKey;
    private Long roleId; // 권한 (권한은 BIGINT 타입으로 주어져있는데, 음... 이 의미를 잘 모르겠음)
    private boolean cellPhoneVerified = false; // 휴대폰 인증 여부
    private boolean emailVerified = false; // 이메일 인증 여부
    private String deviceToken; // FCM 디바이스 토큰

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
