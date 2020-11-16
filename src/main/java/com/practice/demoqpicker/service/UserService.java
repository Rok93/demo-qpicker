package com.practice.demoqpicker.service;

import com.practice.demoqpicker.model.user.AuthType;
import com.practice.demoqpicker.model.user.User;
import com.practice.demoqpicker.model.user.UserRepository;
import com.practice.demoqpicker.utils.BCryptPasswordEncoder;
import com.practice.demoqpicker.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(UserSaveRequestDto requestDto) { // 회원가입 프로세스!
        validateUserName(requestDto); //todo: 여기서 더 확장하면! 원하는 기능별로 예외처리를 분리할 수 있지 않을까?
        User user = createUser(requestDto);

        /*
        if (user.getCountryIsoCode() != null) { // (기존코드)해당 코드는 countryIsoCode가 어떤 부분에서 초기화 되었는지 알 수X
            user.setLastAccessedCountry(countryRepository.findByIsoCode(user.getCountryIsoCode()));
        }
        */

        User savedUser = userRepository.save(user);

        /* (기존 코드)
        if (user.getEmail() != null) {
            stibeeService.addSubscriber(savedUser.getEmail(), savedUser.getId());
        } // 구독관련 (메일이나 알림서비스를 위한..) 서비스인 것으로 추정 관련 정보X

        String authToken = tokenHandler.createTokenForUser(savedUser.getUid()); // User의 token 관련 정보X
        savedUser.setToken(authToken);
        */

        return savedUser;
    }

    private User createUser(UserSaveRequestDto requestDto) {
        if (requestDto.getAuthType().equals(AuthType.EMAIL)) { // 해당 메일로 인증받은 계정이 존재할 때 중복으로 인식
            validateEmail(requestDto);

            return User.builder()
                    .name(requestDto.getName())
                    .email(requestDto.getEmail())
                    .userName(requestDto.getUserName())
                    .password(new BCryptPasswordEncoder().encode(requestDto.getPassword()))
                    .authType(requestDto.getAuthType())
                    .platform(requestDto.getPlatform())
                    .uid(generateRandomUUID())
                    .build();
        }

        String name = requestDto.getName() != null ? requestDto.getName() : generateNewName(requestDto);
        User user = User.builder()
                .name(name)
                .email(enrollEmail(requestDto))
                .userName(requestDto.getThirdPartyUserId())
                .authType(requestDto.getAuthType())
                .platform(requestDto.getPlatform())
                .uid(generateRandomUUID())
                .thirdPartyUserId(requestDto.getThirdPartyUserId())
                .thirdPartyAccessToken(requestDto.getThirdPartyAccessToken())
                .build();

        if (requestDto.getCellphone() != null) { // 인증된 폰 번호 대입
            user.setCellphone(requestDto.getCellphone());
            user.setCellPhoneVerified(true);
        }

        return user;
    }

    private String generateNewName(UserSaveRequestDto requestDto) {
        return requestDto.getAuthType().name() + requestDto.getThirdPartyUserId();
    }

    private String generateRandomUUID() {
        while (true) {
            String uid = UUID.randomUUID().toString();
            if (isDuplicatedUid(uid)) {
                continue;
            }

            return uid;
        }
    }

    private boolean isDuplicatedUid(String uid) {
        return userRepository.findByUid(uid).isPresent();
    }

    private String enrollEmail(UserSaveRequestDto requestDto) {
        return isAlreadyVerifiedEmail(requestDto.getEmail()) ? null : requestDto.getEmail();
    }

    private void validateEmail(UserSaveRequestDto requestDto) {
        if (isAlreadyVerifiedEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 인증에 사용되었던 이메일입니다.");
        }
    }

    private boolean isAlreadyVerifiedEmail(String email) { // todo: isPresent()를 없애는 방법 없을까? )
        return userRepository.findByEmailAndEmailVerifiedIsTrue(email).isPresent();
    }

    private void validateUserName(UserSaveRequestDto requestDto) { // todo:  (+ Controller에서 try catch문으로 예외처리해주기!)
        List<User> findUsers = userRepository.findAllByUserName(requestDto.getUserName());

        if (!findUsers.isEmpty()) {
            User findUser = findUsers.get(0);

            validateDuplicateUserName(findUser);
            validateRejoinPermitDuration(findUser);
        }
    }

    private void validateRejoinPermitDuration(User findUser) {
        LocalDateTime userLeavedAt = findUser.getLeavedAt();
        LocalDateTime rejoinPermitDate = userLeavedAt.plusDays(User.REJOIN_DURATION);

        if (LocalDateTime.now().isAfter(rejoinPermitDate)) {
            throw new IllegalArgumentException("탈퇴 후 30일이 지나야 재가입할 수 있습니다.");
        }
    }

    private void validateDuplicateUserName(User findUser) {
        if (!findUser.isLeaved()) {
            throw new IllegalArgumentException("동일한 사용자 이름이 존재합니다.");
        }
    }

    //    @Transactional
    //    public void deleteUser() {
    //        //         탈퇴 로직
    //        if (!isLoggedIn()) {
    //            return CommonResponse.failWithMessage("로그인이 필요한 서비스입니다.");
    //        }
    //
    //        //         기존 탈퇴 로직
    //        User user = userRepository.getOne(getSessionUserId());
    //
    //        try {
    //            if (user != null) {
    //                String authType = user.getAuthType().name();
    //                switch (authType.toLowerCase()) {
    //                    case "kakao":
    //                        HttpHeaders headers = new HttpHeaders();
    //                        headers.setContentType(MediaType.APPLICATION_JSON);
    //                        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //                        headers.set("Authorization", "KakaoAK " + kakaoRestKey);
    //
    //                        HttpEntity<Map<String, Object>> request = new HttpEntity<>(null, headers);
    //                        String url = "https://kapi.kakao.com/v1/user/unlink?target_id_type=user_id&target_id=" + user.getThirdPartyUserId();
    //
    //                        RestTemplate restTemplate = new RestTemplateBuilder().build();
    //                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
    //                                String.class);
    //                        break;
    //                    case "facebook":
    //                        headers = new HttpHeaders();
    //                        headers.setContentType(MediaType.APPLICATION_JSON);
    //                        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //
    //                        request = new HttpEntity<>(null, headers);
    //                        String getTokenUrl = "https://graph.facebook.com/oauth/access_token?client_id=" + facebookAppId + "&client_secret=" + facebookAppSecret + "&grant_type=client_credentials";
    //
    //                        restTemplate = new RestTemplateBuilder().build();
    //                        ResponseEntity<String> tokenResponseEntity = restTemplate.exchange(getTokenUrl, HttpMethod.POST,
    //                                request, String.class);
    //
    //                        JSONParser jsonParser = new JSONParser();
    //                        JSONObject jsonObject = (JSONObject) jsonParser.parse(tokenResponseEntity.getBody());
    //                        String accessToken = (String) jsonObject.get("access_token");
    //
    //                        String deleteUserUrl = "https://graph.facebook.com/" + user.getThirdPartyUserId() + "/permissions?access_token=" + accessToken;
    //
    //                        ResponseEntity<String> deleteUserResponseEntity = restTemplate.exchange(deleteUserUrl,
    //                                HttpMethod.DELETE, request, String.class);
    //                        break;
    //                    default:
    //                        break;
    //                }
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return CommonResponse.failWithMessage("써드파티 연동해제에 실패하였습니다.");
    //        }
    //
    //        // User Entity에 leave() 메소드를 만들어서 이 내용을 메소드 하나로 퉁치게 만들자!
    //        user.setUserName(null);
    //        user.setPassword(null);
    //        user.setThirdPartyUserId(null);
    //        user.setEmail(null);
    //        user.setLeaved(true);
    //        user.setLeavedAt(LocalDateTime.now());
    //        User leavedUser = userRepository.save(user);
    //
    //        //탈퇴 시 자식댓글이 없을 경우 댓글 모두 삭제(row)(정욱)
    //        List<PickCastComment> pickCastCommentList = pickastCommentRepository.findAllByUserId(user.getId());
    //        for (PickCastComment comment : pickCastCommentList) {
    //
    //            if (comment.getChildCommentCount() == 0) {
    //                pickastCommentRepository.deleteById(comment.getId());
    //            } else {
    //                comment.setDeleted(true);
    //            }
    //
    //            if (comment.getParentId() != null) {
    //                PickCastComment parentComment = pickCastCommentRepository.getOne(comment.getParentId());
    //                if (parentComment.getChildCommentCount() == 1 && parentComment.isDeleted()) {
    //                    pickastCommentRepository.deleteById(parentComment.getId());
    //                }
    //            }
    //        }
    //    }

}
