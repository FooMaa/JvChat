package org.foomaa.jvchat.signals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SignalsConfig {
    @Bean
    @Scope("prototype")
    public Signal beanEvent() {
        return Signal.builder().build();
    }

    @Bean
    @Scope("prototype")
    public SignalFactory beanEventFactory() {
        return SignalFactory.builder().build();
    }
}
