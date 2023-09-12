#!/bin/bash
DIR=$(realpath $0 | sed -e "s/pre_inst_db.*//g")
POST_PWD='9999'
echo $DIR


apt-get update
apt-get install -y $(cat requirements)

echo -e "$POST_PWD\n$POST_PWD" | passwd postgres
# copy
service postgresql restart
sudo -u postgres psql -c "ALTER USER postgres PASSWORD '$POST_PWD';"
