#!/bin/sh

docker build -t rockyj/advent23-rb . && \
  echo "-------------------------------------------------------------" && \
  echo "-------------------Running the container---------------------" && \
  echo "-------------------------------------------------------------" && \
  docker run -it rockyj/advent23-rb:latest
