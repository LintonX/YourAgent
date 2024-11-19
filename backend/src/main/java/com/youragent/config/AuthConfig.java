package com.youragent.config;

import com.youragent.dao.authdao.AuthDaoImpl;
import com.youragent.model.Agent;
import com.youragent.service.agentservice.AgentServiceImpl;
import com.youragent.service.authservice.AuthServiceImpl;
import com.youragent.service.redisservice.RedisService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public AuthServiceImpl authService(@NonNull final AuthDaoImpl authDao,
                                       @NonNull final AgentServiceImpl agentService,
                                       @NonNull final RedisService<String, Agent> redisService,
                                       @NonNull final BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new AuthServiceImpl(authDao, agentService, redisService, bCryptPasswordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
