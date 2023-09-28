CREATE OR REPLACE FUNCTION users_save(
    f_user_id     bigint,
    f_name        character varying(32),
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM users WHERE user_id=f_user_id;
    IF found THEN
        UPDATE users SET user_id=f_user_id, name=f_name WHERE user_id=f_user_id;
        rv := 1;
    ELSE
        INSERT INTO users(user_id, name) VALUES (f_user_id, f_name);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION users_remove( f_id  integer )
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    delete from users where user_id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION users_get( f_id integer )
    RETURN SETOF users AS
$BODY$
DECLARE
    rv users%rowtype;
BEGIN
    SELECT * INTO rv FROM users WHERE user_id=f_id;
    if found then
        RETURN NEXT rv;
    end if;
    RETURN;
END;
$BODY$ LANGUAGE plpgsql;