#!/usr/bin/python3
# coding=utf-8

import base64
import sys

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


class DataBase:
    def __init__(self):
        sys.stdout.write(PENDING + "create connection")
        self.connection = psycopg2.connect(user=ADMIN_USER, password=ADMIN_PWD, host="127.0.0.1")
        self.cursor = self.connection.cursor()
        self.connection.autocommit = True
        sys.stdout.write(SUCCESS + "create connection")

    def start_create_db(self):
        self.create_user()
        # self.cursor.execute(open("1_create_users.sql", "r").read())

    def create_user(self):
        str_user_creating = "CREATE USER {0} WITH PASSWORD \'{1}\'; ".format(DEFAULT_DB_USER, DB_USER_PWD)
        self.cursor.execute(str_user_creating)


if __name__ == '__main__':
    db = DataBase()
    db.start_create_db()
