#!/bin/bash

function start() {
  # force build again
  docker-compose -p hkg build
  docker-compose -p hkg up -d
  docker exec hkg_mongo_1 mongo localhost:27017/hkg /hkg-mongo-index.js
}

function stop() {
  docker-compose -p hkg stop web
  sudo rm RUNNING_PID
}

case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    stop
    start
    ;;
  *)
    echo "useage {start|stop|restart}"
    exit 1
    ;;
esac