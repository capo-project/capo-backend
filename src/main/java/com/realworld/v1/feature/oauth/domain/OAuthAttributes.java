package com.realworld.feature.oauth.domain;


import com.realworld.feature.auth.Authority;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.oauth.domain.user.GoogleOAuth2UserInfo;
import com.realworld.feature.oauth.domain.user.KakaoOAuth2UserInfo;
import com.realworld.feature.oauth.domain.user.OAuth2UserInfo;
import com.realworld.global.category.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 클래스
 */
@Getter
@Slf4j
public class OAuthAttributes {
    private final String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private final OAuth2UserInfo oAuth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 소설별 of 메소드(ofGoogle, ofKakao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
     * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
     */

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        log.info("Extracting OAuthAttributes for socialType: {}", socialType);
        log.info("attributes :: {}", attributes);

        if (socialType == SocialType.KAKAO) {
            log.info("forKakao");
            return ofKakao(userNameAttributeName, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

//    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .nameAttributeKey(userNameAttributeName)
//                .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
//                .build();
//    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .userId(oAuth2UserInfo.getId())
                .userEmail(oAuth2UserInfo.getUserEmail())
                .oauthImage(oAuth2UserInfo.getImageUrl())
                .nickname(oAuth2UserInfo.getNickname())
                .authority(Authority.ROLE_USER)
                .build();
    }
}
