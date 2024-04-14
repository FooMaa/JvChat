package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("org.foomaa.jvchat.ctrl")
public class JvControlsSpringConfig {

    @Bean(name = "initControls")
    @Scope("singleton")
    public JvInitControls initControls() {
        return JvInitControls.getInstance();
    }
}
