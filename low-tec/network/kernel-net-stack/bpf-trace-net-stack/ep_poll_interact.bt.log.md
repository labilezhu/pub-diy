
环境说明：

```
downstream_pod IP: 172.30.207.163

target_pod IP: 172.21.206.207
target_sidecar(Envoy) Inbound listen port: 0.0.0.0:15006
target_sidecar(Envoy) 进程 PID: 4182
target_sidecar(Envoy) 主线程TID: 4182
target_sidecar(Envoy) 主线程名: envoy
target_sidecar(Envoy) 工作线程0 TID: 4449
target_sidecar(Envoy) 工作线程0 名: wrk:worker_0
target_sidecar(Envoy) 工作线程1 TID: 4450
target_sidecar(Envoy) 工作线程1 名: wrk:worker_1

target_app endpoint: 172.21.206.207:8080 / 127.0.0.1:8080
```

值得注意的是，`wrk:worker_0` 和 `wrk:worker_1` 同时监听在同一个 socket(0.0.0.0:15006) 上，但使用了不同的 fd:
* wrk:worker_0: fd=36
* wrk:worker_1: fd=40



##### Inbound Listener(port:15006, fd=36) 新连接建立事件唤醒

1. 线程 `swapper/0` 在 SoftIRQ 中处理网络包，推到 TCP 层
2. TCP 层解释包后，完成了 TCP 三次握手，建立了连接，触发到 fd=36, 0.0.0.0:15006 的连接建立事件
3. 调用 `try_to_wake_up` 函数，唤醒了 `worker_0` 和 `worker_1`。是的，从跟踪结果看，

```log


***waker: elapsed=20929      tid=0,comm=swapper/0: ep_poll_callback: fd=40,        0.0.0.0:15006         0.0.0.0:0      LISTEN socket=0xffff9f5e53bfbd40
try_to_wake_up: wakeupedPID=4182, wakeupedTID=4450, wakeupedTIDComm=wrk:worker_1

***waker: elapsed=20929      tid=0,comm=swapper/0: ep_poll_callback: fd=36,        0.0.0.0:15006         0.0.0.0:0      LISTEN socket=0xffff9f5e53bfbd40
try_to_wake_up: wakeupedPID=4182, wakeupedTID=4449, wakeupedTIDComm=wrk:worker_0
--------
***sleeper-wakeup: elapsed=20929     , pid=4182, tid=4449,comm=wrk:worker_0, event_count=1
***** elapsed=20929     : tid=4449,comm=wrk:worker_0: BEGIN:EventFired:FileEventImpl::assignEvents::eventCallback()
FileEventImpl*=0x55af6486fea0, fd=36, events=0x2
libevent: EV_READ
inet_csk_accept: 172.30.207.163                          38590 172.21.206.207                          15006 sys_exit_accept4 fd=42



```