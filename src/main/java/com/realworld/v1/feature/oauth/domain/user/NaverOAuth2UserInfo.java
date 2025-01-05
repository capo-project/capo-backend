//package com.realworld.feature.oauth.domain.user;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Map;
//
//@Slf4j
//public class NaverOAuth2UserInfo extends OAuth2UserInfo {
//    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
//        super(attributes);
//    }
//
//    @Override
//    public String getId() {
//
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//        if (response == null) {
//            return null;
//        }
//        log.info("id :: {}", response.get("id"));
//        return String.valueOf(response.get("id"));
//    }
//
//    @Override
//    public String getNickname() {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        if (response == null) {
//            return null;
//        }
//
//        return (String) response.get("nickname");
//    }
//
//    @Override
//    public String getUserEmail() {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        if (response == null) {
//            return null;
//        }
//
//        return (String) response.get("email");
//    }
//
//    @Override
//    public String getImageUrl() {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        if (response == null) {
//            return null;
//        }
//        return (String) response.get("profile_image");
//    }
//}
