SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id     bigint, --    parent_id   bigint,
    name        character varying(32), --    FOREIGN KEY (parent_id) REFERENCES parent (id) MATCH SIMPLE
    PRIMARY KEY ( user_id ) --        ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE users IS 'Логин и пароль пользователя';
COMMENT ON COLUMN users.user_id IS 'ID пользователя';

INSERT INTO users (user_id, name) VALUES (0, 'Anton');
