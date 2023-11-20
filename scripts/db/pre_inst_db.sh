#!/bin/bash

DIR=$(realpath $0 | sed -e "s/pre_inst_db.*//g")
PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
POST_PWD=""
UPDATING_REPO=false
NEED_INSTALL=false
NEED_PGHBA=false
LOG_FILE="/tmp/pre_inst_db.log"
USER_SYSTEM="postgres"
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function usage {
    cat <<EOF
    Usage: $0 [options]
    -i      install dependencies    install dependencies (OPTIONAL) Example $0 -i
    -h      help menu               to see this help (OPTIONAL) Example $0 -h
    -p      need make pg_hba.conf   copy pg_hba.conf from repo (OPTIONAL) Example $0 -h
    -w      set password            set defaut password to postgres (OPTIONAL) Example $0 -w '9999'
EOF
}

function check_param {
    if [ -z "$1" ]; then 
        echo -e "This script need a parameters"
        usage
        exit 1
    fi
}

function check_root_and_param {
    echo -n "[...] check and scan parameters"

    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo -e "\\rRun this script with root privileges"
        exit 1
    fi

    if [[ -z "$POST_PWD" || "$POST_PWD" == "-"* ]]; then 
        POST_PWD='9999'
    fi

    echo -e "\\r[ $CHECK_MARK ] check and scan parameters"
}

function check_packages {
    $PROJECT_DIR"scripts/dependencies/check_and_install_dependencies.sh"
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check packages. Fail check_dependencies.sh.."
        exit 1 
    fi
}

function set_pwd_postgres {
    echo -n "[...] set password user $USER_SYSTEM"    

    echo -e "$POST_PWD\n$POST_PWD" | passwd $USER_SYSTEM >> $LOG_FILE 2>&1

    echo -e "\\r[ $CHECK_MARK ] set password user $USER_SYSTEM"
}

function make_pg_hba_file {
    echo -n "[...] make pg_hba.conf file"

    pushd $DIR >> $LOG_FILE 2>&1
    VERSION_PG=$(psql --version | sed -e 's/[^0-9][^0-9]*//' -e 's/\..*//')
    cp /etc/postgresql/$VERSION_PG/main/pg_hba.conf /tmp/
    rm /etc/postgresql/$VERSION_PG/main/pg_hba.conf
    chmod 644 $PROJECT_DIR"data/pg_hba.conf"
    cp $PROJECT_DIR"data/pg_hba.conf" /etc/postgresql/$VERSION_PG/main
    service postgresql restart

    echo -e "\\r[ $CHECK_MARK ] make pg_hba.conf file"
}

function update_pwd_postgtes_from_db {
    echo -n "[...] set password sql user $USER_SYSTEM"

    pushd / >> $LOG_FILE 2>&1
    sudo -u $USER_SYSTEM psql -c "ALTER USER $USER_SYSTEM PASSWORD '$POST_PWD';" >> $LOG_FILE 2>&1

    echo -e "\\r[ $CHECK_MARK ] set password sql user $USER_SYSTEM"
}

# check_param $1

while [ -n "$1" ]; do
    case "$1" in
        -i ) NEED_INSTALL=true ;;
        -p ) NEED_PGHBA=true ;;
        -w ) if [[ -z $2 || $2 == "-"* ]]; then echo -e "You send flag without password"; usage; exit 1; else POST_PWD=$2; fi; shift ;;
        -h ) usage; exit 1;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

check_root_and_param

if [[ $NEED_INSTALL == true ]]; then 
    check_packages
fi

set_pwd_postgres

if [[ $NEED_PGHBA == true ]]; then 
    make_pg_hba_file
fi

update_pwd_postgtes_from_db
