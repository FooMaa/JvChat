package org.foomaa.jvchat.cryptography;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterCryptography {
    private static GetterCryptography instance;
    private final AnnotationConfigApplicationContext context;

    private GetterCryptography() {
        context = new AnnotationConfigApplicationContext(
                CryptographySpringConfig.class);
    }

    public static GetterCryptography getInstance() {
        if (instance == null) {
            instance = new GetterCryptography();
        }
        return instance;
    }

    public HashCryptography getBeanHashCryptography() {
        return context.getBean(CryptographySpringConfig.NameBeans.BeanHashCryptography.getValue(),
                HashCryptography.class);
    }
}