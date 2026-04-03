package org.foomaa.jvchat.structobjects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class StructObjectsConfig {
    @Bean
    @Scope("prototype")
    public ChatStructObject beanChatStructObject() {
        return ChatStructObject.builder().build();
    }

    @Bean
    public ChatStructObjectFactory beanChatStructObjectFactory(
            ObjectProvider<ChatStructObject> chatStructObjectObjectProvider) {
        return ChatStructObjectFactory.builder()
                .chatStructObjectObjectProvider(chatStructObjectObjectProvider)
                .build();
    }

    @Bean
    @Scope("prototype")
    @Profile("servers")
    public CheckerOnlineStructObject beanCheckerOnlineStructObject() {
        return CheckerOnlineStructObject.builder().build();
    }

    @Bean
    @Profile("servers")
    public CheckerOnlineStructObjectFactory beanCheckerOnlineStructObjectFactory(
            ObjectProvider<CheckerOnlineStructObject> checkerOnlineObjectProvider) {
        return CheckerOnlineStructObjectFactory.builder()
                .checkerOnlineObjectProvider(checkerOnlineObjectProvider)
                .build();
    }

    @Bean
    @Scope("prototype")
    public MessageStructObject beanBaseStructObject() {
        return MessageStructObject.builder().build();
    }

    @Bean
    public MessageStructObjectFactory beanBaseStructObjectFactory(
            ObjectProvider<MessageStructObject> messageStructObjectObjectProvider) {
        return MessageStructObjectFactory.builder()
                .messageStructObjectObjectProvider(messageStructObjectObjectProvider)
                .build();
    }

    @Bean
    @Scope("prototype")
    public RootStructObject beanRootStructObject() {
        return RootStructObject.builder().build();
    }

    @Bean
    public RootStructObjectFactory beanRootStructObjectFactory(
            ObjectProvider<RootStructObject> rootStructObjectObjectProvider) {
        return RootStructObjectFactory.builder()
                .rootStructObjectObjectProvider(rootStructObjectObjectProvider)
                .build();
    }

    @Bean
    @Scope("prototype")
    public SocketRunnableCtrlStructObject beanSocketRunnableCtrlStructObject() {
        return SocketRunnableCtrlStructObject.builder().build();
    }

    @Bean
    public SocketRunnableCtrlStructObjectFactory beanSocketRunnableCtrlStructObjectFactory(
            ObjectProvider<SocketRunnableCtrlStructObject> ctrlStructObjectObjectProvider) {
        return SocketRunnableCtrlStructObjectFactory.builder()
                .ctrlStructObjectObjectProvider(ctrlStructObjectObjectProvider)
                .build();
    }

    @Bean
    @Scope("prototype")
    public UserStructObject beanUserStructObject() {
        return UserStructObject.builder().build();
    }

    @Bean
    public UserStructObjectFactory beanUserStructObjectFactory(
            ObjectProvider<UserStructObject> userStructObjectObjectProvider) {
        return UserStructObjectFactory.builder()
                .userStructObjectObjectProvider(userStructObjectObjectProvider)
                .build();
    }
}
