package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterUILinks {
    private static JvGetterUILinks instance;
    private static AnnotationConfigApplicationContext context;

    private JvGetterUILinks() {
        context = new AnnotationConfigApplicationContext(
                JvUILinksSpringConfig.class);
    }

    public static JvGetterUILinks getInstance() {
        if (instance == null) {
            instance = new JvGetterUILinks();
        }
        return instance;
    }

    public void getBeanErrorStartUILink(String msg) {
        context.getBean(JvUILinksSpringConfig.NameBeans.BeanErrorStartUILink.getValue(),
                msg);
    }

    public void getBeanStartAuthenticationUILink() {
        context.getBean(JvUILinksSpringConfig.NameBeans.BeanStartAuthenticationUILink.getValue(),
                JvStartAuthenticationUILink.class);
    }
}