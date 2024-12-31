SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS jvchat_schema.auth_users_info CASCADE;

CREATE TABLE jvchat_schema.auth_users_info (
    id          serial,
    login       character varying,
    email       character varying,
    uuid_user   character varying,
    password    character varying,
    PRIMARY KEY (id)
);

COMMENT ON TABLE jvchat_schema.auth_users_info IS 'Логин и пароль пользователя';
COMMENT ON COLUMN jvchat_schema.auth_users_info.id IS 'ID пользователя';
COMMENT ON COLUMN jvchat_schema.auth_users_info.login IS 'Логин пользователя';
COMMENT ON COLUMN jvchat_schema.auth_users_info.email IS 'Почта пользователя';
COMMENT ON COLUMN jvchat_schema.auth_users_info.uuid_user IS 'Uuid пользователя';
COMMENT ON COLUMN jvchat_schema.auth_users_info.password IS 'Пароль пользователя';
