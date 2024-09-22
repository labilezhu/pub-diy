#!/bin/bash

set -e

IMPORT_ENV=$(grep 'IMPORT-ENV:' $SCRIPT_HOME/$BT | awk '{print $2}')
# echo "IMPORT_ENV=$IMPORT_ENV"
# echo "ENVOY_PID=$ENVOY_PID"

envsubst $IMPORT_ENV < $SCRIPT_HOME/$BT  > /tmp/gen.bt

nl -ba /tmp/gen.bt

bpftrace /tmp/gen.bt $@