package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterUILinks {
    private static GetterUILinks instance;
    private static AnnotationConfigApplicationContext context;

    private GetterUILinks() {
        context = new AnnotationConfigApplicationContext(
                UILinksSpringConfig.class);
    }

    public static GetterUILinks getInstance() {
        if (instance == null) {
            instance = new GetterUILinks();
        }
        return instance;
    }

    public void getBeanErrorStartUILink(String msg) {
        context.getBean(UILinksSpringConfig.NameBeans.BeanErrorStartUILink.getValue(),
                msg);
    }

    public void getBeanStartAuthenticationUILink() {
        context.getBean(UILinksSpringConfig.NameBeans.BeanStartAuthenticationUILink.getValue(),
                StartAuthenticationUILink.class);
    }
}