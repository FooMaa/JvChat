SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.logins_passwords CASCADE;

CREATE TABLE chat_schema.logins_passwords (
    id          serial PRIMARY KEY,            --    parent_id   bigint,
    login       character varying, --    FOREIGN KEY (parent_id) REFERENCES parent (id) MATCH SIMPLE
    password    character varying --    FOREIGN KEY (parent_id) REFERENCES parent (id) MATCH SIMPLE
    -- PRIMARY KEY ( id )        --        ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.logins_passwords IS 'Логин и пароль пользователя';
COMMENT ON COLUMN chat_schema.logins_passwords.id IS 'ID пользователя';
COMMENT ON COLUMN chat_schema.logins_passwords.login IS 'Логин пользователя';
COMMENT ON COLUMN chat_schema.logins_passwords.password IS 'Пароль пользователя';
