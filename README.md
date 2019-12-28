[![Build Status](https://travis-ci.com/naderghanbari/postgis-slick-example.svg?branch=master)](https://travis-ci.com/naderghanbari/postgis-slick-example)

# Domain Driven PostGIS and Slick

This project is a showcase of using Postgres and PostGIS via Slick in a domain driven 
system. 

Tests are the only runnable things in this project and the application is intentionally kept 
headless to keep things simple. Nothing is exposed to the outside world via REST or other means.
Because the focus is on repositories, which belong to the domain layer, there is no api layer.
To see how things work in action, check the unit tests. 

## Build

    > sbt compile

## Test

    > docker container run --env POSTGRES_DBNAME=gis --publish 127.0.0.1:25432:5432 --detach kartoza/postgis:12.0 
    > sbt test

We use docker to run a PostGIS-enabled instance of Postgres. 
[kartoza/postgis:12.0](https://hub.docker.com/r/kartoza/postgis/) runs Postgres `12.0` and PostGIS `3.0`, both of 
which are the latest versions at this time.

For simple features, one could use `H2` in Postgres compatibility 
mode. But as soon as you start using advanced features or extensions like PostGIS, 
H2 won't serve the purpose anymore. With `docker` everywhere, running containers 
is getting easier and easier, more so for CI/CD. For instance, GitLab's CI/CD has native support
for running docker services as part of the pipeline. The bottom line is don't
waste yor time on H2.  

[mdillon/postgis](https://hub.docker.com/r/mdillon/postgis) is another option but
it does not support `PostGIS 3.0` at the moment.

See [.travis.yml](.travis.yml) for more info.
