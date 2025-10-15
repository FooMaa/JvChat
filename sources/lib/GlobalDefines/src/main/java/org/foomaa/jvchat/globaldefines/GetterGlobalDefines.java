package org.foomaa.jvchat.globaldefines;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterGlobalDefines {
    private static GetterGlobalDefines instance;
    private final AnnotationConfigApplicationContext context;

    private GetterGlobalDefines() {
        context = new AnnotationConfigApplicationContext(
                GlobalDefinesSpringConfig.class);
    }

    public static GetterGlobalDefines getInstance() {
        if (instance == null) {
            instance = new GetterGlobalDefines();
        }
        return instance;
    }

    public ColorsAnsiGlobalDefines getBeanColorsAnsiGlobalDefines() {
        return context.getBean(GlobalDefinesSpringConfig.NameBeans.BeanColorsAnsiGlobalDefines.getValue(), ColorsAnsiGlobalDefines.class);
    }

    public MainGlobalDefines getBeanMainGlobalDefines() {
        return context.getBean(GlobalDefinesSpringConfig.NameBeans.BeanMainGlobalDefines.getValue(), MainGlobalDefines.class);
    }

    public DbGlobalDefines getBeanDbGlobalDefines() {
        return context.getBean(GlobalDefinesSpringConfig.NameBeans.BeanDbGlobalDefines.getValue(), DbGlobalDefines.class);
    }

    public MainChatsGlobalDefines getBeanMainChatsGlobalDefines() {
        return context.getBean(GlobalDefinesSpringConfig.NameBeans.BeanMainChatsGlobalDefines.getValue(), MainChatsGlobalDefines.class);
    }

    public FontsGlobalDefines getBeanFontsGlobalDefines() {
        return context.getBean(GlobalDefinesSpringConfig.NameBeans.BeanFontsGlobalDefines.getValue(), FontsGlobalDefines.class);
    }
}