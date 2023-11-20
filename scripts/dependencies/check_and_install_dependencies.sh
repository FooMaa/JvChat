#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/check_and_install_dependencies.log"
USER=$(whoami)
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_and_install_packages {
    echo -n "[...] check packages"
    mvn -v >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check maven packages. Installing..."
        check_sudo mvn 
        $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -m
    fi

    gradle -v >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check gradle packages. Installing..."
        check_sudo gradle
        $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -g
    fi

    mapfile -t REQUIREMENTS < <(cat $PROJECT_DIR"data/dependencies")
    for req in "${!REQUIREMENTS[@]}"
    do
        (dpkg -s ${REQUIREMENTS[$req]} | grep "Status") >> $LOG_FILE 2>&1
        EXIT_CODE=$?
        if [[ $EXIT_CODE -ne 0 ]]; then
            echo -e "\\r[ $CROSS_MARK ] check repo packages. Installing..."
            check_sudo ${REQUIREMENTS[$req]}
            $PROJECT_DIR"scripts/dependencies/install_dependencies.sh" -r
        fi
    done
    echo -e "\\r[ $CHECK_MARK ] check packages"
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
        echo -e "\\rThis script runned as user. Fail dependense: $1. You should run scripts/dependencies/install_dependencies.sh as root."
        rm $LOG_FILE
        exit 1
    fi
} 

check_and_install_packages
rm $LOG_FILE # убрать если понадобится лог
