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
parser.add_argument('-r', '--regime', nargs='?', type=str, dest='regime', default='default', help='[OPTIONAL] Set regime \'dump\' or \'restore\' or \'clear\'. Default \'default\'')
parser.add_argument('-a', '--adminpwd', nargs='?', type=str, dest='ADMIN_PWD', default=STOCK_ADMIN_PWD, help='[OPTIONAL] Set password for admin. Default {0}'.format(STOCK_ADMIN_PWD))
parser.add_argument('-u', '--userpwd', nargs='?', type=str, dest='DB_USER_PWD', default=STOCK_USER_PWD, help='[OPTIONAL] Set password for admin. Default {0}'.format(STOCK_USER_PWD))
args = parser.parse_args()


def arg(name):
    return args.__dict__[name]



ADMIN_PWD = arg('ADMIN_PWD')
DB_USER_PWD = arg('DB_USER_PWD')
regime = arg('regime')

admin_connection = dict({"username": ADMIN_USER,
                         "host": DEFAULT_DB_IP,
                         "port": 5432,
                         "database": ADMIN_DB,
                         "password": ADMIN_PWD})
user_default_connection = dict({"username": DEFAULT_DB_USER,
                                "host": DEFAULT_DB_IP,
                                "port": 5432,
                                "database": ADMIN_DB,
                                "password": DB_USER_PWD})


def atoi(text):
    return int(text) if text.isdigit() else text


def natural_keys(text):
    return [atoi(c) for c in re.split('(\\d+)', text)]


os.chdir(os.path.dirname(os.path.realpath(sys.argv[0])))
ALL_TABLES = [sql for sql in glob.glob('*.sql')]

CREATE_TABLES = [x for x in ALL_TABLES if "fill_" not in x]
CREATE_TABLES.sort(key=natural_keys)


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
            sys.stdout.write(PENDING + "Create connection for {0}".format(url["username"]))
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
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create connection for {0}".format(url["username"]) + '\n')

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
        sys.stdout.write(PENDING + "Close connection for {0}".format(self.url["username"]))
        self.connection.commit()
        self.cursor.close()
        self.connection.close()
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Close connection for {0}".format(self.url["username"]) + '\n')

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.close()


# self.cursor.execute(open("1_create_users.sql", "r").read())
def create_user(db_user, db_pwd):
    db = DataBase(admin_connection)

    sys.stdout.write(PENDING + "Create user {0}".format(db_user))

    if db.exists('pg_roles', 'rolname', db_user):
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create user {0}. Role already exists".format(db_user) + '\n')
    else:
        db.query("CREATE USER {0} CREATEDB CREATEROLE REPLICATION PASSWORD '{1}';".format(db_user, db_pwd))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create user {0}".format(db_user) + '\n')

    db.close()


def create_database(name, db_owner):
    db = DataBase(user_default_connection)

    sys.stdout.write(PENDING + "Create database {0}".format(name))

    if db.exists('pg_database', 'datname', name):
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create user {0}. Database already exists".format(name) + '\n')
    else:
        db.query("CREATE DATABASE \"{0}\" OWNER {1}".format(name, db_owner))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Create database {0}".format(name) + '\n')

    db.close()


def create_schema(db_schema_name, db_name, db_user):
    db = DataBase(create_connection_db(db_name, db_user))

    sys.stdout.write(PENDING + "Create schema {0}".format(db_schema_name))

    db.query("DROP SCHEMA IF EXISTS {0};".format(db_schema_name))
    db.query("CREATE SCHEMA {0};".format(db_schema_name))
    
    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Create schema {0}".format(db_schema_name) + '\n')
    db.close()


def create_connection_db(db_name, db_user):
    connection = dict({"username": db_user,
                       "host": DEFAULT_DB_IP,
                       "port": 5432,
                       "database": db_name,
                       "password": DB_USER_PWD})
    return connection


def get_tables(db_name):
    if db_name == DEFAULT_DB_NAME:
        create_tables = CREATE_TABLES[:]

    return create_tables


def create_tables(db_name, db_user):
    db = DataBase(create_connection_db(db_name, db_user))

    sys.stdout.write(PENDING + "Create all tables for {0}".format(db_name))

    db_tables = get_tables(db_name)
    for table in db_tables:
        db.query(open(table, 'r').read())

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Create all tables for {0}".format(db_name) + '\n')

    db.close()


def drop_database(db_name, db_user):
    db = DataBase(admin_connection)

    sys.stdout.write(PENDING + "Drop database {0}".format(db_name))

    if db.exists('pg_database', 'datname', db_name):
        db.query("DROP DATABASE \"{0}\";".format(db_name))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop database {0}".format(db_name) + '\n')
    else:
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop database {0}. Database not exists, no need to drop".format(db_name) + '\n')

    db.close()


def make_dump_db(backup_dir, db_name, file_name, db_user, db_host):
    sys.stdout.write(PENDING + "Dump database {0}".format(db_name))
    if not os.path.exists(backup_dir):
        os.makedirs(backup_dir)
    dump_file = os.path.join(os.path.normpath(backup_dir), file_name)
    backup_call = ['pg_dump', '-Fc', '--inserts', '-h', db_host, '-U', db_user,
                   db_name, '-f', dump_file]
    rv = subprocess.call(backup_call, stdout=FNULL, stderr=FNULL)

    if rv != 0:
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Dump database {0}".format(db_name) + '\n')
        exit(1)

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Dump database {0}".format(db_name) + '\n')
    exit(0) # если надо выйти из скрипта после создания дампа


def make_pg_restore(backup_dir, db_name, file_name, db_user, db_host, db_schemas):
    sys.stdout.write(PENDING + "Restore database {0} from dump".format(db_name))

    if not os.path.isfile("{0}/{1}".format(backup_dir, file_name)):
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Restore database {0} from dump".format(db_name) + '\n')
        exit(1)

    backup_call = ['pg_restore', '-Fc', '-h', db_host, '-U', db_user, '-d',
                   db_name, "{0}/{1}".format(backup_dir, file_name)]

    sys.stdout.write('\r')
    db = DataBase(create_connection_db(db_name, db_user))
    # пока удаляю каждую схему, можно просто удалять БД, закомментировано ниже
    for db_schema in db_schemas:
        db.query("DROP SCHEMA IF EXISTS {0} CASCADE;".format(db_schema))
    db.close()
    
    # раскомментировать, если потребуется при накатывании БД пересоздать БД 
    #drop_database(db_name, db_user)
    #create_database(db_name, db_user)

    rv = subprocess.call(backup_call, stdout=FNULL, stderr=FNULL)

    if rv != 0:
        sys.stdout.flush()
        sys.stdout.write(FAIL + "Restore database {0} from dump".format(db_name) + '\n')
        exit(1)

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Restore database {0} from dump".format(db_name) + '\n')
    exit(0) # если надо выйти из скрипта после накатывания дампа

def clear_all(db_name, db_user, db_schema):
    db = DataBase(admin_connection)
    #sys.stdout.write(PENDING + "Clear all")
    
    db.query("DROP SCHEMA IF EXISTS {0};".format(db_schema))
    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Drop schema {0}".format(db_schema) + '\n')

    drop_database(db_name, db_user)

    if db.exists('pg_roles', 'rolname', db_user):
        db.query("DROP ROLE {0};".format(db_user))
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "Drop role {0}".format(db_user) + '\n')
    else:
        sys.stdout.flush()
        sys.stdout.write(SUCCESS + "No role {0}. Skipped drop".format(db_user) + '\n')

    sys.stdout.flush()
    sys.stdout.write(SUCCESS + "Clear all" + '\n')
    db.close()
    #exit(1) если нужно просто стереть

if __name__ == '__main__':
    if regime == 'dump':
        os.environ['PGPASSWORD'] = DB_USER_PWD
        make_dump_db('./Dump', DEFAULT_DB_NAME, 'dump.tar.gz',
                 DEFAULT_DB_USER, '127.0.0.1')
    elif regime == 'restore':
        SCHEMA_LIST = [DEFAULT_SCHEMA]
        # ... SCHEMA_LIST.append(...)
        os.environ['PGPASSWORD'] = DB_USER_PWD
        make_pg_restore('./Dump', DEFAULT_DB_NAME, 'dump.tar.gz',
                    DEFAULT_DB_USER, '127.0.0.1', SCHEMA_LIST)
    elif regime == 'clear':
        clear_all(DEFAULT_DB_NAME, DEFAULT_DB_USER, DEFAULT_SCHEMA)
    elif regime != 'default':
        sys.stdout.flush()
        sys.stdout.write(FAIL + "See help to get correct parameter to regim" + '\n')
        exit(1)
    
    create_user(DEFAULT_DB_USER, DB_USER_PWD)
    drop_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)
    create_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если нужны новые Базы Данных, то вызывать также этот метод
    create_schema(DEFAULT_SCHEMA, DEFAULT_DB_NAME,
                  DEFAULT_DB_USER)  # если нужны новые схемы, то вызывать также этот метод
    create_tables(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если баз данных несколько, то вызвать для каждой
