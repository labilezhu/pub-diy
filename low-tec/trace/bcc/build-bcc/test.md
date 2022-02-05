

```bash

docker run ubuntu:18.04

docker run --log-driver none --privileged -it --name ub-bcc \
    --pid host \
    --net host \
    -v /etc/localtime:/etc/localtime:ro \
    -v /sys:/sys:rw \
    -v /usr/src:/usr/src:rw \
    -v /lib/modules:/lib/modules:rw \
    -v /var/run/docker.sock:/var/run/docker.sock:rw \
    ubuntu:18.04

docker start ub-bcc

docker exec -it ub-bcc bash
```


```bash


# Ref. https://github.com/iovisor/bcc/blob/master/INSTALL.md
# For Bionic (18.04 LTS)
apt update
apt-get -y install bison build-essential cmake flex git libedit-dev \
  libllvm6.0 llvm-6.0-dev libclang-6.0-dev python zlib1g-dev libelf-dev libfl-dev python3-distutils
  

#apt-get install linux-headers-$(uname -r)
```


```bash
mkdir /opensource
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
popd
```

```
python3 /usr/share/bcc/tools/execsnoop

```



```bash
# run on container host
sudo apt-get install linux-headers-$(uname -r)
```


