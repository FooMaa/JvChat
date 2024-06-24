SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.chats_messages CASCADE;

CREATE TABLE chat_schema.chats_messages (
    index       serial,
    datetime    timestamp NOT NULL DEFAULT NOW(),
    sender      int NOT NULL,
    receiver    int NOT NULL,
    status      int NOT NULL,
    message     bytea NOT NULl,
    PRIMARY KEY (index),
    FOREIGN KEY (sender) REFERENCES chat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (receiver) REFERENCES chat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.chats_messages IS 'Таблица сообщений';
COMMENT ON COLUMN chat_schema.chats_messages.index IS 'Уникальный индекс сообщения';
COMMENT ON COLUMN chat_schema.chats_messages.datetime IS 'Штамп времени';
COMMENT ON COLUMN chat_schema.chats_messages.sender IS 'Отправитель';
COMMENT ON COLUMN chat_schema.chats_messages.receiver IS 'Получатель';
COMMENT ON COLUMN chat_schema.chats_messages.status IS 'Статус сообщения: отправлено, доставлено, прочитано';
COMMENT ON COLUMN chat_schema.chats_messages.message IS 'Сообщение в двоичном виде';
