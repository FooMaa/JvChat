#!/bin/bash
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"
LOG_FILE="/tmp/run-jvchat.log"

function run {
    echo -n "[...] running"
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    mvn exec:java >> $LOG_FILE 2>&1
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

build
run