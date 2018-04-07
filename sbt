#!/bin/bash

echo "docker-compose start..."
(cd docker && docker-compose start)

sbt "$@"
