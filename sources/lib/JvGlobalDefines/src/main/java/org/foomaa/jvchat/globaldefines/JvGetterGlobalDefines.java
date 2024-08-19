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

    public JvColorsAnsiGlobalDefines getBeanColorsAnsiDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanColorsAnsiGlobalDefines.getValue(), JvColorsAnsiGlobalDefines.class);
    }

    public JvMainGlobalDefines getBeanMainDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanMainGlobalDefines.getValue(), JvMainGlobalDefines.class);
    }

    public JvDbGlobalDefines getBeanDbDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanDbGlobalDefines.getValue(), JvDbGlobalDefines.class);
    }
}
