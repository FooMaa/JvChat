#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/check_and_install_dependencies.log"
USER=$(whoami)
PROFILE=""
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"


function usage {
    cat <<EOF
    Usage: $0 [options]
    -p      check dependencies profile      check dependencies by profile (REQUIRED) Example $0 -p users
    -h      help menu                       to see this help (OPTIONAL) Example $0 -h
EOF
}

function check_and_install_packages {
    echo -n "[...] check packages for $PROFILE"
    mvn -v >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check maven packages. Installing..."
        check_sudo mvn 
        $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -m
        echo -n "[...] check packages for $PROFILE"
    fi

    gradle -v >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check gradle packages. Installing..."
        check_sudo gradle
        $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -g
        echo -n "[...] check packages for $PROFILE"
    fi

    mapfile -t REQUIREMENTS < <(cat $PROJECT_DIR"data/dependencies_$PROFILE")
    for req in "${!REQUIREMENTS[@]}"
    do
        (dpkg -s ${REQUIREMENTS[$req]} | grep "install ok installed") >> $LOG_FILE 2>&1
        EXIT_CODE=$?
        if [[ $EXIT_CODE -ne 0 ]]; then
            echo -e "\\r[ $CROSS_MARK ] check repo packages. Installing..."
            check_sudo ${REQUIREMENTS[$req]}
            $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -r -p $PROFILE
            echo -n "[...] check packages for $PROFILE"
        fi
    done
    echo -e "\\r[ $CHECK_MARK ] check packages for $PROFILE"
}

function Sudo {
    local firstArg=$1
    if [ $(type -t $firstArg) = function ]; then
        shift && command sudo bash -c "$(declare -f $firstArg);$firstArg $*"
    elif [ $(type -t $firstArg) = alias ]; then
        alias sudo='\sudo '
        eval "sudo $@"
    else 
        command sudo "$@"
    fi
}

function check_sudo {
    if [ "$USER" != root ]; then 
        echo "This script runned as user. Fail dependense: $1. You should run scripts/dependencies/install_dependencies.sh as root."
        rm $LOG_FILE
        exit 1
    fi
} 

function check_has_param {
    if [ -z "$1" ]; then 
        echo "This script need a parameters"
        usage
        exit 1
    fi
}

function check_parameters {
    echo -n "[...] check parameters"
    if [[ -z "$PROFILE" ]]; then
        echo -e "\\r[ $CROSS_MARK ] Profile not defined."
        exit 1
    fi

    if [[ "$PROFILE" != "users" && "$PROFILE" != "servers" && "$PROFILE" != "tests" ]]; then 
        echo -e "\\r[ $CROSS_MARK ] Wrong profile defined. Acceptible profiles: users, servers, tests."
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] check parameters"
}

check_has_param $1

while [ -n "$1" ]; do
    case "$1" in
        -p ) if [[ $PROFILE != "" ]]; then echo -e "\\rGive 1 profile"; usage; exit 1; else  PROFILE=$2 ; fi; shift ;;
        -h ) usage; exit 1;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

check_parameters
check_and_install_packages
rm $LOG_FILE # убрать если понадобится лог
