#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/build-jvchat.log"
USER_SYSTEM="postgres"
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function exit_package { 
    echo -e "Install requirements scripts/requirements/install_requirements.sh"
    exit 1 
}

function check_package {
    (dpkg -s $1 | grep "Status") >> $LOG_FILE 2>&1

    if [[ $? -eq 1 ]]; then
        exit_package $1
    fi    
}

function check_requirements {
    echo -n "[...] check and install package"

    mapfile -t REQUIREMENTS < <(cat $PROJECT_DIR/data/requirements)
    for req in "${!REQUIREMENTS[@]}"
    do
        check_package ${REQUIREMENTS[$req]}
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
    mvn clean install
    echo -e "\\r[ $CHECK_MARK ] building"
}

check_requirements
maven_start
