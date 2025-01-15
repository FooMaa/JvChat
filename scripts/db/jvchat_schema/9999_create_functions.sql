-- ----------------------------------------------------------------------------------------------
-- jvchat_schema.auth_users_info_save
-- ----------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_save (
    f_login       character varying,
    f_email       character varying,
    f_password    character varying,
    f_uuid_user   character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM jvchat_schema.auth_users_info WHERE login = f_login;
    IF found THEN
        UPDATE jvchat_schema.auth_users_info SET password = f_password, email = f_email WHERE login = f_login;
        rv := 1;
    ELSE
        INSERT INTO jvchat_schema.auth_users_info(login, email, uuid_user, password) VALUES (f_login, f_email, f_uuid_user, f_password);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_change_password (
    f_email       character varying,
    f_password    character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM jvchat_schema.auth_users_info WHERE email = f_email;
    IF found THEN
        UPDATE jvchat_schema.auth_users_info SET password = f_password WHERE email = f_email;
        rv := 1;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_remove(f_login  integer)
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM jvchat_schema.auth_users_info WHERE login = f_login;
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_check_login_password(
    f_login character varying,
    f_password character varying
)
    RETURNS SETOF jvchat_schema.auth_users_info AS
$BODY$
DECLARE
    rv jvchat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM jvchat_schema.auth_users_info WHERE login = f_login AND password = f_password;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_check_login(
    f_login character varying
)
    RETURNS SETOF jvchat_schema.auth_users_info AS
$BODY$
DECLARE
    rv jvchat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM jvchat_schema.auth_users_info WHERE login = f_login;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_check_email(
    f_email character varying
)
    RETURNS SETOF jvchat_schema.auth_users_info AS
$BODY$
DECLARE
    rv jvchat_schema.auth_users_info%rowtype;
BEGIN
    SELECT * INTO rv FROM jvchat_schema.auth_users_info WHERE email = f_email;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_get_uuid_by_email(
    f_email character varying
)
    RETURNS character varying AS
$BODY$
DECLARE
    rv character varying;
BEGIN
    SELECT uuid_user INTO rv FROM jvchat_schema.auth_users_info WHERE email = f_email;
    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_get_login_by_email(
    f_email character varying
)
    RETURNS character varying AS
$BODY$
DECLARE
    rv character varying;
BEGIN
    SELECT login INTO rv FROM jvchat_schema.auth_users_info WHERE email = f_email;
    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.auth_users_info_get_uuid_by_login(
    f_login character varying
)
    RETURNS character varying AS
$BODY$
DECLARE
    rv character varying;
BEGIN
    SELECT uuid_user INTO rv FROM jvchat_schema.auth_users_info WHERE login = f_login;
    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- jvchat_schema.verify_famous_email
-- ----------------------------------------------------------------------------------------------

CREATE FUNCTION jvchat_schema.verify_famous_email_delete_old_rows() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  DELETE FROM jvchat_schema.verify_famous_email WHERE datetime < NOW() - INTERVAL '1 minute';
  RETURN NEW;
END;
$$;

CREATE TRIGGER verify_famous_email_delete_old_rows_trigger_i
    AFTER INSERT ON jvchat_schema.verify_famous_email 
    EXECUTE PROCEDURE jvchat_schema.verify_famous_email_delete_old_rows();
CREATE TRIGGER verify_famous_email_delete_old_rows_trigger_u
    AFTER UPDATE ON jvchat_schema.verify_famous_email 
    EXECUTE PROCEDURE jvchat_schema.verify_famous_email_delete_old_rows();

CREATE OR REPLACE FUNCTION jvchat_schema.verify_famous_email_save (
    f_uuid_user       character varying,
    f_code          character varying
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
    f_id_user integer;
    rs bool;
BEGIN
    rv := -1;
    SELECT id INTO f_id_user FROM jvchat_schema.auth_users_info WHERE uuid_user = f_uuid;

    PERFORM * FROM jvchat_schema.verify_famous_email WHERE id_user = f_id_user;
    IF found THEN
        --SELECT * INTO rs FROM jvchat_schema.verify_famous_email_remove();
        UPDATE jvchat_schema.verify_famous_email SET code = f_code, datetime = NOW() WHERE id_user = f_id_user;
        rv := 1;
    ELSE
        --SELECT * INTO rs FROM jvchat_schema.verify_famous_email_remove();
        INSERT INTO jvchat_schema.verify_famous_email(id_user, code, datetime) VALUES (f_id_user, f_code, NOW());
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.verify_famous_email_remove()
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM jvchat_schema.verify_famous_email WHERE datetime < NOW() - INTERVAL '1 minute';
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.verify_famous_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF jvchat_schema.verify_famous_email AS
$BODY$
DECLARE
    rv jvchat_schema.verify_famous_email%rowtype;
    rs bool;
BEGIN
    SELECT * INTO rs FROM jvchat_schema.verify_famous_email_remove();
    SELECT * INTO rv FROM jvchat_schema.verify_famous_email, jvchat_schema.auth_users_info 
    WHERE jvchat_schema.auth_users_info.id = jvchat_schema.verify_famous_email.id_user 
    AND f_email = jvchat_schema.auth_users_info.email 
    AND f_code = jvchat_schema.verify_famous_email.code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- jvchat_schema.verify_registration_email
-- ----------------------------------------------------------------------------------------------

CREATE FUNCTION jvchat_schema.verify_registration_email_delete_old_rows() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  DELETE FROM jvchat_schema.verify_registration_email WHERE datetime < NOW() - INTERVAL '1 minute';
  RETURN NEW;
END;
$$;

CREATE TRIGGER verify_registration_email_delete_old_rows_trigger_i
    AFTER INSERT ON jvchat_schema.verify_registration_email 
    EXECUTE PROCEDURE jvchat_schema.verify_registration_email_delete_old_rows();
CREATE TRIGGER verify_registration_email_delete_old_rows_trigger_u
    AFTER UPDATE ON jvchat_schema.verify_registration_email 
    EXECUTE PROCEDURE jvchat_schema.verify_registration_email_delete_old_rows();

CREATE OR REPLACE FUNCTION jvchat_schema.verify_registration_email_save (
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
    PERFORM * FROM jvchat_schema.verify_registration_email WHERE email = f_email;
    IF found THEN
        --SELECT * INTO rs FROM jvchat_schema.verify_registration_email_remove();
        UPDATE jvchat_schema.verify_registration_email SET code = f_code, datetime = NOW() WHERE email = f_email;
        rv := 1;
    ELSE
        --SELECT * INTO rs FROM jvchat_schema.verify_registration_email_remove();
        INSERT INTO jvchat_schema.verify_registration_email(email, code, datetime) VALUES (f_email, f_code, NOW());
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.verify_registration_email_remove()
    RETURNS boolean AS
$BODY$
DECLARE
BEGIN
    DELETE FROM jvchat_schema.verify_registration_email WHERE datetime < NOW() - INTERVAL '1 minute';
    RETURN true;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.verify_registration_email_check_email_code(
    f_email character varying,
    f_code character varying
)
    RETURNS SETOF jvchat_schema.verify_registration_email AS
$BODY$
DECLARE
    rv jvchat_schema.verify_registration_email%rowtype;
    rs bool;
BEGIN
    SELECT * INTO rs FROM jvchat_schema.verify_registration_email_remove();
    SELECT * INTO rv FROM jvchat_schema.verify_registration_email  WHERE email = f_email AND code = f_code;
    IF found THEN
        RETURN NEXT rv;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- jvchat_schema.chats_messages
-- ----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION jvchat_schema.chats_messages_get_chats_by_login(
    f_uuid character varying
)
    RETURNS TABLE (
        login character varying,
        uuid_user character varying,
        last_message_text character varying,
        uuid_message character varying,
        is_login_sent_last_message bool,
        uuid_chat character varying,
        datetime_last_message timestamp,
        status_message int) AS
$BODY$
DECLARE
    rv jvchat_schema.chats_messages%rowtype;
BEGIN
    RETURN QUERY 
    SELECT DISTINCT ON (LEAST(chats.senderID, chats.receiverID), GREATEST(chats.senderID, chats.receiverID)) 
    CASE
        WHEN auth1.uuid_user = f_uuid AND auth2.uuid_user = f_uuid THEN auth1.login 
        WHEN auth1.uuid_user = f_uuid THEN auth2.login
        WHEN auth2.uuid_user = f_uuid THEN auth1.login        
    END AS login,
    CASE
        WHEN auth1.uuid_user = f_uuid AND auth2.uuid_user = f_uuid THEN auth1.uuid_user 
        WHEN auth1.uuid_user = f_uuid THEN auth2.uuid_user
        WHEN auth2.uuid_user = f_uuid THEN auth1.uuid_user    
    END AS uuid_user,
    chats.message AS last_message_text,
    chats.uuid_message AS uuid_message, 
    CASE
        WHEN auth1.uuid_user = f_uuid AND auth2.uuid_user = f_uuid THEN true
        WHEN auth1.uuid_user = f_uuid THEN false
        WHEN auth2.uuid_user = f_uuid THEN true
    END AS is_login_sent_last_message,
    chats.uuid_chat AS uuid_chat,
    chats.datetime AS datetime_last_message,
    chats.status AS status_message
    FROM jvchat_schema.chats_messages AS chats
    LEFT JOIN jvchat_schema.auth_users_info AS auth1 ON chats.senderID = auth1.id 
    LEFT JOIN jvchat_schema.auth_users_info AS auth2 ON chats.receiverID = auth2.id  
    WHERE auth1.uuid_user = f_uuid OR auth2.uuid_user = f_uuid
    ORDER BY LEAST(chats.senderID, chats.receiverID), GREATEST(chats.senderID, chats.receiverID), datetime DESC;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.chats_messages_get_quantity_messages_by_logins(
    f_login_one character varying,
    f_login_two character varying,
    f_quantity int
)
    RETURNS TABLE (sender character varying, receiver character varying, text_message character varying, uuid_message character varying, datetime_message timestamp, status_message int) AS
$BODY$
DECLARE
    rv jvchat_schema.chats_messages%rowtype;
BEGIN
    RETURN QUERY SELECT
    auth1.login AS sender,
    auth2.login AS receiver,
    chats.message AS text_message,
    chats.uuid_message AS uuid_message, 
    chats.datetime AS datetime_message,
    chats.status AS status_message
    FROM jvchat_schema.chats_messages AS chats
    LEFT JOIN jvchat_schema.auth_users_info AS auth1 ON chats.senderID = auth1.id 
    LEFT JOIN jvchat_schema.auth_users_info AS auth2 ON chats.receiverID = auth2.id  
    WHERE (auth1.login = f_login_one AND auth2.login = f_login_two) 
    OR (auth1.login = f_login_two AND auth2.login = f_login_one)
    ORDER BY datetime_message DESC
    LIMIT f_quantity;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.chats_messages_save_message (
    f_loginSender       character varying,
    f_loginReceiver     character varying,
    f_status            int,
    f_message           character varying,
    f_uuid_message      character varying,
    f_datetime          timestamp
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
    f_id_user_sender integer;
    f_id_user_receiver integer;
    f_cast_timestamp timestamp;
BEGIN
    SELECT id INTO f_id_user_sender FROM jvchat_schema.auth_users_info WHERE login = f_loginSender;
    SELECT id INTO f_id_user_receiver FROM jvchat_schema.auth_users_info WHERE login = f_loginReceiver;
    SELECT cast(f_datetime as timestamp) INTO f_cast_timestamp;
    
    rv := -1;

    PERFORM * FROM jvchat_schema.chats_messages 
    WHERE senderID = f_id_user_sender 
    AND receiverID = f_id_user_receiver
    AND uuid_message = f_uuid_message;

    IF found THEN
        UPDATE jvchat_schema.chats_messages SET status = f_status, message = f_message, datetime = f_cast_timestamp 
        WHERE senderID = f_id_user_sender 
        AND receiverID = f_id_user_receiver
        AND uuid_message = f_uuid_message;
        rv := 1;
    ELSE
        INSERT INTO jvchat_schema.chats_messages (senderID, receiverID, status, message, uuid_message, datetime) VALUES (f_id_user_sender, f_id_user_receiver, f_status, f_message, f_uuid_message, f_cast_timestamp);
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.chats_messages_change_status (
    f_uuid_message  character varying,        
    f_status        int
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM jvchat_schema.chats_messages WHERE uuid_message = f_uuid_message;
    IF found THEN
        UPDATE jvchat_schema.chats_messages SET status = f_status WHERE uuid_message = f_uuid_message;
        rv := 1;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------------------------
-- jvchat_schema.online_users_info
-- ----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION jvchat_schema.online_users_info_get_status_time_by_user_login(
    f_login character varying
)
    RETURNS TABLE (status_online int, last_online_time timestamp) AS
$BODY$
DECLARE
    rv jvchat_schema.online_users_info%rowtype;
BEGIN
    RETURN QUERY
    SELECT jvchat_schema.online_users_info.status, jvchat_schema.online_users_info.last_online_time
    FROM jvchat_schema.online_users_info 
    LEFT JOIN jvchat_schema.auth_users_info ON jvchat_schema.online_users_info.id_user = jvchat_schema.auth_users_info.id
    WHERE login = f_login;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.online_users_info_get_online_users()
    RETURNS TABLE (uuid_user character varying) AS
$BODY$
DECLARE
    rv jvchat_schema.online_users_info%rowtype;
BEGIN
    RETURN QUERY
    SELECT jvchat_schema.auth_users_info.uuid_user
    FROM jvchat_schema.online_users_info 
    LEFT JOIN jvchat_schema.auth_users_info ON jvchat_schema.online_users_info.id_user = jvchat_schema.auth_users_info.id
    WHERE jvchat_schema.online_users_info.status = 2;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.online_users_info_get_time_by_user_uuid(f_uuid_user character varying)
    RETURNS timestamp AS
$BODY$
DECLARE
    rv timestamp;
BEGIN
    SELECT jvchat_schema.online_users_info.last_online_time
    INTO rv
    FROM jvchat_schema.online_users_info 
    LEFT JOIN jvchat_schema.auth_users_info ON jvchat_schema.online_users_info.id_user = jvchat_schema.auth_users_info.id
    WHERE jvchat_schema.auth_users_info.uuid_user = f_uuid_user;

    IF found THEN
        RETURN rv;
    END IF;
    RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.online_users_info_save (
    f_uuid_user         character varying,
    f_status        integer
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
    rs bool;
    f_id_user integer;
BEGIN
    rv := -1;
    SELECT id INTO f_id_user FROM jvchat_schema.auth_users_info WHERE uuid_user = f_uuid_user;

    PERFORM * FROM jvchat_schema.online_users_info WHERE id_user = f_id_user;
    IF found THEN
        UPDATE jvchat_schema.online_users_info SET status = f_status, last_online_time = NOW() WHERE id_user = f_id_user;
        rv := 1;
    ELSE
        INSERT INTO jvchat_schema.online_users_info(id_user, status, last_online_time) VALUES (f_id_user, f_status, NOW());
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION jvchat_schema.online_users_info_update_time (
    f_id_user   integer
)
    RETURNS integer AS
$BODY$
DECLARE
    rv integer;
BEGIN
    rv := -1;
    PERFORM * FROM jvchat_schema.online_users_info WHERE id_user = f_id_user;
    IF found THEN
        UPDATE jvchat_schema.online_users_info SET last_online_time = NOW() WHERE id_user = f_id_user;
        rv := 1;
    ELSE
        rv := 2;
    END IF;

    RETURN rv;
END;
$BODY$ LANGUAGE plpgsql;

CREATE FUNCTION jvchat_schema.online_users_info_create_line() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  INSERT INTO jvchat_schema.online_users_info (id_user, status) VALUES (NEW.id, 0);
  RETURN NEW;
END;
$$;

CREATE TRIGGER online_users_info_create_line_trigger_i
    AFTER INSERT ON jvchat_schema.auth_users_info 
    FOR EACH ROW
    EXECUTE PROCEDURE jvchat_schema.online_users_info_create_line();
