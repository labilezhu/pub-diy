# mkdir -p ~/$EID/.docker
# export DOCKER_CONFIG=~/$EID/.docker
# docker login

set -e


docker build -t cndt-bcc-ub -f Dockerfile.bionic .


docker tag cndt-bcc-ub cndt-bcc-ub:latest

echo You RUN:
echo docker push cndt-bcc-ub:latest