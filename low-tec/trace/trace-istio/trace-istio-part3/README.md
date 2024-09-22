
### Send an HTTPS request to access the `httpbin` service through HTTPS:

- from other pod(with istio-proxy):

```bash
k -n idm-mark exec  fortio-server-l2 -c istio-proxy -- curl -v "http://fortio-server:8080/fortio/"
```



- Node port simple TLS:

```bash
export INGRESS_HOST=192.168.122.55
export SECURE_INGRESS_PORT=31356

curl -v -HHost:fortio-server.idm-mark.com --resolve "fortio-server.idm-mark.com:$SECURE_INGRESS_PORT:$INGRESS_HOST" \
--cacert idm-mark.com.crt "https://fortio-server.idm-mark.com:$SECURE_INGRESS_PORT/fortio/"
```