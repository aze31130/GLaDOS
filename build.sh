#!/bin/bash
mvn package
cd target
java -jar GLaDOS-2.0.0.jar