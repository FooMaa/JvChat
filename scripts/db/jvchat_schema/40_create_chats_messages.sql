SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS jvchat_schema.chats_messages CASCADE;

CREATE TABLE jvchat_schema.chats_messages (
    index       	serial,
    senderID    	int NOT NULL,
    receiverID  	int NOT NULL,
    status      	int NOT NULL,
    message     	character varying NOT NULL,
    uuid_chat       character varying NOT NULL,
    uuid_message	character varying NOT NULL,
    datetime    	timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (index),
    FOREIGN KEY (senderID) REFERENCES jvchat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (receiverID) REFERENCES jvchat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE jvchat_schema.chats_messages IS 'Таблица сообщений';
COMMENT ON COLUMN jvchat_schema.chats_messages.index IS 'Уникальный индекс сообщения';
COMMENT ON COLUMN jvchat_schema.chats_messages.senderID IS 'Отправитель';
COMMENT ON COLUMN jvchat_schema.chats_messages.receiverID IS 'Получатель';
COMMENT ON COLUMN jvchat_schema.chats_messages.status IS 'Статус сообщения: отправлено, доставлено, прочитано';
COMMENT ON COLUMN jvchat_schema.chats_messages.message IS 'Сообщение в двоичном виде';
COMMENT ON COLUMN jvchat_schema.chats_messages.uuid_message IS 'Идентификационный код чата';
COMMENT ON COLUMN jvchat_schema.chats_messages.uuid_message IS 'Идентификационный код сообщения';
COMMENT ON COLUMN jvchat_schema.chats_messages.datetime IS 'Штамп времени';
