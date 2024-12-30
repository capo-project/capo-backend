package com.realworld.v1.global.config.redis;

import com.realworld.v1.global.config.ssh.SshTunnelingInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class SshDataLocalConfig extends RedisConnectionFactoryConfig {

    public final SshTunnelingInitializer sshTunnelingInitializer;

    @Bean
    @Override
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        Integer forwardedPort = sshTunnelingInitializer.buildSshConnection(redisProperties.getHost(), redisProperties.getPort());

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(forwardedPort);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

}
