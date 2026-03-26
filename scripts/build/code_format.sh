#!/bin/bash
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
LOG_FILE="/tmp/code-check-format-jvchat.log"
BUILDER=""
APPLY=false
CHECK_MARK="\033[0;32m\xE2\x9c\x94\033[0m"
CROSS_MARK="\033[0;31m\xE2\x9c\x97\033[0m"

function check_user {
    USER=$(whoami)
    if [ "$USER" == root ]; then
        echo "Run this script with user privileges"
        exit 1
    fi
}

function check_code {
    ACTION="check"
    if [[ $APPLY == true ]]; then
        ACTION="apply"
    fi

    echo -n "[...] $ACTION code ($BUILDER)"

    pushd $PROJECT_DIR >> $LOG_FILE 2>&1

    if [[ $BUILDER == "maven" ]]; then
        if [[ $APPLY == true ]]; then
            mvn spotless:apply >> $LOG_FILE 2>&1
        else
            mvn spotless:check >> $LOG_FILE 2>&1
        fi
    elif [[ $BUILDER == "gradle" ]]; then
        if [[ $APPLY == true ]]; then
            gradle spotlessApply >> $LOG_FILE 2>&1
        else
            gradle spotlessCheck >> $LOG_FILE 2>&1
        fi
    fi

    EXIT_CODE=$?
    if [[ $EXIT_CODE -ne 0 ]]; then
        echo -e "\\r[ $CROSS_MARK ] $ACTION code ($BUILDER)"
        tail -10 "$LOG_FILE"
        exit 1
    fi

    echo -e "\\r[ $CHECK_MARK ] $ACTION code ($BUILDER)"
}

function usage {
    cat <<EOF
    Usage: $0 [options]
    -h      help menu               to see this help (OPTIONAL) Example $0 -h
    -g	    use gradle              check code with gradle 	(REQUIRED) Example $0 -g
    -m      use maven               check code with maven 	(REQUIRED) Example $0 -m
    -a      apply format            apply spotless formatter  (OPTIONAL) Example $0 -m -a
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
        -m ) if [[ $BUILDER != "" ]]; then echo -e "\\rGive 1 builder"; usage; exit 1; else BUILDER="maven"; fi ;;
        -g ) if [[ $BUILDER != "" ]]; then echo -e "\\rGive 1 builder"; usage; exit 1; else BUILDER="gradle"; fi ;;
        -a ) APPLY=true ;;
        -h ) usage; exit 1;;
        -- ) usage; exit 1;;
        * ) usage; exit 1 ;;
    esac
    shift
done

check_set_param
check_code