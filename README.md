HKG cache
=====================================

[![Build Status](https://travis-ci.org/mingchuno/golden-cache.svg?branch=develop)](https://travis-ci.org/mingchuno/golden-cache)

HKG is too slow. This is a cache backend + frontend written in Scala Play! and AngularJs

# How to start dev?

1. Backend: Install Scala(2.11.x), sbt(0.13.x), MongoDB(v3.x), Redis(v3.x)
2. Frontend: Install node, npm, bower, grunt
3. `cd ./ui` and `npm install` and `bower install`
4. Run MongoDB which is used to save post
5. Run Redis which is used to save history
6. In project root, `sbt run` to start the server
7. http://localhost:9000/ should work

# How to deploy?

1. `make clean` then `make` to package
2. uplaod `target/universal/golden-cache-{version}.zip` to your production machine
3. install `docker` and `docker-compose` in your production machine
4. unzip the pacakge, make sure `bin/golden-cache` is executable
5. run `sh golden-app.sh start`, it may need sudo since it need to bind to port 80

# How to test?

## Test Scala

## Test Angular JS

The Angualr JS uses Jasmine for testing
http://jasmine.github.io/1.3/introduction.html
The testing codes are put in test/spec/*.js

If you encounter any problems related to karma in running `grunt test`, please install the following package:
```
npm install karma
npm install phantomjs
npm install jasmine-core
```

1. cd `ui`
2. run `grunt test`

# TODO

- **UI enhance**
- FAQ
- backend write test
- front end write test
- parse [img], [url]... tag