package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterUiLinks {
    private static JvGetterUiLinks instance;
    private static AnnotationConfigApplicationContext context;

    private JvGetterUiLinks() {
        context = new AnnotationConfigApplicationContext(
                JvUiLinksSpringConfig.class);
    }

    public static JvGetterUiLinks getInstance() {
        if (instance == null) {
            instance = new JvGetterUiLinks();
        }
        return instance;
    }

    public void getBeanErrorStart(String msg) {
        context.getBean(JvUiLinksSpringConfig.NameBeans.BeanErrorStart.getValue(),
                msg);
    }

    public void getBeanStartAuthentication() {
        context.getBean(JvUiLinksSpringConfig.NameBeans.BeanStartAuthentication.getValue(),
                JvStartAuthentication.class);
    }
}
