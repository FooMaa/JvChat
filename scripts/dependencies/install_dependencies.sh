#!/bin/bash

DIR=$(realpath $0 | sed -e "s/install_dependencies.*//g")
PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
POST_PWD=""
UPDATING_REPO=false
NEED_INSTALL=false
NEED_PGHBA=false
GRADLE=false
MAVEN=false
REPO=false
LOG_FILE="/tmp/install_dependencies.log"
USER_SYSTEM="postgres"
PROFILE=""
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_root {
    USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo "Run this script with root privileges"
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

function install_package { 
    if [[ $UPDATING_REPO == false ]]; then
        apt update >> $LOG_FILE 2>&1
        UPDATING_REPO=true
    fi
    
    apt install -y $1 >> $LOG_FILE 2>&1
}

function check_package {
    (dpkg -s $1 | grep "install ok installed") >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        install_package $1
    fi
}

function install_dependencies {
    echo -n "[...] check and install repo package for $PROFILE"

    mapfile -t REQUIREMENTS < <(cat $PROJECT_DIR"data/dependencies_$PROFILE")
    for req in "${!REQUIREMENTS[@]}"
    do
        check_package ${REQUIREMENTS[$req]}
    done    
    
    echo -e "\\r[ $CHECK_MARK ] check and install repo package for $PROFILE"
}

function install_arc_dependences {
    echo -n "[...] installing $1"
    if [[ $1 == "maven" ]]; then
    	tar -xzf $PROJECT_DIR"data/apache-maven"*".tar.gz"
    	NAME_PACKET=$(ls | grep apache-maven)
    	NAME_COMMAND="mvn"
    elif [[ $1 == "gradle" ]]; then
    	unzip -q $PROJECT_DIR"data/gradle"*".zip"
    	NAME_PACKET=$(ls | grep gradle-)
    	NAME_COMMAND="gradle"
    fi
    
    cp -R $NAME_PACKET /opt
    rm -r $NAME_PACKET 
    ln -sf /opt/$NAME_PACKET/bin/$NAME_COMMAND /usr/bin/$NAME_COMMAND
    echo -e "\\r[ $CHECK_MARK ] installing $1"
}

function download_gradle {
    echo -n "[...] download gradle"
    VERSION=8.4
    wget https://services.gradle.org/distributions/gradle-${VERSION}-bin.zip -P $PROJECT_DIR"data/" >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] download gradle"
        tail -10 "$LOG_FILE"
        post_inst
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] download gradle"
}

function download_maven {
    echo -n "[...] download maven"
    VERSION=3.9.5
    wget https://downloads.apache.org/maven/maven-3/${VERSION}/binaries/apache-maven-${VERSION}-bin.tar.gz -P $PROJECT_DIR"data/" >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] download maven"
        tail -10 "$LOG_FILE"
        post_inst
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] download maven"
}

function check_builder {
    echo -n "[...] check builder $1"
    $1 -v >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check builder $1"
        tail -10 "$LOG_FILE"
        post_inst
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] check builder $1"
}

function check_internet {
    echo -n "[...] check internet"
    ping -c 1 8.8.8.8 >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] check internet"
        tail -10 "$LOG_FILE"
        post_inst
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] check internet"
}

function post_inst {
    echo -n "[...] clear archive"
    rm -r $PROJECT_DIR"apache-maven"* >> $LOG_FILE 2>&1
    rm -r $PROJECT_DIR"gradle-"* >> $LOG_FILE 2>&1
    rm -r $PROJECT_DIR"data/apache-maven"*".tar.gz"* >> $LOG_FILE 2>&1
    rm -r $PROJECT_DIR"data/gradle-"*".zip"* >> $LOG_FILE 2>&1
    echo -e "\\r[ $CHECK_MARK ] clear archive"
}

function remove_conflict_package {
    echo -n "[...] remove conflict package $1"
    (dpkg -s $1 | grep "Status") >> $LOG_FILE 2>&1
    EXIT_CODE=$?
    if [[ $EXIT_CODE -eq 0 ]]; then
        apt remove -y $1 >> $LOG_FILE 2>&1
    fi
    echo -e "\\r[ $CHECK_MARK ] remove conflict package $1"
}

function usage {
    cat <<EOF
    Usage: $0 [options]
    -m	    reinstall maven     only reinstall maven (REQUIRED) Example $0 -m
    -g	    reinstall gradle    only reinstall gradle (REQUIRED) Example $0 -g
    -a	    reinstall all       only reinstall all (REQUIRED) Example $0 -a
    -r	    reinstall repo      only reinstall from repo (REQUIRED) Example $0 -r
    -h      help menu           to see this help (OPTIONAL) Example $0 -h
    -p      profile             profile dependencies (REQUIRED) Example $0 -p users
EOF
}

function check_parameters_profile {
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

function check_param_deps {
    echo -n "[...] check parameters dependencies"
    if [[ $REPO == false && $MAVEN == false && $GRADLE == false ]]; then 
        echo -e "\\r[ $CROSS_MARK ] check parameters dependencies. Accepted: -r, -m, -g, -a"
        usage
        exit 1
    fi
    echo -e "\\r[ $CHECK_MARK ] check parameters dependencies"
}

check_root
check_has_param $1

while [ -n "$1" ]; do
    case "$1" in
        -r ) REPO=true ;;
        -m ) MAVEN=true ;;
        -g ) GRADLE=true ;;
        -a ) REPO=true; MAVEN=true; GRADLE=true ;;
        -p ) if [[ $PROFILE != "" ]]; then echo -e "\\rGive 1 profile"; usage; exit 1; else  PROFILE=$2 ; fi; shift ;;
        -h ) usage; exit 1 ;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

check_param_deps
post_inst

if [[ $REPO == true ]]; then
    check_parameters_profile
    install_dependencies
fi

if [[ $MAVEN == true ]]; then
    remove_conflict_package maven
    download_maven
    install_arc_dependences "maven"
    check_builder "mvn"
fi

if [[ $GRADLE == true ]]; then
    remove_conflict_package gradle
    download_gradle
    install_arc_dependences "gradle"
    check_builder "gradle"
fi

post_inst
