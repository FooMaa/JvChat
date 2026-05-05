package org.foomaa.jvchat.signals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignalsConfig {
    @Bean
    public SignalFactory beanSignalFactory() {
        return SignalFactory.builder().build();
    }
}
