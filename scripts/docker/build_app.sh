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
    -b      build application image
    -r      run application container
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
        -f "$ROOT_DIR/docker/app/Dockerfile" \
        -t jvchat-app \
        "$ROOT_DIR"
fi

if [[ $RUN == true ]]; then
    docker run --rm \
        --name jvchat-app \
        -p 4004:4004 \
        jvchat-app
fi
