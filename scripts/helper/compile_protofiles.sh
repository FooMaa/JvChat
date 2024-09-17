#!/bin/bash

PROJECT_NAME=JvChat
PROJECT_DIR=$( echo "$(realpath $0 | sed -r 's/scripts.+//g')" )
PROTO_PATH="$PROJECT_DIR/sources/lib/JvMessages/src/main/proto"

function create_proto_src {
    protoc $1 --java_out=$PROJECT_DIR/sources/lib/JvMessages/src/main/java --proto_path=$PROTO_PATH
}

FILES=$(find $PROTO_PATH -name "*.proto")
for FILE in $FILES; do
    echo $FILE
    create_proto_src $FILE
done
