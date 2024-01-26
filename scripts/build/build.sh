#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/build-jvchat.log"
USER_SYSTEM="postgres"
PROFILE=""
BUILDER=""
NEED_CHECK=false
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_user {
    USER=$(whoami)
    if [ "$USER" == root ]; then 
        echo "Run this script with user privileges"
        exit 1
    fi
}

function check_packages {
    $PROJECT_DIR"scripts/dependencies/check_and_install_dependencies.sh" -p $PROFILE
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        # если тут бьется вывод, то убрать \\r
        echo -e "\\r[ $CROSS_MARK ] check packages. Fail check_dependencies.sh."
        exit 1 
    fi
}

function build_start {
    echo -n "[...] building"
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    
    if [[ $BUILDER == "maven" ]]; then 
       mvn clean install -P$PROFILE >> $LOG_FILE 2>&1
    elif [[ $BUILDER == "gradle" ]]; then
       gradle clean build -P$PROFILE >> $LOG_FILE 2>&1
    fi
    
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] building"
        tail -10 "$LOG_FILE"
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] building"
}

function usage {
    cat <<EOF
    Usage: $0 [options]
    -c      check dependencies  check dependencies (OPTIONAL) Example $0 -c
    -g	    use gradle		    build with gradle 	(REQUIRED) Example $0 -g
    -m      use maven		    build use maven 	(REQUIRED) Example $0 -m
    -p      run profile         run servers profile (REQUIRED) Example $0 -p users
    -h      help menu           to see this help (OPTIONAL) Example $0 -h
EOF
}

function check_has_param {
    if [ -z "$1" ]; then 
        echo "This script need a parameters"
        usage
        exit 1
    fi
}

function check_set_param {
    if [[ -z "$PROFILE" ]]; then
        echo -e "Profile not defined."
        usage
        exit 1
    fi

    if [[ $PROFILE != "" && $PROFILE != "tests" && $PROFILE != "users" && $PROFILE != "servers" ]]; then
        echo "Give correct profile param to script."
        usage
        exit 1
    fi    

    if [[ $BUILDER != "maven" && $BUILDER != "gradle" ]]; then
       echo "Give builder param to script"
       usage
       exit 1
    fi
}

check_user
check_has_param $1

while [ -n "$1" ]; do
    case "$1" in
        -c ) NEED_CHECK=true ;;
        -m ) if [[ $BUILDER != "" ]]; then echo -e "\\rGive 1 builder"; usage; exit 1; else BUILDER="maven"; fi ;;
        -g ) if [[ $BUILDER != "" ]]; then echo -e "\\rGive 1 builder"; usage; exit 1; else BUILDER="gradle"; fi ;;
        -p ) if [[ $PROFILE != "" ]]; then echo -e "\\rGive 1 profile"; usage; exit 1; else  PROFILE=$2 ; fi; shift ;;
        -h ) usage; exit 1 ;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

check_set_param

if [[ $NEED_CHECK == true ]]; then 
    check_packages
fi

build_start
