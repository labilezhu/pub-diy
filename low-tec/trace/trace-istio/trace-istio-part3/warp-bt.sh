#!/bin/bash

set -xe

envsubst '$(env|egrep "^[A-Z]")' < $SCRIPT_HOME/$BT  > /tmp/gen.bt

nl /tmp/gen.bt

bpftrace /tmp/gen.bt $@
