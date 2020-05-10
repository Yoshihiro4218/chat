package com.yoshi.chat.app.config;

import com.yoshi.chat.domain.repository.typehandler.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.*;
import org.mybatis.spring.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

import java.time.*;

@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return config -> {
            TypeHandlerRegistry reg = config.getTypeHandlerRegistry();
//            reg.register(Xxxxxx.YyyyyStatus.class, new CodeEnumTypeHandler<Xxxxxx.YyyyyStatus>(Xxxxx.YyyyyStatus.class) {
//            });
            reg.register(LocalDateTime.class, LocalDateTimeTypeHandler.class);
        };
    }
}
