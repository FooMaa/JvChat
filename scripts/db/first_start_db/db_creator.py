#!/usr/bin/python3
# coding=utf-8

import base64
import psycopg2


ADMIN_USER = 'postgres'
ADMIN_DB = 'postgres'
#ADMIN_PWD = base64.b64decode('1234567890').decode('utf-8')
DEFAULT_DB_USER = 'jvchat'
#DB_USER_PWD = base64.b64decode('0987654321').decode('utf-8')
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

conn = psycopg2.connect (user = ADMIN_USER, password = '9999', host = "127.0.0.1")
curs = conn.cursor()
conn.autocommit = True
s = "CREATE DATABASE test;"
curs.execute(s)

