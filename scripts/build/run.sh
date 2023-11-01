#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/run-jvchat.log"
USER_SYSTEM="postgres"
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_root {
    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo -e "\\rRun this script with root privileges"
        exit 1
    fi
}

function install_package { 
    if [[ $UPDATING_REPO == false ]]; then
        apt update >> $LOG_FILE 2>&1
        UPDATING_REPO=true
    fi
    
    apt install -y $1 >> $LOG_FILE 2>&1
}

function check_package {
    (dpkg -s $1 | grep "Status") >> $LOG_FILE 2>&1

    if [[ $? -eq 1 ]]; then
        install_package $1
    fi    
}

function install_requirements {
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

function update_maven {
    echo -n "[...] updating maven"
    
    tar -xzf $PROJECT_DIR/data/apache-maven*.tar.gz
    rm -r /usr/share/maven/*
    mv apache-maven*/* /usr/share/maven/
    rm -r apache-maven*
    echo -e "\\r[ $CHECK_MARK ] updating maven"
}

function maven_start {
    echo -n "[...] running"
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    mvn clean install exec:java >> $LOG_FILE 2>&1
    echo -e "\\r[ $CHECK_MARK ] running"
}

check_root
install_requirements
update_maven
maven_start
