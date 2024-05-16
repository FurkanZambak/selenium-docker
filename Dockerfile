FROM bellsoft/liberica-openjdk-alpine:21

# Install curl jq
RUN apk add curl jq

# workspace
WORKDIR /home/selenium-docker


# Add the reqiured files
ADD target/docker-resources     ./
ADD runner.sh                   runner.sh


# Run the tests
ENTRYPOINT sh runner.sh