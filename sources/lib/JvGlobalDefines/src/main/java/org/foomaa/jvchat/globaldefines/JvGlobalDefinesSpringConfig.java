package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


@Configuration
class JvGlobalDefinesSpringConfig {
    public enum NameBeans {
        BeanMainGlobalDefines("beanMainGlobalDefines"),
        BeanColorsAnsiGlobalDefines("beanColorAnsiGlobalDefines"),
        BeanDbGlobalDefines("beanDbGlobalDefines"),
        BeanMainChatsGlobalDefines("beanMainChatsGlobalDefines"),
        BeanFontsGlobalDefines("beanFontsGlobalDefines");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanColorAnsiGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvColorsAnsiGlobalDefines beanColorAnsiGlobalDefines() {
        return new JvColorsAnsiGlobalDefines();
    }

    @Bean(name = "beanMainGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainGlobalDefines beanMainGlobalDefines() {
        return new JvMainGlobalDefines();
    }

    @Bean(name = "beanDbGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvDbGlobalDefines beanDbGlobalDefines() {
        return new JvDbGlobalDefines();
    }

    @Bean(name = "beanMainChatsGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainChatsGlobalDefines beanMainChatsGlobalDefines() {
        return new JvMainChatsGlobalDefines();
    }

    @Bean(name = "beanFontsGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvFontsGlobalDefines beanFontsGlobalDefines() {
        return new JvFontsGlobalDefines();
    }
}