
```bash

kubectl -n idm-mark delete pod fortio-server
kubectl -n idm-mark apply -f - <<EOF
apiVersion: v1
kind: Pod
metadata:
    name: fortio-server
    labels:
        app.kubernetes.io/name: fortio-server
        app: fortio-server
spec:
    restartPolicy: Always
    imagePullSecrets:
    - name: docker-registry-key
    containers:
    - name: main-app
      image: docker.io/fortio/fortio
      imagePullPolicy: Always
      command: ["/usr/bin/fortio"]
      args: ["server", "-M", "8070 http://fortio-server-l2:8080"]
      ports:
      - containerPort: 8080
        protocol: TCP
        name: http      
      - containerPort: 8070
        protocol: TCP
        name: http-m   
      - containerPort: 8079
        protocol: TCP
        name: grpc   
    nodeSelector:
      kubernetes.io/hostname: worknode5

---

apiVersion: v1
kind: Service
metadata:
  labels:
    app.kubernetes.io/name: fortio-server
    app.kubernetes.io/instance: fortio-server
    app.kubernetes.io/version: 3.2.0-SNAPSHOT.10
  name: fortio-server
spec:
  type: NodePort
  selector:
    app.kubernetes.io/name: fortio-server
  sessionAffinity: None
  ports:
    - name: http
      protocol: TCP
      port: 8080
      targetPort: 8080
      nodePort: 30455
    - name: http-m
      protocol: TCP
      port: 8070
      targetPort: 8070
      nodePort: 30457
    - name: grpc
      protocol: TCP
      port: 8079
      targetPort: 8079
      nodePort: 30456

EOF

```


```bash
kubectl -n idm-mark apply -f - <<EOF
apiVersion: v1
kind: Pod
metadata:
    name: fortio-server-l2
    annotations:
      sidecar.istio.io/inject: "true"
    labels:
      app.kubernetes.io/name: fortio-server-l2
      app: fortio-server-l2
      sidecar.istio.io/inject: "true"
spec:
    restartPolicy: Always
    imagePullSecrets:
    - name: docker-registry-key
    containers:
    - name: main-app
      image: docker.io/fortio/fortio
      imagePullPolicy: Always
      command: ["/usr/bin/fortio"]
      args: ["server"]
      ports:
      - containerPort: 8080
        protocol: TCP
        name: http
      - containerPort: 8079
        protocol: TCP
        name: grpc   
  nodeSelector:
    kubernetes.io/hostname: worknode5

---

apiVersion: v1
kind: Service
metadata:
  labels:
    app.kubernetes.io/name: fortio-server-l2
    app.kubernetes.io/instance: fortio-server-l2
    app.kubernetes.io/version: 3.2.0-SNAPSHOT.10
  name: fortio-server-l2
spec:
  type: ClusterIP
  selector:
    app.kubernetes.io/name: fortio-server-l2
  sessionAffinity: None
  ports:
    - name: http
      protocol: TCP
      port: 8080
      targetPort: 8080
    - name: grpc
      protocol: TCP
      port: 8079
      targetPort: 8079
EOF
```