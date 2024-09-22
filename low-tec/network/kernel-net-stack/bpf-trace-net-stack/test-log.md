


```bash
export PID=4182
export SCRIPT_HOME=`pwd`
export bpftrace_image=cndt-bcc-ub

docker run -it --rm --init  --privileged --name bpftrace -h bpftrace \
    --pid host \
    --net host \
    -e SCRIPT_HOME=$SCRIPT_HOME \
    -e PID=$PID \
    -e ENVOY_PID=$PID \
    -e BT=trace-envoy-filter-flow.bt \
    -v /etc/localtime:/etc/localtime:ro \
    -v /sys:/sys:rw \
    -v /usr/src:/usr/src:rw \
    -v /lib/modules:/lib/modules:ro \
    -v ${SCRIPT_HOME}:${SCRIPT_HOME}:rw \
    $bpftrace_image bash

cd /usr/share/bcc/tools   

python3 ./offwaketime -p $PID
```

### Wakeup by socket event

#### Socket Readable Event

##### `kubelet` connect wakeup envoy by socket Readable Event

```log
    waker:           kubelet 1172
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_connect' <<<<<<<<<<<<<<< kubelet connect Envoy
    b'__sys_connect'
    b'inet_stream_connect'
    b'release_sock'
    b'__release_sock'
    b'tcp_v4_do_rcv' <<<<<<<<<<<<<<<< `sk->sk_backlog_rcv` point to
    b'tcp_rcv_state_process' <<<<<<<<<<<<<<< `sk->sk_state` == TCP_SYN_SENT
    b'tcp_rcv_synsent_state_process' <<<<<< read `SYN/ACK` from peer at TCP backlog
    b'tcp_send_ack'  <<<<<<<<<<< send `ACK`, finish 3 handshake
    b'__tcp_send_ack.part.0'
    b'__tcp_transmit_skb'
    b'ip_queue_xmit'
    b'__ip_queue_xmit'
    b'ip_local_out'
    b'ip_output'
    b'ip_finish_output'
    b'__ip_finish_output'
    b'ip_finish_output2'
    b'__local_bh_enable_ip' <<<<<<<<<< Task from user process done, kernel try run SoftIRQ by the way.
    b'do_softirq.part.0'
    b'do_softirq_own_stack'
    b'__softirqentry_text_start'
    b'net_rx_action'
    b'process_backlog'
    b'__netif_receive_skb'
    b'__netif_receive_skb_one_core'
    b'ip_rcv' <<<<<<<<<<<<< Receive IP packet(TCP SYNC) from `kubelet` to `Envoy`
    b'ip_rcv_finish'
    b'ip_local_deliver'
    b'ip_local_deliver_finish'
    b'ip_protocol_deliver_rcu'
    b'tcp_v4_rcv' <<<<<<<<<<< `Envoy` side listener socket sk->sk_state == TCP_LISTEN
    b'tcp_child_process'
    b'sock_def_readable'
    b'__wake_up_sync_key'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        201092
```

##### `pilot-agent` wakeup envoy by socket Readable Event

```log
    waker:           pilot-agent 4405
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_write'
    b'ksys_write'
    b'vfs_write'
    b'__vfs_write'
    b'new_sync_write'
    b'sock_write_iter'
    b'sock_sendmsg'
    b'inet6_sendmsg'
    b'tcp_sendmsg'
    b'tcp_sendmsg_locked'
    b'tcp_push'
    b'__tcp_push_pending_frames'
    b'tcp_write_xmit'
    b'__tcp_transmit_skb'
    b'ip_queue_xmit'
    b'__ip_queue_xmit'
    b'ip_local_out'
    b'ip_output'
    b'ip_finish_output'
    b'__ip_finish_output'
    b'ip_finish_output2'
    b'__local_bh_enable_ip'
    b'do_softirq.part.0'
    b'do_softirq_own_stack'
    b'__softirqentry_text_start'
    b'net_rx_action'
    b'process_backlog'
    b'__netif_receive_skb'
    b'__netif_receive_skb_one_core'
    b'ip_rcv'
    b'ip_rcv_finish'
    b'ip_local_deliver'
    b'ip_local_deliver_finish'
    b'ip_protocol_deliver_rcu'
    b'tcp_v4_rcv'
    b'tcp_v4_do_rcv'
    b'tcp_rcv_established'
    b'tcp_data_ready'
    b'sock_def_readable'
    b'__wake_up_sync_key'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        309
```

##### `kubelet` wakeup envoy by socket Close Event
```log
waker:           kubelet 1172
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'[unknown]'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'exit_to_usermode_loop'
    b'task_work_run'
    b'____fput'
    b'__fput'
    b'sock_close'
    b'__sock_release'
    b'inet_release'
    b'tcp_close'
    b'tcp_send_fin'
    b'__tcp_push_pending_frames'
    b'tcp_write_xmit'
    b'__tcp_transmit_skb'
    b'ip_queue_xmit'
    b'__ip_queue_xmit'
    b'ip_local_out'
    b'ip_output'
    b'ip_finish_output'
    b'__ip_finish_output'
    b'ip_finish_output2'
    b'__local_bh_enable_ip'
    b'do_softirq.part.0'
    b'do_softirq_own_stack'
    b'__softirqentry_text_start'
    b'net_rx_action'
    b'process_backlog'
    b'__netif_receive_skb'
    b'__netif_receive_skb_one_core'
    b'ip_rcv'
    b'ip_rcv_finish'
    b'ip_local_deliver'
    b'ip_local_deliver_finish'
    b'ip_protocol_deliver_rcu'
    b'tcp_v4_rcv'
    b'tcp_v4_do_rcv'
    b'tcp_rcv_established'
    b'tcp_data_queue'
    b'tcp_fin' <<<<<<<<<<<<<<<<<<<<< TCP CLOSE
    b'sock_def_wakeup'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        486
```

### Wakeup thread preempted when writing socket
```log
    waker:            0
    --               --
    b'finish_task_switch'
    b'preempt_schedule_common'
    b'_cond_resched'
    b'__release_sock'
    b'release_sock'
    b'tcp_sendmsg'
    b'inet_sendmsg'
    b'sock_sendmsg'
    b'sock_write_iter'
    b'do_iter_readv_writev'
    b'do_iter_write'
    b'vfs_writev'
    b'do_writev'
    b'__x64_sys_writev'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'writev'
    b'Envoy::Network::IoSocketHandleImpl::writev(Envoy::Buffer::RawSlice const*, unsigned long)'
    b'Envoy::Network::IoSocketHandleImpl::write(Envoy::Buffer::Instance&)'
    b'Envoy::Network::RawBufferSocket::doWrite(Envoy::Buffer::Instance&, bool)'
    b'Envoy::Network::ConnectionImpl::onWriteReady()'
    b'Envoy::Network::ConnectionImpl::onFileEvent(unsigned int)'
    b'std::__1::__function::__func<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5, std::__1::allocator<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5>, void (unsigned int)>::operator()(unsigned int&&)'
    b'Envoy::Event::FileEventImpl::assignEvents(unsigned int, event_base*)::$_1::__invoke(int, short, void*)'
    b'event_process_active_single_queue'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        206
```




### wakeup by `hrtimer_interrupt`

```log
    waker:           dog:main_thread 4226
    [Missed User Stack] -17
    b'apic_timer_interrupt'
    b'smp_apic_timer_interrupt'
    b'hrtimer_interrupt'
    b'__hrtimer_run_queues'
    b'hrtimer_wakeup'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'std::__1::__function::__func<Envoy::Server::GuardDogImpl::start(Envoy::Api::Api&)::$_4, std::__1::allocator<Envoy::Server::GuardDogImpl::start(Envoy::Api::Api&)::$_4>, void ()>::operator()()'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          dog:workers_gua 4227
        4072
```

```log
    waker:           calico-node 3307
    b'runtime.goexit'
    b'github.com/projectcalico/felix/calc.(*AsyncCalcGraph).loop'
    b'runtime.selectgo'
    b'runtime.mcall'
    b'runtime.park_m'
    b'runtime.schedule'
    b'runtime.findrunnable'
    b'runtime.checkTimers'
    b'runtime.runtimer'
    b'runtime.runOneTimer'
    b'time.sendTime'
    b'runtime.selectnbsend'
    b'apic_timer_interrupt'
    b'smp_apic_timer_interrupt'
    b'hrtimer_interrupt'
    b'__hrtimer_run_queues'
    b'hrtimer_wakeup'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_1 4450
        64299
```

#### wakeup by `hrtimer_interrupt` when `irq_exit`

```log
    waker:           swapper/1 0
    b'secondary_startup_64'
    b'start_secondary'
    b'cpu_startup_entry'
    b'do_idle'
    b'default_idle_call'
    b'arch_cpu_idle'
    b'native_safe_halt'
    b'reschedule_interrupt'
    b'smp_reschedule_interrupt'
    b'scheduler_ipi'
    b'irq_exit'
    b'__softirqentry_text_start'
    b'apic_timer_interrupt'
    b'smp_apic_timer_interrupt'
    b'hrtimer_interrupt'
    b'__hrtimer_run_queues'
    b'hrtimer_wakeup'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        4996
```


## Main thread & worker threads

###  `main` Thread Local sync to `worker`
```log
    waker:           envoy 4182
    b'__libc_start_main'
    b'main'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::Server::InstanceImpl::run()'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'Envoy::Server::InstanceImpl::flushStats()'
    b'Envoy::Stats::ThreadLocalStoreImpl::mergeHistograms(std::__1::function<void ()>)'
    b'Envoy::ThreadLocal::TypedSlot<Envoy::Stats::ThreadLocalStoreImpl::TlsCache>::runOnAllThreads(std::__1::function<void (Envoy::OptRef<Envoy::Stats::ThreadLocalStoreImpl::TlsCache>)> const&, std::__1::function<void ()> const&)'
    b'Envoy::ThreadLocal::InstanceImpl::SlotImpl::runOnAllThreads(std::__1::function<void (std::__1::shared_ptr<Envoy::ThreadLocal::ThreadLocalObject>)> const&, std::__1::function<void ()> const&)'
    b'Envoy::ThreadLocal::InstanceImpl::runOnAllThreads(std::__1::function<void ()>, std::__1::function<void ()>)'
    b'event_active'
    b'event_callback_activate_nolock_'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_write'
    b'ksys_write'
    b'vfs_write'
    b'__vfs_write'
    b'eventfd_write'
    b'__wake_up_locked_key'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        169609
```

### `worker` DispatcherImpl::runPostCallbacks() wakeup `main`

```log
    waker:           wrk:worker_1 4450
    b'start_thread'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'Envoy::Event::DispatcherImpl::runPostCallbacks()'
    b'std::__1::__function::__func<Envoy::ThreadLocal::InstanceImpl::runOnAllThreads(std::__1::function<void ()>, std::__1::function<void ()>)::$_6, std::__1::allocator<Envoy::ThreadLocal::InstanceImpl::runOnAllThreads(std::__1::function<void ()>, std::__1::function<void ()>)::$_6>, void ()>::destroy()'
    b'std::__1::__shared_ptr_pointer<std::__1::function<void ()>*, Envoy::ThreadLocal::InstanceImpl::runOnAllThreads(std::__1::function<void ()>, std::__1::function<void ()>)::$_5, std::__1::allocator<std::__1::function<void ()> > >::__on_zero_shared()'
    b'event_active'
    b'event_callback_activate_nolock_'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_write'
    b'ksys_write'
    b'vfs_write'
    b'__vfs_write'
    b'eventfd_write'
    b'__wake_up_locked_key'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::InstanceImpl::run()'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'main'
    b'__libc_start_main'
    target:          envoy 4182
        5767
```

### `main` Thread Local(Http::TlsCaching) sync to `worker` 


```log
    waker:           envoy 4182
    b'__libc_start_main'
    b'main'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::Server::InstanceImpl::run()'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'Envoy::Http::TlsCachingDateProviderImpl::onRefreshDate()'
    b'Envoy::ThreadLocal::InstanceImpl::SlotImpl::set(std::__1::function<std::__1::shared_ptr<Envoy::ThreadLocal::ThreadLocalObject> (Envoy::Event::Dispatcher&)>)'
    b'event_active'
    b'event_callback_activate_nolock_'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_write'
    b'ksys_write'
    b'vfs_write'
    b'__vfs_write'
    b'eventfd_write'
    b'__wake_up_locked_key'
    b'__wake_up_common'
    b'ep_poll_callback'
    b'__wake_up'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'autoremove_wake_function'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'schedule_hrtimeout_range_clock'
    b'schedule_hrtimeout_range'
    b'ep_poll'
    b'do_epoll_wait'
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        1286891
```

### AccessLog
```log
    waker:           envoy 4182
    b'__libc_start_main'
    b'main'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::Server::InstanceImpl::run()'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'std::__1::__function::__func<Envoy::AccessLog::AccessLogFileImpl::AccessLogFileImpl(std::__1::unique_ptr<Envoy::Filesystem::File, std::__1::default_delete<Envoy::Filesystem::File> >&&, Envoy::Event::Dispatcher&, Envoy::Thread::BasicLockable&, Envoy::AccessLogFileStats&, std::__1::chrono::duration<long long, std::__1::ratio<1l, 1000l> >, Envoy::Thread::ThreadFactory&)::$_0, std::__1::allocator<Envoy::AccessLog::AccessLogFileImpl::AccessLogFileImpl(std::__1::unique_ptr<Envoy::Filesystem::File, std::__1::default_delete<Envoy::Filesystem::File> >&&, Envoy::Event::Dispatcher&, Envoy::Thread::BasicLockable&, Envoy::AccessLogFileStats&, std::__1::chrono::duration<long long, std::__1::ratio<1l, 1000l> >, Envoy::Thread::ThreadFactory&)::$_0>, void ()>::operator()()'
    b'absl::CondVar::Signal()'
    b'absl::Mutex::Fer(absl::base_internal::PerThreadSynch*)'
    b'entry_SYSCALL_64_after_hwframe'
    b'do_syscall_64'
    b'__x64_sys_futex'
    b'do_futex'
    b'futex_wake'
    --               --
    b'finish_task_switch'
    b'schedule'
    b'futex_wait_queue_me'
    b'futex_wait'
    b'do_futex'
    b'__x64_sys_futex'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'syscall'
    b'AbslInternalPerThreadSemWait'
    b'absl::CondVar::WaitCommon(absl::Mutex*, absl::synchronization_internal::KernelTimeout)'
    b'Envoy::AccessLog::AccessLogFileImpl::flushThreadFunc()'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          AccessLogFlush 4478
        12024649
```