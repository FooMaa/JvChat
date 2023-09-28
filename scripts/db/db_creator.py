#!/usr/bin/python3
# coding=utf-8

import base64
import glob
import os.path
import re
import sys
import subprocess
import psycopg2

ADMIN_USER = 'postgres'
ADMIN_DB = 'postgres'
ADMIN_PWD = '9999'  # base64.b64decode('1234567890').decode('utf-8')
DEFAULT_DB_USER = 'jvchat'
DB_USER_PWD = '1111'
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
            sys.stdout.write(FAIL + "Connection to database {0}\nError: {1}".format(self.url["database"], err))
            exit(1)
        self.cursor = self.connection.cursor()
        self.connection.autocommit = True
        print(SUCCESS + "Create connection for {0}".format(url["username"]))

    def query(self, command):
        try:
            self.cursor.execute(command)
        except psycopg2.OperationalError as err:
            sys.stdout.flush()
            sys.stdout.write(FAIL + "Execution fail.\nError: {0}".format(err))
            exit(1)

    def get_data(self, table, columns):
        command = "SELECT {0} FROM {1};".format(columns, table)
        try:
            self.query(command)
        except psycopg2.OperationalError as err:
            sys.stdout.flush()
            sys.stdout.write(FAIL + "Execution fail.\nError: {0}".format(err))
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


# self.cursor.execute(open("1_create_users.sql", "r").read())
def create_user():
    db = DataBase(admin_connection)

    sys.stdout.write(PENDING + "Create user {0}".format(DEFAULT_DB_USER))

    if db.exists('pg_roles', 'rolname', DEFAULT_DB_USER):
        print(SUCCESS + "Create user {0}. Role already exists".format(DEFAULT_DB_USER))
    else:
        db.query("CREATE USER {0} CREATEDB CREATEROLE REPLICATION PASSWORD '{1}';".format(DEFAULT_DB_USER, DB_USER_PWD))
        print(SUCCESS + "Create user {0}".format(DEFAULT_DB_USER))

    db.close()


def create_database(name, owner):
    db = DataBase(user_default_connection)

    sys.stdout.write(PENDING + "Create database {0}".format(name))

    if db.exists('pg_database', 'datname', name):
        print(SUCCESS + "Create user {0}. Database already exists".format(name))
    else:
        db.query("CREATE DATABASE \"{0}\" OWNER {1}".format(name, owner))
        print(SUCCESS + "Create database {0}".format(name))

    db.close()


def create_schema(schema_name, db_name, user):
    db = DataBase(create_connection_db(db_name, user))

    sys.stdout.write(PENDING + "Create schema {0}".format(schema_name))

    db.query("DROP SCHEMA IF EXISTS {0};".format(schema_name))
    db.query("CREATE SCHEMA {0};".format(schema_name))

    print(SUCCESS + "Create database {0}".format(schema_name))
    db.close()


def create_connection_db(db_name, user):
    connection = dict({"username": user,
                       "host": DEFAULT_DB_IP,
                       "port": 5432,
                       "database": db_name,
                       "password": DB_USER_PWD})
    return connection


def get_tables(db_name):
    if db_name == DEFAULT_DB_NAME:
        create_tables = CREATE_TABLES[:]

    return create_tables


def create_tables(db_name, user):
    db = DataBase(create_connection_db(db_name, user))

    sys.stdout.write(PENDING + "Create all tables for {0}".format(db_name))

    db_tables = get_tables(db_name)
    for table in db_tables:
        sql_file = open(table, 'r')
        db.query(sql_file.read())

    print(SUCCESS + "Create all tables for {0}".format(db_name))

    db.close()


def drop_database(db_name, user):
    db = DataBase(admin_connection)

    sys.stdout.write(PENDING + "Drop database {0}".format(db_name))

    if db.exists('pg_database', 'datname', db_name):
        db.query("DROP DATABASE \"{0}\";".format(db_name))
        print(SUCCESS + "Drop database {0}".format(db_name))
    else:
        print(SUCCESS + "Drop database {0}. Database not exists, no need to drop".format(db_name))

    db.close()


if __name__ == '__main__':
    create_user()
    drop_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)
    create_database(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если нужны новые Базы Данных, то вызывать также этот метод
    create_schema(DEFAULT_SCHEMA, DEFAULT_DB_NAME,
                  DEFAULT_DB_USER)  # если нужны новые схемы, то вызывать также этот метод
    create_tables(DEFAULT_DB_NAME, DEFAULT_DB_USER)  # если баз данных несколько, то вызвать для каждой
