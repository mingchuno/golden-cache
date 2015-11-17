#!/bin/bash
sudo docker-compose -p hkg up -d
sudo docker exec hkg_mongo_1 mongo localhost:27017/hkg /hkg-mongo-index.js