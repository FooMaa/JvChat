package org.foomaa.jvchat.models;

import org.springframework.context.annotation.*;

import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.*;

@Configuration
public class ModelsConfig {
    @Bean
    @Lazy
    public ChatsModel beanChatsModel(
            UsersInfoSettings usersInfoSettings,
            UsersModel usersModel,
            RootObjectsModel rootObjectsModel,
            MessageStructObjectFactory messageStructObjectFactory,
            ChatStructObjectFactory chatStructObjectFactory,
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        return ChatsModel.builder()
                .usersInfoSettings(usersInfoSettings)
                .usersModel(usersModel)
                .rootObjectsModel(rootObjectsModel)
                .messageStructObjectFactory(messageStructObjectFactory)
                .chatStructObjectFactory(chatStructObjectFactory)
                .userStructObjectFactory(userStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .build();
    }

    @Bean
    @Lazy
    @Profile("servers")
    public CheckersOnlineModel beanCheckersOnlineModel(
            UsersModel usersModel,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            RootObjectsModel rootObjectsModel,
            CheckerOnlineStructObjectFactory checkerOnlineStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        return CheckersOnlineModel.builder()
                .usersModel(usersModel)
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .rootObjectsModel(rootObjectsModel)
                .checkerOnlineStructObjectFactory(checkerOnlineStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .build();
    }

    @Bean
    @Lazy
    public MessagesModel beanMessagesModel(
            RootObjectsModel rootObjectsModel,
            MessageStructObjectFactory messageStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        return MessagesModel.builder()
                .rootObjectsModel(rootObjectsModel)
                .messageStructObjectFactory(messageStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .build();
    }

    @Bean
    public RootObjectsModel beanRootObjectsModel(RootStructObjectFactory rootStructObjectFactory) {
        return RootObjectsModel.builder()
                .rootStructObjectFactory(rootStructObjectFactory)
                .build();
    }

    @Bean
    @Lazy
    public SocketRunnableCtrlModel beanSocketRunnableCtrlModel(
            RootObjectsModel rootObjectsModel,
            SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        return SocketRunnableCtrlModel.builder()
                .rootObjectsModel(rootObjectsModel)
                .socketRunnableCtrlStructObjectFactory(socketRunnableCtrlStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .build();
    }

    @Bean
    @Lazy
    public UsersModel beanUsersModel(
            RootObjectsModel rootObjectsModel,
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        return UsersModel.builder()
                .userStructObjectFactory(userStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
                .build();
    }
}
