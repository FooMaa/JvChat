package org.foomaa.jvchat.cryptography;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptographyConfig {
    @Bean
    public HashCryptography beanHashCryptography() {
        return HashCryptography.builder().build();
    }
}
