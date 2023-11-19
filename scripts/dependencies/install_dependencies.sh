#!/bin/bash

DIR=$(realpath $0 | sed -e "s/install_dependencies.*//g")
PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
POST_PWD=""
UPDATING_REPO=false
NEED_INSTALL=false
NEED_PGHBA=false
LOG_FILE="/tmp/install_dependencies.log"
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

function install_dependencies {
    echo -n "[...] check and install package"

    mapfile -t REQUIREMENTS < <(cat $PROJECT_DIR/data/dependencies)
    for req in "${!REQUIREMENTS[@]}"
    do
        check_package ${REQUIREMENTS[$req]}
    done    
    
    echo -e "\\r[ $CHECK_MARK ] check and install package"
}

function remove_arc_dependences {
    echo -n "[...] remove $1"   
    if [[ $1 == "maven" ]]; then
    	NAME_PACKET=$(ls /opt | grep apache-maven)
    	NAME_COMMAND="mvn"
    elif [[ $1 == "gradle" ]]; then
    	NAME_PACKET=$(ls /opt | grep gradle-)
    	NAME_COMMAND="gradle"
    fi
    
    if [[ $NAME_PACKET != "" && $NAME_COMMAND != "" ]]; then
    	if [ -d /opt/$NAME_PACKET ]; then
    	    rm -r /opt/$NAME_PACKET
    	fi
    	if [ -L /usr/bin/$NAME_COMMAND ]; then
    	    rm -r /usr/bin/$NAME_COMMAND
    	fi
    	if [ -f /usr/bin/$NAME_COMMAND ]; then
    	    rm -r /usr/bin/$NAME_COMMAND
    	fi
    fi
    echo -e "\\r[ $CHECK_MARK ] remove $1"
}

function install_arc_dependences {
    echo -n "[...] installing $1"
    
    if [[ $1 == "maven" ]]; then
    	tar -xzf $PROJECT_DIR/data/apache-maven*.tar.gz
    	NAME_PACKET=$(ls | grep apache-maven)
    	NAME_COMMAND="mvn"
    elif [[ $1 == "gradle" ]]; then
    	echo $(ls data)
    	unzip -q $PROJECT_DIR/data/gradle*.zip
    	NAME_PACKET=$(ls | grep gradle-)
    	NAME_COMMAND="gradle"
    fi
     
    mv $NAME_PACKET /opt/ 
    #export M2_HOME=/opt/$NAME_MVN
    #export GRADLE_HOME=/opt/$NAME_GRADLE
    
    ln -s /opt/$NAME_PACKET/bin/$NAME_COMMAND /usr/bin/$NAME_COMMAND
    echo -e "\\r[ $CHECK_MARK ] installing $1"
}

check_root
install_dependencies
remove_arc_dependences maven
remove_arc_dependences gradle
install_arc_dependences maven
install_arc_dependences gradle
