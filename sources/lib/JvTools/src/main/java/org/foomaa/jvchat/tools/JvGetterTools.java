package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JvGetterTools {
    private static JvGetterTools instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterTools() {
        context = new AnnotationConfigApplicationContext(
                JvToolsSpringConfig.class);
    }

    public static JvGetterTools getInstance() {
        if (instance == null) {
            instance = new JvGetterTools();
        }
        return instance;
    }

    public JvMainTools getBeanMainTools() {
        return context.getBean(JvToolsSpringConfig.NameBeans.BeanMainTools.getValue(), JvMainTools.class);
    }
}
