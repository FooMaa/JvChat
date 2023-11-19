#!/bin/bash
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/run-jvchat.log"
PROFILE=""
BUILDER=""
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_user {
    USER=$(whoami)
    if [ "$USER" == root ]; then 
        echo -e "\\rRun this script with user privileges"
        exit 1
    fi
}

function run {
    echo -n "[...] running"
    pushd $PROJECT_DIR >> $LOG_FILE 2>&1
    if [[ $BUILDER == "maven" ]]; then 
    	if [[ $PROFILE == "tests" ]]; then
            mvn test -Ptests
        else
            mvn exec:java -P$PROFILE >> $LOG_FILE 2>&1
        fi
    elif [[ $BUILDER == "gradle" ]]; then
    	if [[ $PROFILE == "tests" ]]; then
            gradle test -Ptests
        else
            gradle run -P$PROFILE >> $LOG_FILE 2>&1
        fi
    fi
    echo -e "\\r[ $CHECK_MARK ] running"
}

function build {
    echo -n "[...] building"
    if [[ $BUILDER == "maven" ]]; then
    	bash $PROJECT_DIR"scripts/build/build.sh" -m >> $LOG_FILE 2>&1
    elif [[ $BUILDER == "gradle" ]]; then 
    	bash $PROJECT_DIR"scripts/build/build.sh" -g >> $LOG_FILE 2>&1
    fi
    
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
    -g	    use gradle		    build with gradle 	( OPTIONAL ) Example $0 -g
    -m      use maven		    build use maven 	( OPTIONAL ) Example $0 -m
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
    if [[ $PROFILE != "tests" && $PROFILE != "users" && $PROFILE != "servers" ]]; then
        echo "Give profile param to script"
        usage
        exit 1
    fi

    if [[ $BUILDER != "maven" && $BUILDER != "gradle" ]]; then
       echo "Give builder param to script"
       usage
       exit 1
    fi
}

check_has_param $1

while [ -n "$1" ]; do
    case "$1" in
        -t ) PROFILE="tests" ;;
        -u ) PROFILE="users" ;;
        -m ) BUILDER="maven" ;;
        -g ) BUILDER="gradle" ;;
        -s ) PROFILE="servers"; shift ;;
        -h ) usage; exit 1;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac 
    shift
done

check_user
check_set_param
build
run
