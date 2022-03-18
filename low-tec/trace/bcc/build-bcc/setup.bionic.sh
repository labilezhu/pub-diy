#!/usr/bin/env bash
set -e

## apt install

apt update
apt-get -y install bison build-essential cmake flex git libedit-dev \
  libllvm6.0 llvm-6.0-dev libclang-6.0-dev python zlib1g-dev libelf-dev libfl-dev python3-distutils

apt install -y vim curl iproute2 netcat tcpdump

apt-get -y install gettext-base

## 
mkdir /opensource
pushd /opensource
git clone https://github.com/brendangregg/FlameGraph.git
popd

## make

update-alternatives --install /usr/bin/python python /usr/bin/python2.7 1
update-alternatives --install /usr/bin/python python /usr/bin/python3.6 2

cd /opensource

git clone https://github.com/iovisor/bcc.git

pushd bcc
git checkout tags/v0.24.0 -b v0.24.0
popd

mkdir bcc/build; cd bcc/build
cmake ..
make
make install
cmake -DPYTHON_CMD=python3 .. # build python3 binding
pushd src/python/
make
make install
make clean
popd
make clean



