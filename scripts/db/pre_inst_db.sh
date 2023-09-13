#!/bin/bash

DIR=$(realpath $0 | sed -e "s/pre_inst_db.*//g")
POST_PWD=""
NEED_INSTALL=false
NEED_PGHBA=false

function usage {
    cat <<EOF
    Usage: $0 [options]
    -i      install requirements    install requirements ( OPTIONAL ) Example ./pre_inst_db.sh -i
    -h      help menu               to see this help ( OPTIONAL ) Example ./pre_inst_db.sh -h
    -p      need make pg_hba.conf   copy pg_hba.conf from repo ( OPTIONAL ) Example ./pre_inst_db.sh -h
    -w      set password            set defaut password to postgres ( REQUIRED ) Example ./pre_inst_db.sh -w '9999'
EOF
}

function check_root {
    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo "Run this script as root"
        exit 1
    fi
}

function install_requirements {
    apt-get update
    apt-get install -y $(cat requirements)
}

function set_pwd_postgres {
    echo -e "$POST_PWD\n$POST_PWD" | passwd postgres
}

function make_pg_hba_file {
    pushd $DIR
    rm /etc/postgresql/14/main/pg_hba.conf
    chmod 644 pg_hba.conf
    cp pg_hba.conf /etc/postgresql/14/main
    service postgresql restart
}

function update_pwd_postgtes_from_db {
    sudo -u postgres psql -c "ALTER USER postgres PASSWORD '$POST_PWD';"
}

while [ -n "$1" ]; do
    case "$1" in
        -i ) NEED_INSTALL=true ;;
        -p ) NEED_PGHBA=true ;;
        -w ) POST_PWD=$2; shift ;;
        -h ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

    check_root

if [[ $NEED_INSTALL == true ]]; then 
    install_requirements
fi

set_pwd_postgres

if [[ $NEED_PGHBA == true ]]; then 
    make_pg_hba_file
fi

if [[ "$POST_PWD" != '' ]]; then 
    echo $POST_PWD
    update_pwd_postgtes_from_db
fi
