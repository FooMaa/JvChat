package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("org.foomaa.jvchat.ctrl")
public class JvControlsSpringConfig {
    @Bean
    @Scope("singleton")
    public JvInitControls initControls() {
        return JvInitControls.getInstance();
    }
}
