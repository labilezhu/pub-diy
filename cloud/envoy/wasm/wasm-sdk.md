

```bash
git clone https://github.com/proxy-wasm/proxy-wasm-cpp-sdk.git
cd proxy-wasm-cpp-sdk
docker build -t wasmsdk:v2 -f Dockerfile-sdk .


export PID=$(ssh labile@192.168.122.55 -- pgrep -f '"docker build"')
timeout 3h ssh -o serveraliveinterval=60 labile@192.168.122.55 -- tail --pid="$PID" -f /dev/null ; sudo systemctl suspend && ping 192.168.1.1


```