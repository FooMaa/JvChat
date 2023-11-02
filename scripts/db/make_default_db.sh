#!/bin/bash

DIR=$(realpath $0 | sed -e "s/\/make_default_db.*//g")

function check_root {
    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo -e "\\rRun this script with root privileges"
        exit 1
    fi
}

function run {
    $DIR/pre_inst_db.sh -i -p
    $DIR/db_creator.py
}

check_root
run
