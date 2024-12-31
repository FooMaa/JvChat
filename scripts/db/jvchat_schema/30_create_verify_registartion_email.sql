SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS jvchat_schema.verify_registration_email CASCADE;

CREATE TABLE jvchat_schema.verify_registration_email (
    email       character varying,
    code        character varying,
    datetime    timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (email)
);

COMMENT ON TABLE jvchat_schema.verify_registration_email IS 'Временная таблица для подтверждения почты при регистрации';
COMMENT ON COLUMN jvchat_schema.verify_registration_email.email IS 'Почта пользователя';
COMMENT ON COLUMN jvchat_schema.verify_registration_email.code IS 'Проверочный код пользователя';
COMMENT ON COLUMN jvchat_schema.verify_registration_email.datetime IS 'Штамп времени';
