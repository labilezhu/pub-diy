## Command 
```
python3 ./offwaketime -p $ENVOY_PID
```

## Preempted

### involuntary preempted on cpu thread
```log
    waker:            0
    --               --
    b'finish_task_switch'
    b'schedule'
    b'exit_to_usermode_loop'
    b'prepare_exit_to_usermode'
    b'swapgs_restore_regs_and_return_to_usermode'
    b'Envoy::Http::ActiveStreamFilterBase::clearRouteCache()'
    b'proxy_wasm::exports::remove_header_map_value(void*, proxy_wasm::Word, proxy_wasm::Word, proxy_wasm::Word)'
    b'proxy_wasm::null_plugin::MetadataExchange::Plugin::PluginContext::onRequestHeaders(unsigned int, bool)'
    b'std::__1::__function::__func<proxy_wasm::NullPlugin::getFunction(std::__1::basic_string_view<char, std::__1::char_traits<char> >, std::__1::function<proxy_wasm::Word (proxy_wasm::ContextBase*, proxy_wasm::Word, proxy_wasm::Word, proxy_wasm::Word)>*)::$_25, std::__1::allocator<proxy_wasm::NullPlugin::getFunction(std::__1::basic_string_view<char, std::__1::char_traits<char> >, std::__1::function<proxy_wasm::Word (proxy_wasm::ContextBase*, proxy_wasm::Word, proxy_wasm::Word, proxy_wasm::Word)>*)::$_25>, proxy_wasm::Word (proxy_wasm::ContextBase*, proxy_wasm::Word, proxy_wasm::Word, proxy_wasm::Word)>::operator()(proxy_wasm::ContextBase*&&, proxy_wasm::Word&&, proxy_wasm::Word&&, proxy_wasm::Word&&)'
    b'proxy_wasm::ContextBase::onRequestHeaders(unsigned int, bool)'
    b'virtual thunk to Envoy::Extensions::Common::Wasm::Context::decodeHeaders(Envoy::Http::RequestHeaderMap&, bool)'
    b'Envoy::Http::FilterManager::decodeHeaders(Envoy::Http::ActiveStreamDecoderFilter*, Envoy::Http::RequestHeaderMap&, bool)'
    b'Envoy::Http::ConnectionManagerImpl::ActiveStream::decodeHeaders(std::__1::unique_ptr<Envoy::Http::RequestHeaderMap, std::__1::default_delete<Envoy::Http::RequestHeaderMap> >&&, bool)'
    b'Envoy::Http::Http1::ServerConnectionImpl::onMessageCompleteBase()'
    b'Envoy::Http::Http1::ConnectionImpl::onMessageComplete()'
    b'Envoy::Http::Http1::LegacyHttpParserImpl::Impl::Impl(http_parser_type, void*)::{lambda(http_parser*)#3}::__invoke(http_parser*)'
    b'http_parser_execute'
    b'Envoy::Http::Http1::LegacyHttpParserImpl::execute(char const*, int)'
    b'Envoy::Http::Http1::ConnectionImpl::dispatchSlice(char const*, unsigned long)'
    b'Envoy::Http::Http1::ConnectionImpl::dispatch(Envoy::Buffer::Instance&)'
    b'virtual thunk to Envoy::Http::Http1::ConnectionImpl::dispatch(Envoy::Buffer::Instance&)'
    b'Envoy::Http::ConnectionManagerImpl::onData(Envoy::Buffer::Instance&, bool)'
    b'Envoy::Network::FilterManagerImpl::onContinueReading(Envoy::Network::FilterManagerImpl::ActiveReadFilter*, Envoy::Network::ReadBufferSource&)'
    b'Envoy::Network::ConnectionImpl::onReadReady()'
    b'Envoy::Network::ConnectionImpl::onFileEvent(unsigned int)'
    b'std::__1::__function::__func<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5, std::__1::allocator<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5>, void (unsigned int)>::operator()(unsigned int&&)'
    b'Envoy::Event::FileEventImpl::assignEvents(unsigned int, event_base*)::$_1::__invoke(int, short, void*)'
    b'event_process_active_single_queue'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_1 4450
```

### voluntary preempted when `mutex_lock`
```log
    waker:            0
    --               --
    b'finish_task_switch'
    b'preempt_schedule_common'
    b'_cond_resched'
    b'mutex_lock'
    b'ep_scan_ready_list.constprop.0'
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
        199
```

### voluntary preempted when `Envoy` syscalling
```log
    waker:            0
    --               --
    b'finish_task_switch'
    b'preempt_schedule_common'
    b'_cond_resched'
    b'aa_sk_perm'
    b'apparmor_socket_recvmsg'
    b'security_socket_recvmsg'
    b'sock_recvmsg'
    b'sock_read_iter'
    b'do_iter_readv_writev'
    b'do_iter_read'
    b'vfs_readv'
    b'do_readv'
    b'__x64_sys_readv'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'readv'
    b'Envoy::Network::IoSocketHandleImpl::readv(unsigned long, Envoy::Buffer::RawSlice*, unsigned long)'
    b'Envoy::Network::IoSocketHandleImpl::read(Envoy::Buffer::Instance&, absl::optional<unsigned long>)'
    b'Envoy::Network::RawBufferSocket::doRead(Envoy::Buffer::Instance&)'
    b'Envoy::Network::ConnectionImpl::onReadReady()'
    b'Envoy::Network::ConnectionImpl::onFileEvent(unsigned int)'
    b'std::__1::__function::__func<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5, std::__1::allocator<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5>, void (unsigned int)>::operator()(unsigned int&&)'
    b'Envoy::Event::FileEventImpl::assignEvents(unsigned int, event_base*)::$_1::__invoke(int, short, void*)'
    b'event_process_active_single_queue'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_1 4450
        235
```

## Unix Domain Socket

### `pilot-agent` 通过 `UNIX Domain Socket` 读取 `Envoy` 的 `XDS` 数据，读取后 wakeup 了 `Envoy`(可推测为 Envoy 补唤醒并应该收到 WriteReady 事件)

```log
    waker:           pilot-agent 4136
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
    b'__x64_sys_read'
    b'ksys_read'
    b'vfs_read'
    b'__vfs_read'
    b'new_sync_read'
    b'sock_read_iter'
    b'sock_recvmsg'
    b'unix_stream_recvmsg'
    b'unix_stream_read_generic'
    b'consume_skb'
    b'skb_release_all'
    b'skb_release_head_state'
    b'unix_destruct_scm'
    b'sock_wfree'
    b'unix_write_space'
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
    b'Envoy::Server::InstanceImpl::run()'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'main'
    b'__libc_start_main'
    target:          envoy 4182
        84
```


## Downstream Trigger TCP Event

### TCP CLOSE

#### `kubelet` active close TCP
```log
    waker:           kubelet 606
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
    b'__local_bh_enable_ip' <<<<<<<<<< Task from user process done, kernel try run SoftIRQ by the way.
    b'do_softirq.part.0'
    b'do_softirq_own_stack'
    b'__softirqentry_text_start'
    b'net_rx_action'
    b'process_backlog'
    b'__netif_receive_skb'
    b'__netif_receive_skb_one_core'
    b'ip_rcv' <<<<<<<<<<<<< Receive IP packet(TCP FIN) from `kubelet` to `Envoy`
    b'ip_rcv_finish'
    b'ip_local_deliver'
    b'ip_local_deliver_finish'
    b'ip_protocol_deliver_rcu'
    b'tcp_v4_rcv' <<<<<<<<<<< `Envoy` side downstream socket event(downstream TCP FIN)
    b'tcp_v4_do_rcv'
    b'tcp_rcv_established'
    b'tcp_data_queue'
    b'tcp_fin'
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
    b'do_epoll_wait' <<<<<<<<<<<<<< `Envoy` epoll waiting
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_1 4450
        182
```

### TCP Receive Data

#### `kubelet` send TCP data segment to `Envoy`, ReadReady

```log
    waker:           kubelet 169240
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
    b'vfs_write' <<<<<<<< `kubelet` write socket
    b'__vfs_write'
    b'new_sync_write'
    b'sock_write_iter'
    b'sock_sendmsg'
    b'inet_sendmsg'
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
    b'ip_finish_output2' <<<<<<< ip level sent done
    b'__local_bh_enable_ip' <<<<<<<<<< Task from user process done, kernel try run SoftIRQ by the way.
    b'do_softirq.part.0'
    b'do_softirq_own_stack'
    b'__softirqentry_text_start'
    b'net_rx_action'
    b'process_backlog'
    b'__netif_receive_skb'
    b'__netif_receive_skb_one_core'
    b'ip_rcv' <<<<<<<<<<<<< Receive IP packet(TCP data) from `kubelet` to `Envoy`
    b'ip_rcv_finish'
    b'ip_local_deliver'
    b'ip_local_deliver_finish'
    b'ip_protocol_deliver_rcu'
    b'tcp_v4_rcv' <<<<<<<<<<< `Envoy` side downstream socket event(downstream TCP data segment)
    b'tcp_v4_do_rcv'
    b'tcp_rcv_established'
    b'tcp_data_queue' <<<<<<<<<<< `Envoy` side downstream socket TCP segment enqueue to backlog
    b'tcp_data_ready'
    b'sock_def_readable' <<<<<<<<<<< `Envoy` side downstream socket raise ReadReady event
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
    b'do_epoll_wait' <<<<<<<<<<<<<< `Envoy` epoll waiting
    b'__x64_sys_epoll_wait'
    b'do_syscall_64'
    b'entry_SYSCALL_64_after_hwframe'
    b'epoll_wait'
    b'event_base_loop'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          wrk:worker_0 4449
        197
```

#### `ksoftirqd` thread receive TCP data segment to `Envoy`, ReadReady
```log
    waker:           ksoftirqd/1 18
    b'ret_from_fork'
    b'kthread'
    b'smpboot_thread_fn'
    b'run_ksoftirqd'
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
    b'sock_def_readable' <-----https://elixir.bootlin.com/linux/v5.4/source/net/core/sock.c#L2791
    b'__wake_up_sync_key'
    b'__wake_up_common_lock'
    b'__wake_up_common'
    b'ep_poll_callback' <----https://elixir.bootlin.com/linux/v5.4/source/fs/eventpoll.c#L1207
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
    target:          wrk:worker_1 4450
        2066849
```


## Upstream Event

### DNS(UDP) non-blocking
```log
    waker:           coredns 83776
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
    b'__x64_sys_sendmsg'
    b'__sys_sendmsg'
    b'___sys_sendmsg'
    b'____sys_sendmsg'
    b'sock_sendmsg'
    b'inet6_sendmsg'
    b'udpv6_sendmsg'
    b'udp_sendmsg'
    b'udp_send_skb.isra.0'
    b'ip_send_skb'
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
    b'udp_rcv'
    b'__udp4_lib_rcv'
    b'udp_unicast_rcv_skb.isra.0'
    b'udp_queue_rcv_skb'
    b'udp_queue_rcv_one_skb'
    b'__udp_enqueue_schedule_skb'
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
    b'Envoy::Server::InstanceImpl::run()'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'main'
    b'__libc_start_main'
    target:          envoy 4182
        344
```




## Envoy internal thread interaction


### DispatcherImpl::runPostCallback()
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
        248
```

### Thread local

```log

    waker:           wrk:worker_1 4450
    b'start_thread'
    b'Envoy::Thread::ThreadImplPosix::ThreadImplPosix(std::__1::function<void ()>, absl::optional<Envoy::Thread::Options> const&)::{lambda(void*)#1}::__invoke(void*)'
    b'Envoy::Server::WorkerImpl::threadRoutine(Envoy::Server::GuardDog&, std::__1::function<void ()> const&)'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'Envoy::Event::FileEventImpl::assignEvents(unsigned int, event_base*)::$_1::__invoke(int, short, void*)'
    b'std::__1::__function::__func<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5, std::__1::allocator<Envoy::Event::DispatcherImpl::createFileEvent(int, std::__1::function<void (unsigned int)>, Envoy::Event::FileTriggerType, unsigned int)::$_5>, void (unsigned int)>::operator()(unsigned int&&)'
    b'Envoy::Network::ConnectionImpl::onFileEvent(unsigned int)'
    b'Envoy::Network::ConnectionImpl::onReadReady()'
    b'Envoy::Network::FilterManagerImpl::onContinueReading(Envoy::Network::FilterManagerImpl::ActiveReadFilter*, Envoy::Network::ReadBufferSource&)'
    b'Envoy::Http::ConnectionManagerImpl::onData(Envoy::Buffer::Instance&, bool)'
    b'virtual thunk to Envoy::Http::Http1::ConnectionImpl::dispatch(Envoy::Buffer::Instance&)'
    b'Envoy::Http::Http1::ConnectionImpl::dispatch(Envoy::Buffer::Instance&)'
    b'Envoy::Http::Http1::ConnectionImpl::dispatchSlice(char const*, unsigned long)'
    b'Envoy::Http::Http1::LegacyHttpParserImpl::execute(char const*, int)'
    b'http_parser_execute'
    b'Envoy::Http::Http1::LegacyHttpParserImpl::Impl::Impl(http_parser_type, void*)::{lambda(http_parser*)#3}::__invoke(http_parser*)'
    b'Envoy::Http::Http1::ConnectionImpl::onMessageComplete()'
    b'Envoy::Http::Http1::ServerConnectionImpl::onMessageCompleteBase()'
    b'Envoy::Http::ConnectionManagerImpl::ActiveStream::decodeHeaders(std::__1::unique_ptr<Envoy::Http::RequestHeaderMap, std::__1::default_delete<Envoy::Http::RequestHeaderMap> >&&, bool)'
    b'Envoy::Http::FilterManager::decodeHeaders(Envoy::Http::ActiveStreamDecoderFilter*, Envoy::Http::RequestHeaderMap&, bool)'
    b'Envoy::Router::Filter::decodeHeaders(Envoy::Http::RequestHeaderMap&, bool)'
    b'Envoy::Router::Filter::createConnPool(Envoy::Upstream::ThreadLocalCluster&)'
    b'Envoy::Extensions::Upstreams::Http::Generic::GenericGenericConnPoolFactory::createGenericConnPool(Envoy::Upstream::ThreadLocalCluster&, bool, Envoy::Router::RouteEntry const&, absl::optional<Envoy::Http::Protocol>, Envoy::Upstream::LoadBalancerContext*) const'
    b'Envoy::Extensions::Upstreams::Http::Http::HttpConnPool::HttpConnPool(Envoy::Upstream::ThreadLocalCluster&, bool, Envoy::Router::RouteEntry const&, absl::optional<Envoy::Http::Protocol>, Envoy::Upstream::LoadBalancerContext*)'
    b'Envoy::Upstream::ClusterManagerImpl::ThreadLocalClusterManagerImpl::ClusterEntry::httpConnPool(Envoy::Upstream::ResourcePriority, absl::optional<Envoy::Http::Protocol>, Envoy::Upstream::LoadBalancerContext*)'
    b'Envoy::Upstream::ClusterManagerImpl::ThreadLocalClusterManagerImpl::ClusterEntry::connPool(Envoy::Upstream::ResourcePriority, absl::optional<Envoy::Http::Protocol>, Envoy::Upstream::LoadBalancerContext*, bool)'
    b'Envoy::Upstream::OriginalDstCluster::LoadBalancer::chooseHost(Envoy::Upstream::LoadBalancerContext*)'
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
        12781
```

### runOnAllThreads()

```log
    waker:           envoy 4182
    b'__libc_start_main'
    b'main'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::Server::InstanceImpl::run()'
    b'event_base_loop'
    b'event_process_active_single_queue'
    b'Envoy::Event::DispatcherImpl::runPostCallbacks()'
    b'std::__1::__function::__func<Envoy::Upstream::OriginalDstCluster::LoadBalancer::chooseHost(Envoy::Upstream::LoadBalancerContext*)::$_0, std::__1::allocator<Envoy::Upstream::OriginalDstCluster::LoadBalancer::chooseHost(Envoy::Upstream::LoadBalancerContext*)::$_0>, void ()>::operator()()'
    b'Envoy::Upstream::OriginalDstCluster::addHost(std::__1::shared_ptr<Envoy::Upstream::Host>&)'
    b'Envoy::Upstream::PrioritySetImpl::updateHosts(unsigned int, Envoy::Upstream::PrioritySet::UpdateHostsParams&&, std::__1::shared_ptr<std::__1::vector<unsigned int, std::__1::allocator<unsigned int> > const>, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, absl::optional<unsigned int>)'
    b'Envoy::Upstream::HostSetImpl::runUpdateCallbacks(std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&)'
    b'Envoy::Upstream::PrioritySetImpl::runReferenceUpdateCallbacks(unsigned int, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&)'
    b'std::__1::__function::__func<Envoy::Upstream::ClusterManagerImpl::onClusterInit(Envoy::Upstream::ClusterManagerCluster&)::$_8, std::__1::allocator<Envoy::Upstream::ClusterManagerImpl::onClusterInit(Envoy::Upstream::ClusterManagerCluster&)::$_8>, void (unsigned int, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&)>::operator()(unsigned int&&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&, std::__1::vector<std::__1::shared_ptr<Envoy::Upstream::Host>, std::__1::allocator<std::__1::shared_ptr<Envoy::Upstream::Host> > > const&)'
    b'Envoy::Upstream::ClusterManagerImpl::postThreadLocalClusterUpdate(Envoy::Upstream::ClusterManagerCluster&, Envoy::Upstream::ClusterManagerImpl::ThreadLocalClusterUpdateParams&&)'
    b'Envoy::ThreadLocal::TypedSlot<Envoy::Upstream::ClusterManagerImpl::ThreadLocalClusterManagerImpl>::runOnAllThreads(std::__1::function<void (Envoy::OptRef<Envoy::Upstream::ClusterManagerImpl::ThreadLocalClusterManagerImpl>)> const&)'
    b'Envoy::ThreadLocal::InstanceImpl::SlotImpl::runOnAllThreads(std::__1::function<void (std::__1::shared_ptr<Envoy::ThreadLocal::ThreadLocalObject>)> const&)'
    b'Envoy::ThreadLocal::InstanceImpl::runOnAllThreads(std::__1::function<void ()>)'
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
        12940
```

## IRQ Trigger


```
    waker:           swapper/1 0
    b'secondary_startup_64'
    b'start_secondary'
    b'cpu_startup_entry'
    b'do_idle'
    b'default_idle_call'
    b'arch_cpu_idle'
    b'native_safe_halt'
    b'ret_from_intr'
    b'do_IRQ'
    b'irq_exit'
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
    b'udp_rcv'
    b'__udp4_lib_rcv'
    b'udp_unicast_rcv_skb.isra.0'
    b'udp_queue_rcv_skb'
    b'udp_queue_rcv_one_skb'
    b'__udp_enqueue_schedule_skb'
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
    b'Envoy::Server::InstanceImpl::run()'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'main'
    b'__libc_start_main'
    target:          envoy 4182
        3090
```

### APIC Timer IRQ Trigger hrtimer_wakeup epoll timeout

```log
    waker:           fortio 4094
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
    b'Envoy::Server::InstanceImpl::run()'
    b'Envoy::MainCommonBase::run()'
    b'Envoy::MainCommon::main(int, char**, std::__1::function<void (Envoy::Server::Instance&)>)'
    b'main'
    b'__libc_start_main'
    target:          envoy 4182
        4076
```


## Lock wakeup

### futex unlocked wake
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
        33058275
```


### futex timeout wake
```log
    waker:           swapper/1 0
    b'secondary_startup_64'
    b'start_secondary'
    b'cpu_startup_entry'
    b'do_idle'
    b'default_idle_call'
    b'arch_cpu_idle'
    b'native_safe_halt'
    b'apic_timer_interrupt'
    b'smp_apic_timer_interrupt'
    b'hrtimer_interrupt'
    b'__hrtimer_run_queues'
    b'hrtimer_wakeup'
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
    b'gpr_cv_wait'
    b'timer_thread(void*)'
    b'grpc_core::(anonymous namespace)::ThreadInternalsPosix::ThreadInternalsPosix(char const*, void (*)(void*), void*, bool*, grpc_core::Thread::Options const&)::{lambda(void*)#1}::__invoke(void*)'
    b'start_thread'
    target:          grpc_global_tim 4212
        28011271
```

## SoftIRQ timer wakeup
```log

```

