CREATE OR REPLACE FUNCTION chat_schema.users_save (
    f_user_id     bigint,
    f_name        character varying(32)
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.users WHERE user_id=f_user_id;
    IF found THEN
        UPDATE chat_schema.users SET user_id=f_user_id, name=f_name WHERE user_id=f_user_id;
        rv := 1;
    ELSE
        INSERT INTO chat_schema.users(user_id, name) VALUES (f_user_id, f_name);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.users_remove( f_id  integer )
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.users WHERE user_id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.users_get( f_id integer )
    RETURNS SETOF chat_schema.users AS
$BODY$
DECLARE
    rv chat_schema.users%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.users WHERE user_id=f_id;
    if found then
        RETURN NEXT rv;
    end if;
    RETURN;
END;
$BODY$ LANGUAGE plpgsql;
