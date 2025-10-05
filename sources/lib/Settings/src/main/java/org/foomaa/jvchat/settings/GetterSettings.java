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

    public MainSettings getBeanMainSettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanMainSettings.getValue(), MainSettings.class);
    }

    public DisplaySettings getBeanDisplaySettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanDisplaySettings.getValue(), DisplaySettings.class);
    }

    public UsersInfoSettings getBeanUsersInfoSettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanUsersInfoSettings.getValue(), UsersInfoSettings.class);
    }

    public ServersInfoSettings getBeanServersInfoSettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanServersInfoSettings.getValue(), ServersInfoSettings.class);
    }

    public UISettings getBeanUISettings() {
        return context.getBean(SettingsSpringConfig.NameBeans.BeanUISettings.getValue(), UISettings.class);
    }
}