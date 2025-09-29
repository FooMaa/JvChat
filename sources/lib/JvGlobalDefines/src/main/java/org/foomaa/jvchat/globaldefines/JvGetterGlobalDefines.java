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

    public JvColorsAnsiGlobalDefines getBeanColorsAnsiGlobalDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanColorsAnsiGlobalDefines.getValue(), JvColorsAnsiGlobalDefines.class);
    }

    public JvMainGlobalDefines getBeanMainGlobalDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanMainGlobalDefines.getValue(), JvMainGlobalDefines.class);
    }

    public JvDbGlobalDefines getBeanDbGlobalDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanDbGlobalDefines.getValue(), JvDbGlobalDefines.class);
    }

    public JvMainChatsGlobalDefines getBeanMainChatsGlobalDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanMainChatsGlobalDefines.getValue(), JvMainChatsGlobalDefines.class);
    }

    public JvFontsGlobalDefines getBeanFontsGlobalDefines() {
        return context.getBean(JvGlobalDefinesSpringConfig.NameBeans.BeanFontsGlobalDefines.getValue(), JvFontsGlobalDefines.class);
    }
}