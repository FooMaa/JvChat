-- ----------------------------------------------------------------------------------------------
-- chat_schema.auth_users_info_save
-- ----------------------------------------------------------------------------------------------
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

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_change_password (
    f_email       character varying,
    f_password    character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.auth_users_info WHERE email=f_email;
    IF found THEN
        UPDATE chat_schema.auth_users_info SET password=f_password WHERE email=f_email;
        rv := 1;
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

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_get_id_by_email(
    f_email character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    SELECT id INTO rv FROM chat_schema.auth_users_info WHERE email=f_email;
    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- chat_schema.verify_email
-- ----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION chat_schema.verify_email_save (
    f_id_user       integer,
    f_code          character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.verify_email WHERE id_user=f_id_user;
    IF found THEN
        UPDATE chat_schema.verify_email SET code=f_code WHERE id_user=f_id_user;
        rv := 1;
    ELSE
        INSERT INTO chat_schema.verify_email(id_user, code) VALUES (f_id_user, f_code);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_email_remove(f_id  integer)
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.verify_email WHERE id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF chat_schema.verify_email AS
$BODY$
DECLARE
    rv chat_schema.verify_email%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.verify_email, chat_schema.auth_users_info WHERE chat_schema.auth_users_info.id = chat_schema.verify_email.id_user AND f_email = chat_schema.auth_users_info.email AND f_code = chat_schema.verify_email.code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- chat_schema.check_registration_email
-- ----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION chat_schema.check_registration_email_save (
    f_email         character varying,
    f_code          character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.check_registration_email WHERE email=f_email;
    IF found THEN
        UPDATE chat_schema.check_registration_email SET code=f_code WHERE email=f_email;
        rv := 1;
    ELSE
        INSERT INTO chat_schema.check_registration_email(email, code) VALUES (f_email, f_code);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.check_registration_email_remove(f_id  integer)
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.check_registration_email WHERE id=f_id;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.check_registration_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF chat_schema.check_registration_email AS
$BODY$
DECLARE
    rv chat_schema.check_registration_email%rowtype;
BEGIN
    SELECT * INTO rv FROM chat_schema.check_registration_email  WHERE email = f_email AND code = f_code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
