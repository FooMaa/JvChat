CREATE OR REPLACE FUNCTION chat_schema.logins_passwords_save (
    -- f_id     bigint,
    f_login       character varying,
    f_password    character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.logins_passwords WHERE login=f_login;
    IF found THEN
        UPDATE chat_schema.logins_passwords SET password=f_password WHERE login=f_login;
        rv := 1;
    ELSE
        INSERT INTO chat_schema.logins_passwords(login, password) VALUES (f_login, f_password);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.logins_passwords_remove( f_id  integer )
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.logins_passwords WHERE id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.logins_passwords_get( f_id integer )
    RETURNS SETOF chat_schema.logins_passwords AS
$BODY$
DECLARE
    rv chat_schema.logins_passwords%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.logins_passwords WHERE id=f_id;
    if found then
        RETURN NEXT rv;
    end if;
    RETURN;
END;
$BODY$ LANGUAGE plpgsql;
