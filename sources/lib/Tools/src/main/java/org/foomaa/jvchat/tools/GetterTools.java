package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterTools {
    private static GetterTools instance;
    private final AnnotationConfigApplicationContext context;

    private GetterTools() {
        context = new AnnotationConfigApplicationContext(
                ToolsSpringConfig.class);
    }

    public static GetterTools getInstance() {
        if (instance == null) {
            instance = new GetterTools();
        }
        return instance;
    }

    public MainTools getBeanMainTools() {
        return context.getBean(ToolsSpringConfig.NameBeans.BeanMainTools.getValue(),
                MainTools.class);
    }

    public StructTools getBeanStructTools() {
        return context.getBean(ToolsSpringConfig.NameBeans.BeanStructTools.getValue(),
                StructTools.class);
    }

    public ServersTools getBeanServersTools() {
        return context.getBean(ToolsSpringConfig.NameBeans.BeanServersTools.getValue(),
                ServersTools.class);
    }

    public UsersTools getBeanUsersTools() {
        return context.getBean(ToolsSpringConfig.NameBeans.BeanUsersTools.getValue(),
                UsersTools.class);
    }

    public FormatTools getBeanFormatTools() {
        return context.getBean(ToolsSpringConfig.NameBeans.BeanFormatTools.getValue(),
                FormatTools.class);
    }
}