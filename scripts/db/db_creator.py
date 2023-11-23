#!/usr/bin/python3
# coding=utf-8

import base64
import glob
import os.path
import re
import sys
import subprocess
import psycopg2
from argparse import ArgumentParser

ADMIN_USER = 'postgres'
ADMIN_DB = 'postgres'
STOCK_ADMIN_PWD = '9999'  # base64.b64decode('1234567890').decode('utf-8')
DEFAULT_DB_USER = 'jvchat'
STOCK_USER_PWD = '1111'
DEFAULT_DB_IP = '127.0.0.1'
DEFAULT_DB_NAME = 'chat'
DEFAULT_BACKUP_FOLDER = '/tmp/'
DEFAULT_SCHEMA = 'chat_schema'
DEFAULT_BACKUP = "chat_dump.bak"

RED = '\033[31m'
GREEN = '\033[32m'
END = '\033[0m'
PENDING = '[...] '
SUCCESS = "\r[ " + GREEN + "OK" + END + " ] "
FAIL = "\r[ " + RED + "FAIL" + END + " ] "
FNULL = open(os.devnull, 'w')

parser = ArgumentParser()
parser.add_argument('-r', nargs='?', type=str, dest='regime', default='default', help='[OPTIONAL] Set regime \'dump\' or \'restore\' or \'clear\'. Default \'default\'')
parser.add_argument('-a', nargs='?', type=str, dest='ADMIN_PWD', default=STOCK_ADMIN_PWD, help='[OPTIONAL] Set password for admin. Default {0}'.format(STOCK_ADMIN_PWD))
parser.add_argument('-u', nargs='?', type=str, dest='DB_USER_PWD', default=STOCK_USER_PWD, help='[OPTIONAL] Set password for admin. Default {0}'.format(STOCK_USER_PWD))
args = parser.parse_args()


def arg(name):
    return args.__dict__[name]


ADMIN_PWD = arg('ADMIN_PWD')
DB_USER_PWD = arg('DB_USER_PWD')
regime = arg('regime')

if DB_USER_PWD == "" or DB_USER_PWD == None:
    DB_USER_PWD = STOCK_USER_PWD
if ADMIN_PWD == "" or ADMIN_PWD == None:
    ADMIN_PWD = STOCK_ADMIN_PWD

admin_default_connection = dict({"username": ADMIN_USER,
                         "host": DEFAULT_DB_IP,
                         "port": 5432,
                         "database": ADMIN_DB,
                         "password": ADMIN_PWD})


def atoi(text):
    return int(text) if text.isdigit() else text


def natural_keys(text):
    return [atoi(c) for c in re.split('(\\d+)', text)]


os.chdir(os.path.dirname(os.path.realpath(sys.argv[0])))
ALL_TABLES = [sql for sql in glob.glob('*.sql')]

#раскомментировать только для таблиц рядом со скриптом
#CREATE_TABLES = [x for x in ALL_TABLES if "fill_" not in x]
#CREATE_TABLES.sort(key=natural_keys)
#FILL_TABLES = [x for x in ALL_TABLES if "fill_" in x]
#FILL_TABLES.sort(key=natural_keys)

#если потребуются еще схемы, то делать аналогично
CREATE_TABLES_DEFAULT_SCHEMA = [os.path.basename(sql) for sql in glob.glob("{0}/*.sql".format(DEFAULT_SCHEMA)) if "fill_" not in sql]
CREATE_TABLES_DEFAULT_SCHEMA.sort(key=natural_keys)
CREATE_TABLES_DEFAULT_SCHEMA = [DEFAULT_SCHEMA + '/' + x for x in CREATE_TABLES_DEFAULT_SCHEMA]

FILL_TABLES_DEFAULT_SCHEMA = [os.path.basename(sql) for sql in glob.glob("{0}/*.sql".format(DEFAULT_SCHEMA)) if "fill_" in sql]
FILL_TABLES_DEFAULT_SCHEMA.sort(key=natural_keys)
FILL_TABLES_DEFAULT_SCHEMA = [DEFAULT_SCHEMA + '/' + x for x in FILL_TABLES_DEFAULT_SCHEMA]



class DataBase:
    def __init__(self, url):
        self.connection = None
        self.cursor = None
        self.url = None
        self.info = None

        if url:
            self.url = url
            self.open(url)

    def open(self, url):
        try:
            self.connection = psycopg2.connect(database=self.url["database"],
                                               user=self.url["username"],
                                               password=self.url["password"],
                                               host=self.url["host"],
                                               port=self.url["port"])
        except psycopg2.DatabaseError as err:
            sys.stdout.flush()
            sys.stdout.write(FAIL + "Connection to database {0}\nError: {1}".format(self.url["database"], err) + '\n')
            exit(1)
        self.cursor = self.connection.cursor()
        self.connection.autocommit = True
        self.url = url

    def query(self, command):
        try:
            self.cursor.execute(command)
        except psycopg2.OperationalError as err:
            sys.stdout.flush()
            sys.stdout.write(FAIL + "Execution fail.\nError: {0}".format(err) + '\n')
            exit(1)

    def get_data(self, table, columns):
        command = "SELECT {0} FROM {1};".format(columns, table)
        try:
            self.query(command)
        except psycopg2.OperationalError as err:
            sys.stdout.flush()
            sys.stdout.write(FAIL + "Execution fail.\nError: {0}".format(err) + '\n')
            exit(1)
        data = self.cursor.fetchall()
        return data

    def exists(self, table, columns, record):
        data_list = self.get_data(table, columns)
        if (record,) in data_list:
            return True
        return False

    def close(self):
        self.connection.commit()
        self.cursor.close()
        self.connection.close()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.close()


def create_connection_db(db_name, db_user, db_pwd):
    connection = dict({"username": db_user,
                       "host": DEFAULT_DB_IP,
                       "port": 5432,
                       "database": db_name,
                       "password": db_pwd})
    return connection

def get_tables(db_name):
    # на каждую новую БД делать такую проверку
    if db_name == DEFAULT_DB_NAME:
        create_tables = CREATE_TABLES_DEFAULT_SCHEMA[:]
        for table in FILL_TABLES_DEFAULT_SCHEMA[:]:
            create_tables.append(table)
        
    return create_tables

# self.cursor.execute(open("1_create_users.sql", "r").read())
def create_user(db_user, db_pwd):
    sys.stdout.write(PENDING + "Create user {0}".format(db_user))
    db = DataBase(admin_default_connection)

    if db.exists('pg_roles', 'rolname', db_user):
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create user {0}. Role already exists".format(db_user) + '\n')
    else:
        db.query("CREATE USER {0} CREATEDB CREATEROLE REPLICATION PASSWORD '{1}';".format(db_user, db_pwd))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create user {0}".format(db_user) + '\n')

    db.close()


def drop_database(db_name, db_user):
    sys.stdout.write(PENDING + "Drop database {0}".format(db_name))

    db = DataBase(admin_default_connection)

    if db.exists('pg_database', 'datname', db_name):
        db.query("DROP DATABASE \"{0}\";".format(db_name))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop database {0}".format(db_name) + '\n')
    else:
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop database {0}. Database not exists, no need to drop".format(db_name) + '\n')

    db.close()


def update_public_schema_db(db_name, db_user):
    db_admin = DataBase(create_connection_db(db_name, ADMIN_USER, ADMIN_PWD))
    db_admin.query("ALTER SCHEMA public OWNER TO {0};".format(db_user))
    db_admin.close()


def create_database(db_name, db_user):
    sys.stdout.write(PENDING + "Create database {0}".format(db_name))
    db = DataBase(create_connection_db(ADMIN_DB, db_user, DB_USER_PWD))

    if db.exists('pg_database', 'datname', db_name):
        update_public_schema_db(db_name, db_user)
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create database {0}. Database already exists".format(db_name) + '\n')
    else:
        db.query("CREATE DATABASE \"{0}\" OWNER {1}".format(db_name, db_user))
        update_public_schema_db(db_name, db_user)
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create database {0}".format(db_name) + '\n')

    db.close()


def create_schema(db_schema_name, db_name, db_user):
    sys.stdout.write(PENDING + "Create and update schemas {0}".format(db_schema_name))

    db = DataBase(create_connection_db(db_name, db_user, DB_USER_PWD))
    db.query("DROP SCHEMA IF EXISTS {0} CASCADE;".format(db_schema_name))
    db.query("CREATE SCHEMA {0};".format(db_schema_name))
    
    db.close()
    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Create and update schemas {0}".format(db_schema_name) + '\n')


def create_tables(db_name, db_user):
    sys.stdout.write(PENDING + "Create and fill all tables for {0}".format(db_name))

    db = DataBase(create_connection_db(db_name, db_user, DB_USER_PWD))

    db_tables = get_tables(db_name)

    for table in db_tables:
        db.query(open(table, 'r').read())

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Create and fill all tables for {0}".format(db_name) + '\n')

    db.close()


def make_dump_db(backup_dir, db_name, file_name, db_user, db_host):
    sys.stdout.write(PENDING + "Dump database {0}".format(db_name))
    if not os.path.exists(backup_dir):
        os.makedirs(backup_dir)
    dump_file = os.path.join(os.path.normpath(backup_dir), file_name)
    backup_call = ['pg_dump', '-Fc', '--inserts', '-h', db_host, '-U', db_user,
                   db_name, '-f', dump_file]
    #rv = subprocess.call(backup_call, stdout=FNULL, stderr=FNULL)
    rv = subprocess.run(backup_call, stdout=subprocess.DEVNULL, stderr=subprocess.PIPE) #change PIPE to DEVNULL if not need stderr
    #if rv != 0:
    if rv.returncode != 0:
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Dump database {0}".format(db_name) + '\n')
        sys.stdout.write(rv.stderr.decode() + '\n') #if need mistakes
        exit(1)

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Dump database {0}".format(db_name) + '\n')
    exit(0)


def make_pg_restore(backup_dir, db_name, file_name, db_user, db_host, db_schemas):
    sys.stdout.write(PENDING + "Restore database {0} from dump".format(db_name)) # comment if drop db

    if not os.path.isfile("{0}/{1}".format(backup_dir, file_name)):
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Restore database {0} from dump. No path \"Dump\"".format(db_name) + '\n')
        exit(1)

    backup_call = ['pg_restore', '-Fc', '-h', db_host, '-U', db_user, '-d',
                   db_name, "{0}/{1}".format(backup_dir, file_name)]

    db = DataBase(create_connection_db(db_name, db_user, DB_USER_PWD)) # if drop db delete this
    # пока удаляю каждую схему, можно просто удалять БД, закомментировано ниже
    for db_schema in db_schemas:
        db.query("DROP SCHEMA IF EXISTS {0} CASCADE;".format(db_schema))
    db.close()
    
    # раскомментировать, если потребуется при накатывании БД пересоздать БД 
    #drop_database(db_name, db_user) drop_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)
    #create_database(db_name, db_user) create_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)
    #sys.stdout.write(PENDING + "Restore database {0} use dump".format(db_name))
    #rv = subprocess.call(backup_call, stdout=FNULL, stderr=FNULL)

    rv = subprocess.run(backup_call, stdout=subprocess.DEVNULL, stderr=subprocess.PIPE) #change PIPE to DEVNULL if not need stderr
    #if rv != 0:
    if rv.returncode != 0:
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Restore database {0} from dump".format(db_name) + '\n')
        sys.stdout.write(rv.stderr.decode() + '\n') #if need mistakes
        exit(1)

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Restore database {0} from dump".format(db_name) + '\n')
    exit(0)


def clear_all(db_name, db_user, db_schema):
    drop_database(db_name, db_user)

    sys.stdout.write(PENDING + "Drop schema {0}".format(db_schema))
    db = DataBase(admin_default_connection)
    db.query("DROP SCHEMA IF EXISTS {0} CASCADE;".format(db_schema))
    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Drop schema {0}".format(db_schema) + '\n')

    sys.stdout.write(PENDING + "Drop role {0}".format(db_user))
    if db.exists('pg_roles', 'rolname', db_user):
        db.query("DROP ROLE {0};".format(db_user))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop role {0}".format(db_user) + '\n')
    else:
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "No role {0}. Skipped drop".format(db_user) + '\n')

    sys.stdout.write(PENDING + "Clear all") # чтоб не бить вывод
    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Clear all" + '\n')
    db.close()
    #exit(0) #если нужно просто стереть


def check_param():
    sys.stdout.write(PENDING + "Checking calc parameters")

    for i in range(len(sys.argv) - 1): 
        for j in range(i + 1, len(sys.argv)):
            if sys.argv[i].startswith('-') and sys.argv[j].startswith('-') and (sys.argv[i][:2] == sys.argv[j][:2]):
                sys.stdout.flush()
                sys.stdout.write(FAIL + "You have repeate param \"{0}\". See help.".format(sys.argv[i][:2]) + '\n')
                exit(1)

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Checking calc parameters" + '\n')

def check_regime():
    #в дальнейшем дамп можно делать не в ./Dump, а в /tmp/Dump  
    if regime == 'dump':
        os.environ['PGPASSWORD'] = DB_USER_PWD
        make_dump_db('./Dump', DEFAULT_DB_NAME, 'dump.tar.gz',
                 DEFAULT_DB_USER, '127.0.0.1') # Если нужны еще дампы, то делать аналогично
    elif regime == 'restore':
        SCHEMA_LIST = [DEFAULT_SCHEMA]
        # ... SCHEMA_LIST.append(...)
        os.environ['PGPASSWORD'] = DB_USER_PWD
        make_pg_restore('./Dump', DEFAULT_DB_NAME, 'dump.tar.gz',
                    DEFAULT_DB_USER, '127.0.0.1', SCHEMA_LIST) # Если нужны из других дампов, то делать аналогично
    elif regime == 'clear':
        clear_all(DEFAULT_DB_NAME, DEFAULT_DB_USER, DEFAULT_SCHEMA)
    elif regime != 'default':
        sys.stdout.flush()
        sys.stdout.write(FAIL + "See help to get correct parameter to regim" + '\n')
        exit(1)

if __name__ == '__main__':
    check_param() 
    check_regime()
    
    create_user(DEFAULT_DB_USER, DB_USER_PWD)
    drop_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)
    create_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если нужны новые Базы Данных, то вызывать также этот метод
    create_schema(DEFAULT_SCHEMA, DEFAULT_DB_NAME,
                  DEFAULT_DB_USER)  # если нужны новые схемы, то вызывать также этот метод
    create_tables(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если баз данных несколько, то вызвать для каждой
