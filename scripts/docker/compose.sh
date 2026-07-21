#!/bin/bash

ROOT_DIR="$(realpath "$(dirname "$0")/../..")"
COMPOSE_FILE="$ROOT_DIR/docker/docker-compose.yml"

BUILD=false
RUN=false
DOWN=false
CLEAN=false

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

    -b      build containers
    -r      run containers
    -a      build and run
    -d      stop containers
    -c      stop containers and remove volumes
    -h      help
EOF
}

while [ -n "$1" ]; do
    case "$1" in
        -b ) BUILD=true ;;
        -r ) RUN=true ;;
        -a ) BUILD=true; RUN=true ;;
        -d ) DOWN=true ;;
        -c ) CLEAN=true ;;
        -h ) usage; exit 0 ;;
        -- ) usage; exit 1 ;;
        * ) usage; exit 1 ;;
    esac
    shift
done

if [[ $BUILD == true && $RUN == false ]]; then
    docker compose -f "$COMPOSE_FILE" build
fi

if [[ $BUILD == true && $RUN == true ]]; then
    docker compose -f "$COMPOSE_FILE" up --build
fi

if [[ $BUILD == false && $RUN == true ]]; then
    docker compose -f "$COMPOSE_FILE" up
fi

if [[ $DOWN == true ]]; then
    docker compose -f "$COMPOSE_FILE" down
fi

if [[ $CLEAN == true ]]; then
    docker compose -f "$COMPOSE_FILE" down -v
fi
