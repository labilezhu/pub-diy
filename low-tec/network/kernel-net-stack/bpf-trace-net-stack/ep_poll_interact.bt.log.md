

##### Inbound Listener(port:15006, fd=36) 新连接建立事件唤醒

线程 `swapper/0` 在 SoftIRQ 中处理网络包，推到 TCP 本

```log


***waker: elapsed=20929      tid=0,comm=swapper/0: ep_poll_callback: fd=36,        0.0.0.0:15006         0.0.0.0:0      LISTEN socket=0xffff9f5e53bfbd40
try_to_wake_up: wakeupedPID=4182, wakeupedTID=4449, wakeupedTIDComm=wrk:worker_0
--------
***sleeper-wakeup: elapsed=20929     , pid=4182, tid=4449,comm=wrk:worker_0, event_count=1
***** elapsed=20929     : tid=4449,comm=wrk:worker_0: BEGIN:EventFired:FileEventImpl::assignEvents::eventCallback()
FileEventImpl*=0x55af6486fea0, fd=36, events=0x2
libevent: EV_READ
inet_csk_accept: 172.30.207.163                          38590 172.21.206.207                          15006 sys_exit_accept4 fd=42



```