package com.realworld.global.config;


import com.realworld.feature.oauth.handler.OAuth2LoginFailureHandler;
import com.realworld.feature.oauth.handler.OAuth2SuccessHandler;
import com.realworld.feature.oauth.service.CustomOAuth2UserService;
import com.realworld.global.config.jwt.JwtAccessDeniedHandler;
import com.realworld.global.config.jwt.JwtAuthenticationEntryPoint;
import com.realworld.global.config.jwt.JwtSecurityConfig;
import com.realworld.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    String[] excludeDevURI = new String[]{"/api/v1/login", "/api/v1/member", "/api/v1/duplication-check/user-id/**", "/api/v1/auth/email", "/api/v1/auth/email/**", "/error", "/api/v1/reissue", "/api/v1/user/find-userId/**", "/api/v1/user/find-password/**", "/api/v1/", "/api/login/oauth2/code/kakao", "/login", "/auth/success", "/**", "/login/oauth2/code/naver", "/favicon.ico", "/api/prometheus", "/api/actuator/**" };
    String[] excludeLocalURI = new String[]{"/v1/login", "/v1/member", "/v1/duplication-check/user-id/**", "/v1/auth/email", "/v1/auth/email/**", "/error", "/v1/reissue", "/v1/user/find-userId/**", "/v1/user/find-password/**", "/login/oauth2/code/kakao", "/login", "/auth/success", "/**", "/login/oauth2/code/naver","/favicon.ico", "/api/actuator/**" };
    String[] getExcludeDevURI = new String[]{"/api/v1/cards", "/api/v1/cards/**", "/api/v1/file/**", "/api/actuator/**" };
    String[] getExcludeLocalURI = new String[]{"/api/v1/cards", "/api/v1/cards/**", "/api/v1/file/**", "/api/actuator/**" };

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                //.headers(headers->
                //        headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                //.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAccessDeniedHandler()))
                .sessionManagement((httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, getExcludeDevURI).permitAll()
                        .requestMatchers(HttpMethod.GET, getExcludeLocalURI).permitAll()
                        .requestMatchers(excludeDevURI).permitAll()
                        .requestMatchers(excludeLocalURI).permitAll()
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
                .apply(new JwtSecurityConfig(jwtTokenProvider));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
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
