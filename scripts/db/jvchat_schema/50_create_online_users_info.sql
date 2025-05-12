SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS jvchat_schema.online_users_info CASCADE;

CREATE TABLE jvchat_schema.online_users_info (
    id_user             serial,
    status              int,
    last_online_time    timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_user),
    FOREIGN KEY (id_user) REFERENCES jvchat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE jvchat_schema.online_users_info IS 'Таблица для отслеживания онлайна пользователей';
COMMENT ON COLUMN jvchat_schema.online_users_info.id_user IS 'ID пользователя';
COMMENT ON COLUMN jvchat_schema.online_users_info.status IS 'Состояние пользователя 0 - ошибка, 1 - не в сети, 2 - в сети';
COMMENT ON COLUMN jvchat_schema.online_users_info.last_online_time IS 'Штамп времени последнего появления в сети';
