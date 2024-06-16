package org.foomaa.jvchat.globaldefs;

import org.springframework.context.annotation.*;

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
