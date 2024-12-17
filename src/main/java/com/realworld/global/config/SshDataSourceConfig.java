package com.realworld.global.config;

import com.realworld.global.config.ssh.SshTunnelingInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Slf4j
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class SshDataSourceConfig {

    private final SshTunnelingInitializer initializer;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        Integer forwardPort = initializer.buildSshConnection();
        String url = properties.getUrl().replace("[forwardedPort]", Integer.toString(forwardPort));
        log.info("url={}",url);
        return DataSourceBuilder.create()
                .url(url)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .driverClassName(properties.getDriverClassName())
                .build();
    }

}
