package com.realworld.feature.oauth.service;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.member.repository.MemberRepository;
import com.realworld.feature.oauth.domain.CustomOAuth2User;
import com.realworld.feature.oauth.domain.OAuthAttributes;
import com.realworld.global.category.SocialType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.realworld.global.category.SocialType.KAKAO;
import static com.realworld.global.category.SocialType.NAVER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("로그인 요청 진입");
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해  DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser() 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보냄
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * userRequest에서 registrationId 추출 후, registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId :: {} ", registrationId);
        SocialType socialType = getSocialType(registrationId);
        log.info("socialType :: {}", socialType);
        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json (유저 정보들)

        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        // socialType에 따라 유저정보를 통해 OAuthAttributes 객체 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        Member createMember = getMember(extractAttributes, socialType);

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createMember.getAuthority().toString())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createMember.getUserEmail(),
                createMember.getAuthority()
        );
    }

    /**
     * TODO: SocialType,
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
     * 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 save를 통하여 회원을 저장한다.
     *
     * @param attributes
     * @param socialType
     * @return
     */
    private Member getMember(OAuthAttributes attributes, SocialType socialType) {
        log.info("attributes :: {}", attributes);

        MemberJpaEntity findMemberEntity = memberRepository.findById(attributes.getOAuth2UserInfo().getId()).orElse(null);

        if (findMemberEntity == null) {
            return saveMember(attributes, socialType);
        }
        return findMemberEntity.toDomain();
    }

    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member createdMember = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());

        return memberRepository.save(createdMember.toEntity()).toDomain();
    }

    private SocialType getSocialType(String registrationId) {

        if (registrationId.toUpperCase().equals("KAKAO")) {
            return SocialType.KAKAO;
        }

        return SocialType.GOOGLE;
    }
}
