package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.*;

import org.foomaa.jvchat.cryptography.HashCryptography;
import org.foomaa.jvchat.dbworker.DbRequests;
import org.foomaa.jvchat.dbworker.DbWorker;
import org.foomaa.jvchat.messages.DeserializatorDataMessages;
import org.foomaa.jvchat.messages.SerializatorDataMessages;
import org.foomaa.jvchat.models.*;
import org.foomaa.jvchat.network.EmailProcessor;
import org.foomaa.jvchat.network.ServersSocket;
import org.foomaa.jvchat.network.UsersSocket;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.MessageStructObjectFactory;
import org.foomaa.jvchat.tools.FormatTools;
import org.foomaa.jvchat.tools.StructTools;

@Configuration
public class ControlsConfig {
    @Bean
    @Profile("users")
    public ChatsCtrl beanChatsCtrl(ChatsModel chatsModel, FormatTools formatTools) {
        return ChatsCtrl.builder()
                .chatsModel(chatsModel)
                .formatTools(formatTools)
                .build();
    }

    @Bean
    @Profile("servers")
    public DbCtrl beanDbCtrl(DbRequests dbRequests, DbWorker dbWorker) {
        return DbCtrl.builder().dbRequests(dbRequests).dbWorker(dbWorker).build();
    }

    @Bean
    @Profile("servers")
    public EmailCtrl beanEmailCtrl(EmailProcessor emailProcessor, DbCtrl dbCtrl) {
        return EmailCtrl.builder().emailProcessor(emailProcessor).dbCtrl(dbCtrl).build();
    }

    @Bean
    public MessagesDefinesCtrl beanMessagesDefinesCtrl() {
        return MessagesDefinesCtrl.builder().build();
    }

    @Bean
    @Profile("users")
    public MessagesDialogCtrl beanUsersMessagesDialogCtrl(
            @Lazy MessagesModel messagesModel,
            @Lazy ChatsModel chatsModel,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            ChatsCtrl chatsCtrl,
            UsersInfoSettings usersInfoSettings,
            MessageStructObjectFactory messageStructObjectFactory) {
        return MessagesDialogCtrl.builder()
                .messagesModel(messagesModel)
                .chatsModel(chatsModel)
                .formatTools(formatTools)
                .sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .chatsCtrl(chatsCtrl)
                .usersInfoSettings(usersInfoSettings)
                .messageStructObjectFactory(messageStructObjectFactory)
                .build();
    }

    @Bean
    @Profile("servers")
    public MessagesDialogCtrl beanServersMessagesDialogCtrl(
            @Lazy MessagesModel messagesModel,
            @Lazy ChatsModel chatsModel,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            OnlineServersCtrl onlineServersCtrl,
            MessageStructObjectFactory messageStructObjectFactory) {
        return MessagesDialogCtrl.builder()
                .messagesModel(messagesModel)
                .chatsModel(chatsModel)
                .formatTools(formatTools)
                .sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .onlineServersCtrl(onlineServersCtrl)
                .messageStructObjectFactory(messageStructObjectFactory)
                .build();
    }

    @Bean
    @Lazy
    @Profile("users")
    public NetworkCtrl beanUsersNetworkCtrl(
            MainSettings mainSettings,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            TakeMessagesCtrl takeMessagesCtrl,
            UsersSocket usersSocket,
            SocketRunnableCtrlFactory socketRunnableCtrlFactory) {
        return NetworkCtrl.builder()
                .mainSettings(mainSettings)
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .takeMessagesCtrl(takeMessagesCtrl)
                .usersSocket(usersSocket)
                .socketRunnableCtrlFactory(socketRunnableCtrlFactory)
                .build();
    }

    @Bean
    @Lazy
    @Profile("servers")
    public NetworkCtrl beanServersNetworkCtrl(
            MainSettings mainSettings,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            TakeMessagesCtrl takeMessagesCtrl,
            ServersSocket serversSocket,
            OnlineServersCtrl onlineServersCtrl,
            SocketRunnableCtrlFactory socketRunnableCtrlFactory) {
        return NetworkCtrl.builder()
                .mainSettings(mainSettings)
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .takeMessagesCtrl(takeMessagesCtrl)
                .serversSocket(serversSocket)
                .onlineServersCtrl(onlineServersCtrl)
                .socketRunnableCtrlFactory(socketRunnableCtrlFactory)
                .build();
    }

    @Bean
    @Profile("servers")
    public OnlineServersCtrl beanOnlineServersCtrl(
            DbCtrl dbCtrl,
            ServersInfoSettings serversInfoSettings,
            CheckersOnlineModel checkersOnlineModel,
            UsersModel usersModel,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            SendMessagesCtrl sendMessagesCtrl) {
        return OnlineServersCtrl.builder()
                .dbCtrl(dbCtrl)
                .serversInfoSettings(serversInfoSettings)
                .checkersOnlineModel(checkersOnlineModel)
                .usersModel(usersModel)
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .sendMessagesCtrl(sendMessagesCtrl)
                .build();
    }

    @Bean
    @Profile("users")
    public SendMessagesCtrl beanUsersSendMessagesCtrl(
            SerializatorDataMessages serializatorDataMessages,
            MessagesDefinesCtrl messagesDefinesCtrl,
            StructTools structTools,
            @Lazy NetworkCtrl networkCtrl) {
        return SendMessagesCtrl.builder()
                .serializatorDataMessages(serializatorDataMessages)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .structTools(structTools)
                .networkCtrl(networkCtrl)
                .build();
    }

    @Bean
    @Profile("servers")
    public SendMessagesCtrl beanServersSendMessagesCtrl(
            SerializatorDataMessages serializatorDataMessages,
            MessagesDefinesCtrl messagesDefinesCtrl,
            StructTools structTools,
            @Lazy NetworkCtrl networkCtrl,
            ServersInfoSettings serversInfoSettings) {
        return SendMessagesCtrl.builder()
                .serializatorDataMessages(serializatorDataMessages)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .structTools(structTools)
                .networkCtrl(networkCtrl)
                .serversInfoSettings(serversInfoSettings)
                .build();
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public SocketRunnableCtrl beanSocketRunnableCtrl(
            SocketRunnableCtrlModel socketRunnableCtrlModel, @Lazy NetworkCtrl networkCtrl) {
        return SocketRunnableCtrl.builder()
                .socketRunnableCtrlModel(socketRunnableCtrlModel)
                .networkCtrl(networkCtrl)
                .build();
    }

    @Bean
    public SocketRunnableCtrlFactory beanSocketRunnableCtrlFactory(
            ObjectProvider<SocketRunnableCtrl> socketRunnableCtrlObjectProvider) {
        return SocketRunnableCtrlFactory.builder()
                .socketRunnableCtrlObjectProvider(socketRunnableCtrlObjectProvider)
                .build();
    }

    @Bean
    @Profile("users")
    public TakeMessagesCtrl beanUsersTakeMessagesCtrl(
            HashCryptography hashCryptography,
            DeserializatorDataMessages deserializatorDataMessages,
            StructTools structTools,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            MessagesDialogCtrl messagesDialogCtrl,
            UsersInfoSettings usersInfoSettings,
            ChatsCtrl chatsCtrl) {
        return TakeMessagesCtrl.builder()
                .hashCryptography(hashCryptography)
                .deserializatorDataMessages(deserializatorDataMessages)
                .structTools(structTools)
                .formatTools(formatTools)
                .sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .messagesDialogCtrl(messagesDialogCtrl)
                .usersInfoSettings(usersInfoSettings)
                .chatsCtrl(chatsCtrl)
                .build();
    }

    @Bean
    @Profile("servers")
    public TakeMessagesCtrl beanServersTakeMessagesCtrl(
            HashCryptography hashCryptography,
            DeserializatorDataMessages deserializatorDataMessages,
            StructTools structTools,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            MessagesDialogCtrl messagesDialogCtrl,
            OnlineServersCtrl onlineServersCtrl,
            EmailCtrl emailCtrl,
            DbCtrl dbCtrl) {
        return TakeMessagesCtrl.builder()
                .hashCryptography(hashCryptography)
                .deserializatorDataMessages(deserializatorDataMessages)
                .structTools(structTools)
                .formatTools(formatTools)
                .sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .messagesDialogCtrl(messagesDialogCtrl)
                .onlineServersCtrl(onlineServersCtrl)
                .emailCtrl(emailCtrl)
                .dbCtrl(dbCtrl)
                .build();
    }
}
