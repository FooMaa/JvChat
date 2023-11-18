#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/build-jvchat.log"
USER_SYSTEM="postgres"
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function exit_package { 
    echo -e "Install dependencies scripts/dependencies/install_dependencies.sh"
    exit 1 
}

function check_package {
    (dpkg -s $1 | grep "Status") >> $LOG_FILE 2>&1

    if [[ $? -eq 1 ]]; then
        exit_package $1
    fi    
}

function check_dependencies {
    echo -n "[...] check and install package"

    mapfile -t DEPENDENCIES < <(cat $PROJECT_DIR/data/dependencies)
    for dep in "${!DEPENDENCIES[@]}"
    do
        check_package ${DEPENDENCIES[$dep]}
    done    
    
    echo -e "\\r[ $CHECK_MARK ] check and install package"
}

function setting_package {
    echo -n "[...] setting packages"
    update-alternatives --config java
    export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64
    echo -e "\\r[ $CHECK_MARK ] setting packages"
}

function maven_start {
    echo -n "[...] building"
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    mvn clean install >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] building"
        cat "$LOG_FILE"
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] building"
}

check_dependencies
maven_start
