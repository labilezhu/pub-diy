#! /bin/sh

/bin/echo -e "#include <new>\n $1 {} "

/bin/echo -e "#include <new>\n $1 {} " | g++ -x c++ -S - -o- | grep "^_.*:$" | sed -e 's/:$//'

# /home/labile/pub-diy/low-tec/trace/trace-istio/bpftrace/ccmangle.sh "void* operator new(std::size_t)"
# /home/labile/pub-diy/low-tec/trace/trace-istio/bpftrace/ccmangle.sh "Envoy::Server::ActiveTcpSocket::ActiveTcpSocket(Envoy::Server::ActiveTcpListener&, std::__1::unique_ptr<Envoy::Network::ConnectionSocket, std::__1::default_delete<Envoy::Network::ConnectionSocket> >&&, bool)"
