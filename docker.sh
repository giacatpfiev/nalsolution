#!/usr/bin/env bash

docker container stop c-mysql80
docker container rm c-mysql80

docker run -e MYSQL_ROOT_PASSWORD=root123456 -v `pwd`/_docker/my.cnf:/etc/mysql/my.cnf -p 3309:3306 --name c-mysql80 -d mysql:8.0


