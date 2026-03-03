package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterSettings {
    private static GetterSettings instance;
    private final AnnotationConfigApplicationContext context;

    private GetterSettings() {
        context = new AnnotationConfigApplicationContext(
                SettingsSpringConfig.class);
    }

    public static GetterSettings getInstance() {
        if (instance == null) {
            instance = new GetterSettings();
        }
        return instance;
    }

    public DisplaySettings getBeanDisplaySettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanDisplaySettings.getValue(), DisplaySettings.class);
    }
}