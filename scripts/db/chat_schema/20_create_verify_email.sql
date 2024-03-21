SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.verify_email CASCADE;

CREATE TABLE chat_schema.verify_email (
    id          serial,
    id_user     serial,
    code        character varying,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES chat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.verify_email IS 'Временная таблица для сброса паролей';
COMMENT ON COLUMN chat_schema.verify_email.id IS 'ID записи';
COMMENT ON COLUMN chat_schema.verify_email.id_user IS 'ID пользователя';
COMMENT ON COLUMN chat_schema.verify_email.code IS 'Проверочный код пользователя';
