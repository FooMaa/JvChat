CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_save (
    f_login       character varying,
    f_email       character varying,
    f_password    character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.auth_users_info WHERE login=f_login;
    IF found THEN
        UPDATE chat_schema.auth_users_info SET password=f_password, email=f_email WHERE login=f_login;
        rv := 1;
    ELSE
        INSERT INTO chat_schema.auth_users_info(login, email, password) VALUES (f_login, f_email, f_password);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_remove(f_id  integer)
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.auth_users_info WHERE id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_get(f_id integer)
    RETURNS SETOF chat_schema.auth_users_info AS
$BODY$
DECLARE
    rv chat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.auth_users_info WHERE id=f_id;
    IF found THEN
        RETURN NEXT rv;
    END IF;
    RETURN;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_check_login_password(
    f_login character varying,
    f_password character varying
)
    RETURNS SETOF chat_schema.auth_users_info AS
$BODY$
DECLARE
    rv chat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.auth_users_info WHERE login=f_login AND password=f_password;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_check_login(
    f_login character varying
)
    RETURNS SETOF chat_schema.auth_users_info AS
$BODY$
DECLARE
    rv chat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.auth_users_info WHERE login=f_login;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_check_email(
    f_email character varying
)
    RETURNS SETOF chat_schema.auth_users_info AS
$BODY$
DECLARE
    rv chat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.auth_users_info WHERE email=f_email;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
