package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalDefinesConfig {
    @Bean
    public ColorsAnsiGlobalDefines beanColorAnsiGlobalDefines() {
        return ColorsAnsiGlobalDefines.builder().build();
    }

    @Bean
    public DbGlobalDefines beanDbGlobalDefines() {
        return DbGlobalDefines.builder().build();
    }

    @Bean
    public FontsGlobalDefines beanFontsGlobalDefines() {
        return FontsGlobalDefines.builder().build();
    }

    @Bean
    public MainChatsGlobalDefines beanMainChatsGlobalDefines() {
        return MainChatsGlobalDefines.builder().build();
    }

    @Bean
    public MainGlobalDefines beanMainGlobalDefines() {
        return MainGlobalDefines.builder().build();
    }
}
