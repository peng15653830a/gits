package xyz.gits.boot.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.gits.boot.common.core.exception.handler.GlobalExceptionHandler;

/**
 * @author songyinyin
 * @date 2020/4/27 上午 11:53
 */
@Configuration
@EnableConfigurationProperties(GitsProperties.class)
public class GitsAutoConfiguration {
    /**
     * 用户加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 委托密码编码器，默认使用是 bcrypt 算法加密
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 设置默认加密算法
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        passwordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return passwordEncoder;
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
