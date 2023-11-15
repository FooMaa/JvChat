#!/bin/bash
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"
LOG_FILE="/tmp/run-jvchat.log"
PROFILE=""

function run {
    echo -n "[...] running"
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    if [[ $PROFILE == "tests" ]]; then
        mvn clean install -Ptests
    elif [[ $PROFILE == "users" ]]; then
        mvn exec:java -Pusers >> $LOG_FILE 2>&1
    elif [[ $PROFILE == "servers" ]]; then
        mvn exec:java -Pservers >> $LOG_FILE 2>&1
    fi
    echo -e "\\r[ $CHECK_MARK ] running"
}

function build {
    echo -n "[...] building"
    bash $PROJECT_DIR"scripts/build/build.sh" >> $LOG_FILE 2>&1
    EXIT_CODE=$?
        if [[ $EXIT_CODE -ne 0 ]]; then
            echo -e "\\r[ $CROSS_MARK ] building"
            cat "$LOG_FILE"
            exit 1
        fi
    echo -e "\\r[ $CHECK_MARK ] building"
}

function usage {
    cat <<EOF
    Usage: $0 [options]
    -t      run tests               run only tests ( OPTIONAL ) Example $0 -t
    -h      help menu               to see this help ( OPTIONAL ) Example $0 -h
    -u      run users               run users profile ( OPTIONAL ) Example $0 -u
    -s      run servers             run servers profile ( OPTIONAL ) Example $0 -s
EOF
}

function check_param {
    if [ -z "$1" ]; then 
        echo -e "This script need a parameters"
        usage
        exit 1
    fi
}

check_param $1

while [ -n "$1" ]; do
    case "$1" in
        -t ) PROFILE="tests" ;;
        -u ) PROFILE="users" ;;
        -s ) PROFILE="servers"; shift ;;
        -h ) usage; exit 1;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

build
run