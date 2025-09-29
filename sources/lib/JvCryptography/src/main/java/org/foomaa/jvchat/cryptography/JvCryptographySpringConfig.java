package org.foomaa.jvchat.cryptography;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


@Configuration
class JvCryptographySpringConfig {
    public enum NameBeans {
        BeanHashCryptography("beanHashCryptography");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanHashCryptography")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvHashCryptography beanHashCryptography() {
        return new JvHashCryptography();
    }
}