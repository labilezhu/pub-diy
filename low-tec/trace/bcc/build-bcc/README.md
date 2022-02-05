# pub-diy
pub-diy


## Ubuntu

```bash
# mkdir -p ~/$EID/.docker
# export DOCKER_CONFIG=/root/$EID/.docker
# docker login 

docker run --init --log-driver none --privileged -d --name cndt-bcc-ub -h cndt-bcc-ub \
    --pid host \
    --net host \
    -v /etc/localtime:/etc/localtime:ro \
    -v /sys:/sys:rw \
    -v /usr/src:/usr/src:rw \
    -v /lib/modules:/lib/modules:rw \
    -v /var/run/docker.sock:/var/run/docker.sock:rw \
    `#bind the executeable file as the same path in container:` \
    -v /home/labile:/home/labile:rw \
    cndt-bcc-ub:latest \
    /bin/sleep 365d

```

#### run on container host

```bash

sudo apt-get install linux-headers-$(uname -r)
```

#### docker exec
```bash
docker exec -it cndt-bcc-ub bash

# test
python3 /usr/share/bcc/tools/tcplife
```

## Example

```bash
/home/labile/envoy.mybuild -c /home/labile/bpf-poc/envoy/standalone-envoy/envoy-demo.yaml

```



