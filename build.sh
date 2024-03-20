#!/bin/bash
mvn package
cp configs/*.json target
cd target
java -jar GLaDOS-2.0.0.jar