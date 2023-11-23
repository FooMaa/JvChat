SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.users CASCADE;

CREATE TABLE chat_schema.users (
    user_id     bigint, --    parent_id   bigint,
    name        character varying(32), --    FOREIGN KEY (parent_id) REFERENCES parent (id) MATCH SIMPLE
    PRIMARY KEY ( user_id ) --        ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE chat_schema.users IS 'Логин и пароль пользователя';
COMMENT ON COLUMN chat_schema.users.user_id IS 'ID пользователя';
