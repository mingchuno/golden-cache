#!/bin/bash

# force build again
sudo docker-compose -p hkg build
sudo docker-compose -p hkg up -d
sudo docker exec hkg_mongo_1 mongo localhost:27017/hkg /hkg-mongo-index.js