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
            MessageStructObjectFactory messageStructObjectFactory,
            ChatStructObjectFactory chatStructObjectFactory,
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        return ChatsModel.builder()
                .usersInfoSettings(usersInfoSettings)
                .usersModel(usersModel)
                .messageStructObjectFactory(messageStructObjectFactory)
                .chatStructObjectFactory(chatStructObjectFactory)
                .userStructObjectFactory(userStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
                .build();
    }

    @Bean
    @Lazy
    @Profile("servers")
    public CheckersOnlineModel beanCheckersOnlineModel(
            UsersModel usersModel,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            CheckerOnlineStructObjectFactory checkerOnlineStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        return CheckersOnlineModel.builder()
                .usersModel(usersModel)
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .checkerOnlineStructObjectFactory(checkerOnlineStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
                .build();
    }

    @Bean
    @Lazy
    public MessagesModel beanMessagesModel(
            MessageStructObjectFactory messageStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        return MessagesModel.builder()
                .messageStructObjectFactory(messageStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
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
            SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        return SocketRunnableCtrlModel.builder()
                .socketRunnableCtrlStructObjectFactory(socketRunnableCtrlStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
                .build();
    }

    @Bean
    @Lazy
    public UsersModel beanUsersModel(
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        return UsersModel.builder()
                .userStructObjectFactory(userStructObjectFactory)
                .rootStructObjectFactory(rootStructObjectFactory)
                .rootObjectsModel(rootObjectsModel)
                .build();
    }
}
