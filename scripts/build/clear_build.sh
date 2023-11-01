#!/bin/bash
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/JvChat.+//g')"$PROJECT_NAME )
USER=$(whoami)
    if [ "$USER" != root ]; then 
        echo -e "\\rRun this script with root privileges"
        exit 1
    fi
bash $PROJECT_DIR"JvChat/scripts/db/make_default_db.sh"
bash $PROJECT_DIR"JvChat/scripts/build/run.sh"

