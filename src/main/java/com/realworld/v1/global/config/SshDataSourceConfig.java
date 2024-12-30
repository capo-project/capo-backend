package com.realworld.v1.global.config;

import com.realworld.v1.global.config.ssh.SshTunnelingInitializer;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;

@Slf4j
@Validated
@Configuration
@Profile("local")
public class SshDataSourceConfig {

    private final String databaseUrl;
    private final Integer databasePort;

    private final SshTunnelingInitializer initializer;

    public SshDataSourceConfig(
            @NotNull @Value("${ssh.database_url}") String databaseUrl,
            @NotNull @Value("${ssh.database_port}")Integer databasePort,
            SshTunnelingInitializer initializer
    ) {
        this.databaseUrl = databaseUrl;
        this.databasePort = databasePort;
        this.initializer = initializer;
    }

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        Integer forwardPort = initializer.buildSshConnection(databaseUrl, databasePort);
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
