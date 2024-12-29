#!/bin/bash

DIR=$(realpath $0 | sed -e "s/\/make_default_db.*//g")
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_root {
    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo "Run this script with root privileges"
        exit 1
    fi
}

function run {
    bash $DIR"/pre_inst_db.sh" -i -c
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] . Fail pre_inst_db.sh..."
        exit 1 
    fi
    $DIR"/db_creator.py" -r clear
}

check_root
run
