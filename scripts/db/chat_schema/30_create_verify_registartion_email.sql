SET CLIENT_ENCODING TO 'UTF-8';

DROP TABLE IF EXISTS chat_schema.verify_registration_email CASCADE;

CREATE TABLE chat_schema.verify_registration_email (
    --id          serial, -- убрать если выяснится, что его мало
    email       character varying,
    code        character varying,
    datetime        timestamp NOT NULL DEFAULT NOW()--,
    --PRIMARY KEY (id)
);

COMMENT ON TABLE chat_schema.verify_registration_email IS 'Временная таблица для подтверждения почты при регистрации';
--COMMENT ON COLUMN chat_schema.verify_registration_email.id IS 'ID записи';
COMMENT ON COLUMN chat_schema.verify_registration_email.email IS 'Почта пользователя';
COMMENT ON COLUMN chat_schema.verify_registration_email.code IS 'Проверочный код пользователя';
COMMENT ON COLUMN chat_schema.verify_registration_email.datetime IS 'Штамп времени';
