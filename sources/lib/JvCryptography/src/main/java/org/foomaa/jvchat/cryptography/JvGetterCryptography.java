package org.foomaa.jvchat.cryptography;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterCryptography {
    private static JvGetterCryptography instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterCryptography() {
        context = new AnnotationConfigApplicationContext(
                JvCryptographySpringConfig.class);
    }

    public static JvGetterCryptography getInstance() {
        if (instance == null) {
            instance = new JvGetterCryptography();
        }
        return instance;
    }

    public JvHashCryptography getBeanHashCryptography() {
        return context.getBean(JvCryptographySpringConfig.NameBeans.BeanHashCryptography.getValue(),
                JvHashCryptography.class);
    }
}