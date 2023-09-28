#!/usr/bin/python3
# coding=utf-8

import base64
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
DEFAULT_DB_TEMPLATE = 'chat_template'
DEFAULT_BACKUP_FOLDER = '/tmp/'
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
user_connection = dict({"username": DEFAULT_DB_USER,
                        "host": DEFAULT_DB_IP,
                        "port": 5432,
                        "database": ADMIN_DB,
                        "password": DB_USER_PWD})


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

    sys.stdout.write(PENDING + "create user {0}".format(DEFAULT_DB_USER))

    if db.exists('pg_roles', 'rolname', DEFAULT_DB_USER):
        print(SUCCESS + "Create user {0}. Role already exists".format(DEFAULT_DB_USER))
    else:
        db.query("CREATE USER {0} CREATEDB CREATEROLE REPLICATION PASSWORD '{1}';".format(DEFAULT_DB_USER, DB_USER_PWD))
        print(SUCCESS + "Create user {0}".format(DEFAULT_DB_USER))

    db.close()


if __name__ == '__main__':
    create_user()