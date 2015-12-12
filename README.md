HKG cache
=====================================

[![Build Status](https://travis-ci.org/mingchuno/golden-cache.svg?branch=develop)](https://travis-ci.org/mingchuno/golden-cache)

HKG is too slow. This is a cache backend + frontend written in Scala Play! and AngularJs

# How to start dev?

1. Backend: Install Scala(2.11.x), sbt(0.13.x), MongoDB(v3.x)
2. Frontend: Install node, npm, bower, grunt
3. `cd ./ui` and `npm install` and `bower install`
4. in project root, `sbt run` tio start the server
5. http://localhost:9000/ should work

# How to deploy?

1. `make clean` then `make` to package
2. uplaod `target/universal/golden-cache-{version}.zip` to your production machine
3. install `docker` and `docker-compose` in your production machine
4. unzip the pacakge and run `sudo start.sh` need sudo since it need to bind to port 80

# TODO

- **UI enhance**
- FAQ
- backend write test
- front end write test
- parse [img], [url]... tag