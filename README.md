HKG cache
=====================================

[![Join the chat at https://gitter.im/mingchuno/golden-cache](https://badges.gitter.im/mingchuno/golden-cache.svg)](https://gitter.im/mingchuno/golden-cache?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/mingchuno/golden-cache.svg?branch=develop)](https://travis-ci.org/mingchuno/golden-cache) [![Greenkeeper badge](https://badges.greenkeeper.io/mingchuno/golden-cache.svg)](https://greenkeeper.io/)

HKG is too slow. This is a cache backend + frontend written in Scala Play! and AngularJs

# How to start dev?

1. Backend: Install Scala(2.11.x), sbt(0.13.x), MongoDB(v3.x), Redis(v3.x).
	1. `brew install scala sbt mongodb redis`.
2. Frontend: Install node, npm, bower, grunt.
	1. `brew install npm`.
	2. `npm install -g bower grunt-cli`.
3. `cd ./ui` and `npm install` and `bower install`.
4. Run MongoDB which is used to save post.
	1. Run `mongod`. It defaults to read `/data/db` and needs `rwx` permission.
5. Run Redis which is used to save history.
	1. Run `redis-server`.
6. In project root, `sbt run` to start the server.
	1. If it feels stuck, run `sbt run -v` for more details.
7. `http://localhost:9000/` should work.

# How to deploy?

1. `make clean` then `make` to package
2. uplaod `target/universal/golden-cache-{version}.zip` to your production machine
3. install `docker` and `docker-compose` in your production machine
4. unzip the pacakge, make sure `bin/golden-cache` is executable
5. run `sh golden-app.sh start`, it may need sudo since it need to bind to port 80

# How to test?

## Test in Scala (Backend)

`sbt test`

## Test in AngularJS (Frontend) 

The Angualr JS uses [Jasmine](http://jasmine.github.io/1.3/introduction.html) for testing.
The testing codes are written in **test/spec/*.js**

If you encounter any problems related to karma in running `grunt test`, please install the following package:

```
npm install karma phantomjs jasmine-core
```

1. `cd ui`
2. `grunt test`

# TODO

- **UI enhance**
- FAQ
- backend write test
- front end write test
- parse [img], [url]... tag