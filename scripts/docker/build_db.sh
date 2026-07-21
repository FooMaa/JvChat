#!/bin/bash

ROOT_DIR="$(realpath "$(dirname "$0")/../..")"

BUILD=false
RUN=false

function check_user {
    USER=$(whoami)
    if [ "$USER" == root ]; then
        echo "Run this script with user privileges"
        exit 1
    fi
}

function usage {
    cat <<EOF
    Usage: $0 [options]

    -b      build database image
    -r      run database container
    -a      build and run
    -h      help
EOF
}

while [ -n "$1" ]; do
    case "$1" in
        -b ) BUILD=true ;;
        -r ) RUN=true ;;
        -a ) BUILD=true; RUN=true ;;
        -h ) usage; exit 0 ;;
        -- ) usage; exit 1 ;;
        * ) usage; exit 1 ;;
    esac
    shift
done

if [[ $BUILD == true ]]; then
    docker build \
        -f "$ROOT_DIR/docker/db/Dockerfile" \
        -t jvchat-db \
        "$ROOT_DIR"
fi

if [[ $RUN == true ]]; then
    docker run \
        --name jvchat-db \
        -p 5432:5432 \
        -v jvchat-pg:/var/lib/postgresql \
        jvchat-db
fi
