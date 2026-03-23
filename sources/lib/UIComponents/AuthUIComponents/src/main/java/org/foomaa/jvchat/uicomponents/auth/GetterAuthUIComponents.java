package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterAuthUIComponents {
    private static GetterAuthUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private GetterAuthUIComponents() {
        context = null;
    }

    public static GetterAuthUIComponents getInstance() {
        if (instance == null) {
            instance = new GetterAuthUIComponents();
        }
        return instance;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }
}