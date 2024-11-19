package com.youragent.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class InitializerConfig extends AbstractHttpSessionApplicationInitializer {

    public InitializerConfig() {
        super(RedisConfig.class);
    }

}