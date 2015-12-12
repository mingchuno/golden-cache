FROM mongo

COPY hkg-mongo-index.js /hkg-mongo-index.js

VOLUME /data

CMD mongod --storageEngine=wiredTiger --dbpath /data