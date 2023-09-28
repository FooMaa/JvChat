SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id     bigint,
--    parent_id   bigint,
    name        character varying(32),
    PRIMARY KEY ( id )
--    FOREIGN KEY (parent_id) REFERENCES parent (id) MATCH SIMPLE
--        ON UPDATE CASCADE ON DELETE CASCADE
)

COMMENT ON TABLE users.user_id IS "INFO";

INSERT INTO users (user_id, name) VALUES (0, 'Anton');