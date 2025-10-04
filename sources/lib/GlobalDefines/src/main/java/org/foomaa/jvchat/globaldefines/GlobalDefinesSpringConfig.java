package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


@Configuration
class GlobalDefinesSpringConfig {
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
    public ColorsAnsiGlobalDefines beanColorAnsiGlobalDefines() {
        return new ColorsAnsiGlobalDefines();
    }

    @Bean(name = "beanMainGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MainGlobalDefines beanMainGlobalDefines() {
        return new MainGlobalDefines();
    }

    @Bean(name = "beanDbGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public DbGlobalDefines beanDbGlobalDefines() {
        return new DbGlobalDefines();
    }

    @Bean(name = "beanMainChatsGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MainChatsGlobalDefines beanMainChatsGlobalDefines() {
        return new MainChatsGlobalDefines();
    }

    @Bean(name = "beanFontsGlobalDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public FontsGlobalDefines beanFontsGlobalDefines() {
        return new FontsGlobalDefines();
    }
}