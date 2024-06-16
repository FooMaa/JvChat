package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JvGetterGlobalDefines {
    private static JvGetterGlobalDefines instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterGlobalDefines() {
        context = new AnnotationConfigApplicationContext(
                JvGlobalDefinesSpringConfig.class);
    }

    public static JvGetterGlobalDefines getInstance() {
        if (instance == null) {
            instance = new JvGetterGlobalDefines();
        }
        return instance;
    }

    public JvColorsAnsi getBeanColorsAnsi() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanColorsAnsi.getValue(), JvColorsAnsi.class);
    }

    public JvMainDefines getBeanMainDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanMainDefines.getValue(), JvMainDefines.class);
    }
}
