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

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_remove(f_login  integer)
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.auth_users_info WHERE login=f_login;
    RETURN true;
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

CREATE OR REPLACE FUNCTION chat_schema.auth_users_info_get_login_by_email(
    f_email character varying
)
    RETURNS character varying AS
$BODY$
DECLARE
    rv character varying;
BEGIN
    SELECT login INTO rv FROM chat_schema.auth_users_info WHERE email=f_email;
    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- chat_schema.verify_famous_email
-- ----------------------------------------------------------------------------------------------

CREATE FUNCTION chat_schema.verify_famous_email_delete_old_rows() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  DELETE FROM chat_schema.verify_famous_email WHERE datetime < NOW() - INTERVAL '1 minute';
  RETURN NEW;
END;
$$;

CREATE TRIGGER verify_famous_email_delete_old_rows_trigger_i
    AFTER INSERT ON chat_schema.verify_famous_email 
    EXECUTE PROCEDURE chat_schema.verify_famous_email_delete_old_rows();
CREATE TRIGGER verify_famous_email_delete_old_rows_trigger_u
    AFTER UPDATE ON chat_schema.verify_famous_email 
    EXECUTE PROCEDURE chat_schema.verify_famous_email_delete_old_rows();

CREATE OR REPLACE FUNCTION chat_schema.verify_famous_email_save (
    f_id_user       integer,
    f_code          character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
    rs bool;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.verify_famous_email WHERE id_user=f_id_user;
    IF found THEN
        --SELECT * INTO rs FROM chat_schema.verify_famous_email_remove();
        UPDATE chat_schema.verify_famous_email SET code=f_code, datetime=NOW() WHERE id_user=f_id_user;
        rv := 1;
    ELSE
        --SELECT * INTO rs FROM chat_schema.verify_famous_email_remove();
        INSERT INTO chat_schema.verify_famous_email(id_user, code, datetime) VALUES (f_id_user, f_code, NOW());
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_famous_email_remove()
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.verify_famous_email WHERE datetime < NOW() - INTERVAL '1 minute';
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_famous_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF chat_schema.verify_famous_email AS
$BODY$
DECLARE
    rv chat_schema.verify_famous_email%rowtype;
    rs bool;
BEGIN
    SELECT * INTO rs FROM chat_schema.verify_famous_email_remove();
    SELECT * INTO rv FROM chat_schema.verify_famous_email, chat_schema.auth_users_info 
    WHERE chat_schema.auth_users_info.id=chat_schema.verify_famous_email.id_user 
    AND f_email=chat_schema.auth_users_info.email 
    AND f_code=chat_schema.verify_famous_email.code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- chat_schema.verify_registration_email
-- ----------------------------------------------------------------------------------------------

CREATE FUNCTION chat_schema.verify_registration_email_delete_old_rows() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  DELETE FROM chat_schema.verify_registration_email WHERE datetime < NOW() - INTERVAL '1 minute';
  RETURN NEW;
END;
$$;

CREATE TRIGGER verify_registration_email_delete_old_rows_trigger_i
    AFTER INSERT ON chat_schema.verify_registration_email 
    EXECUTE PROCEDURE chat_schema.verify_registration_email_delete_old_rows();
CREATE TRIGGER verify_registration_email_delete_old_rows_trigger_u
    AFTER UPDATE ON chat_schema.verify_registration_email 
    EXECUTE PROCEDURE chat_schema.verify_registration_email_delete_old_rows();

CREATE OR REPLACE FUNCTION chat_schema.verify_registration_email_save (
    f_email         character varying,
    f_code          character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
    rs bool;
BEGIN
    rv := -1;
    PERFORM * FROM chat_schema.verify_registration_email WHERE email=f_email;
    IF found THEN
        --SELECT * INTO rs FROM chat_schema.verify_registration_email_remove();
        UPDATE chat_schema.verify_registration_email SET code=f_code, datetime=NOW() WHERE email=f_email;
        rv := 1;
    ELSE
        --SELECT * INTO rs FROM chat_schema.verify_registration_email_remove();
        INSERT INTO chat_schema.verify_registration_email(email, code, datetime) VALUES (f_email, f_code, NOW());
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_registration_email_remove()
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM chat_schema.verify_registration_email WHERE datetime < NOW() - INTERVAL '1 minute';
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION chat_schema.verify_registration_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF chat_schema.verify_registration_email AS
$BODY$
DECLARE
    rv chat_schema.verify_registration_email%rowtype;
    rs bool;
BEGIN
    SELECT * INTO rs FROM chat_schema.verify_registration_email_remove();
    SELECT * INTO rv FROM chat_schema.verify_registration_email  WHERE email = f_email AND code = f_code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- chat_schema.chats_messages
-- ----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION chat_schema.chats_messages_get_chats_by_login(
    f_login character varying
)
    RETURNS TABLE (Sender character varying, Receiver character varying, Message bytea, Datetime timestamp) AS
$BODY$
DECLARE
    rv chat_schema.chats_messages%rowtype;
    rLogin_ID int;
BEGIN
    RETURN QUERY 
    SELECT DISTINCT ON (LEAST(chats.senderID, chats.receiverID), GREATEST(chats.senderID, chats.receiverID)) 
    auth1.login AS login_sender, auth2.login AS login_receiver, chats.message, chats.datetime 
    FROM chat_schema.chats_messages AS chats
    LEFT JOIN chat_schema.auth_users_info AS auth1 ON chats.senderID = auth1.id 
    LEFT JOIN chat_schema.auth_users_info AS auth2 ON chats.receiverID = auth2.id 
    WHERE auth1.login = f_login OR auth2.login = f_login 
    ORDER BY LEAST(chats.senderID, chats.receiverID), GREATEST(chats.senderID, chats.receiverID), datetime DESC;
END;
$BODY$ LANGUAGE plpgsql;
