FROM bellsoft/liberica-openjdk-alpine:21

# Install curl jq
RUN apk add curl jq dos2unix

# workspace
WORKDIR /home/selenium-docker


# Add the reqiured files
ADD target/docker-resources     ./
ADD runner.sh                   runner.sh

RUN dos2unix runner.sh


# Run the tests
ENTRYPOINT sh runner.sh