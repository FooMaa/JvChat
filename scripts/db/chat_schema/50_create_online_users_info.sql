SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.online_users_info CASCADE;

CREATE TABLE chat_schema.online_users_info (
    id_user             serial,
    status              int,
    last_online_time    timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_user),
    FOREIGN KEY (id_user) REFERENCES chat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.online_users_info IS 'Таблица для отслеживания онлайна пользователей';
COMMENT ON COLUMN chat_schema.online_users_info.id_user IS 'ID пользователя';
COMMENT ON COLUMN chat_schema.online_users_info.status IS 'Состояние пользователя -1 - ошибка, 0 - не в сети, 1 - в сети';
COMMENT ON COLUMN chat_schema.online_users_info.last_online_time IS 'Штамп времени последнего появления в сети';
