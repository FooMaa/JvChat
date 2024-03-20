SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.reset_password_data CASCADE;

CREATE TABLE chat_schema.reset_password_data (
    id          serial,
    id_user     serial,
    code        character varying,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES chat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.reset_password_data IS 'Временная таблица для сброса паролей';
COMMENT ON COLUMN chat_schema.reset_password_data.id IS 'ID записи';
COMMENT ON COLUMN chat_schema.reset_password_data.id_user IS 'ID пользователя';
COMMENT ON COLUMN chat_schema.reset_password_data.code IS 'Проверочный код пользователя';
