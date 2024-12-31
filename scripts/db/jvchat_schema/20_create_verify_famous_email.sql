SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS jvchat_schema.verify_famous_email CASCADE;

CREATE TABLE jvchat_schema.verify_famous_email (
    id_user     serial,
    code        character varying NOT NULL,
    datetime    timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_user),
    FOREIGN KEY (id_user) REFERENCES jvchat_schema.auth_users_info(id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE jvchat_schema.verify_famous_email IS 'Временная таблица для сброса паролей';
COMMENT ON COLUMN jvchat_schema.verify_famous_email.id_user IS 'ID пользователя';
COMMENT ON COLUMN jvchat_schema.verify_famous_email.code IS 'Проверочный код пользователя';
COMMENT ON COLUMN jvchat_schema.verify_famous_email.datetime IS 'Штамп времени';
