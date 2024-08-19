package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Configuration
public class JvGlobalDefinesSpringConfig {
    public enum NameBeans {
        BeanMainGlobalDefines("beanMainGlobalDefines"),
        BeanColorsAnsiGlobalDefines("beanColorAnsiGlobalDefines"),
        BeanDbGlobalDefines("beanDbGlobalDefines");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanColorAnsiGlobalDefines")
    @Scope("singleton")
    public JvColorsAnsiGlobalDefines beanColorAnsiGlobalDefines() {
        return JvColorsAnsiGlobalDefines.getInstance();
    }

    @Bean(name = "beanMainGlobalDefines")
    @Scope("singleton")
    public JvMainGlobalDefines beanMainGlobalDefines() {
        return JvMainGlobalDefines.getInstance();
    }

    @Bean(name = "beanDbGlobalDefines")
    @Scope("singleton")
    public JvDbGlobalDefines beanDbGlobalDefines() {
        return JvDbGlobalDefines.getInstance();
    }
}
