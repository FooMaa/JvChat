package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Configuration
public class JvGlobalDefinesSpringConfig {
    public enum NameBeans {
        BeanColorsAnsi("beanColorAnsi");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanColorAnsi")
    @Scope("singleton")
    public JvColorsAnsi beanColorAnsi() {
        return JvColorsAnsi.getInstance();
    }
}
