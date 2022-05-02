
```bash


bpftrace -e '
#include <net/tcp_states.h>
#include <net/sock.h>
#include <linux/socket.h>
#include <linux/tcp.h>

BEGIN
{
	@tcp_states[1] = "ESTABLISHED";
	@tcp_states[2] = "SYN_SENT";
	@tcp_states[3] = "SYN_RECV";
	@tcp_states[4] = "FIN_WAIT1";
	@tcp_states[5] = "FIN_WAIT2";
	@tcp_states[6] = "TIME_WAIT";
	@tcp_states[7] = "CLOSE";
	@tcp_states[8] = "CLOSE_WAIT";
	@tcp_states[9] = "LAST_ACK";
	@tcp_states[10] = "LISTEN";
	@tcp_states[11] = "CLOSING";
	@tcp_states[12] = "NEW_SYN_RECV";
}

kprobe:tcp_set_state
{
	$sk = (struct sock *)arg0;
	$newstate = arg1;


    printf("newstate=%s\n%s\n", @tcp_states[$newstate], kstack());
}

END
{
	clear(@tcp_states);
}
'














```