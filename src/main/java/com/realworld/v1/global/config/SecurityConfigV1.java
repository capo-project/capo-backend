package com.realworld.v1.global.config;


import com.realworld.v1.feature.oauth.handler.OAuth2LoginFailureHandler;
import com.realworld.v1.feature.oauth.handler.OAuth2SuccessHandler;
import com.realworld.v1.feature.oauth.service.CustomOAuth2UserService;
import com.realworld.v1.global.config.jwt.JwtAccessDeniedHandler;
import com.realworld.v1.global.config.jwt.JwtAuthenticationEntryPoint;
import com.realworld.v1.global.config.jwt.JwtSecurityConfig;
import com.realworld.v1.global.config.jwt.JwtTokenProviderV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableWebMvc
public class SecurityConfigV1 {
    private final JwtTokenProviderV1 jwtTokenProviderV1;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    private final String[] exclude = new String[]{"/api/v1/login", "/api/v1/member", "/api/v1/duplication-check/user-id/**", "/api/v1/auth/email", "/api/v1/auth/email/**", "/error", "/api/v1/reissue", "/api/v1/user/find-userId/**", "/api/v1/user/find-password/**", "/api/v1/", "/api/login/oauth2/code/kakao", "/login", "/auth/success", "/**", "/login/oauth2/code/naver", "/favicon.ico", "/api/prometheus", "/api/actuator/**"};


    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain filterChainV1(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/v1")
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSourceV1()))
                //.headers(headers->
                //        headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                //.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAccessDeniedHandler()))
                .sessionManagement((httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(exclude).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(oauth ->
                        oauth
                                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/api/login/oauth2/code/*"))
                                .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)

                )
                .apply(new JwtSecurityConfig(jwtTokenProviderV1));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSourceV1() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
