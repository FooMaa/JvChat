package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterSettings {
    private static JvGetterSettings instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterSettings() {
        context = new AnnotationConfigApplicationContext(
                JvSettingsSpringConfig.class);
    }

    public static JvGetterSettings getInstance() {
        if (instance == null) {
            instance = new JvGetterSettings();
        }
        return instance;
    }

    public JvMainSettings getBeanMainSettings() {
        return context.getBean(JvSettingsSpringConfig.NameBeans.BeanMainSettings.getValue(), JvMainSettings.class);
    }

    public JvDisplaySettings getBeanDisplaySettings() {
        return context.getBean(JvSettingsSpringConfig.NameBeans.BeanDisplaySettings.getValue(), JvDisplaySettings.class);
    }

    public JvUsersInfoSettings getBeanUsersInfoSettings() {
        return context.getBean(JvSettingsSpringConfig.NameBeans.BeanUsersInfoSettings.getValue(), JvUsersInfoSettings.class);
    }

    public JvServersInfoSettings getBeanServersInfoSettings() {
        return context.getBean(JvSettingsSpringConfig.NameBeans.BeanServersInfoSettings.getValue(), JvServersInfoSettings.class);
    }
}