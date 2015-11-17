HKG cache
=====================================

HKG is too slow. This is a cache backend + frontend written in Scala Play! and AngularJs

# How to start dev?

- Backend
  - Install Scala(2.11.x), sbt(0.13.x), MongoDB(v3.x)
  - run with `sbt run`
- Frontend
  - Install node, npm, bower, grunt
  - `sbt`
  - `npm install`
  - `bower install`
  - browse to `localhost:9000`
- DevOps
  - Docker
  - docker-compose

# TODO

- Docker integration
- Travis CI integration
- backend write test
- front end write test
- seperate View, Controller and Service logic in frontend
- UI enhance
- parse [img], [url]... tag