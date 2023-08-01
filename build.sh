#!/bin/bash
set -e
JAVA_VERSION="11"
BUILD_FOLDER="build"
OUTPUT_JAR_NAME="GLaDOS.jar"
echo "Compiling glados !"
mkdir -p $BUILD_FOLDER
javac -cp "./libs/JDA-4.3.0_277-withDependencies.jar:./libs/jda-utilities-2.1-all.jar:./libs/json-20220320.jar:./libs/sqlite-jdbc-3.42.0.0.jar" --release $JAVA_VERSION -d $BUILD_FOLDER $(find src -type f -name "*.java")
cd $BUILD_FOLDER
jar cfe $OUTPUT_JAR_NAME Main $(find . -type f -name "*.class")
cp ../libs/*.jar .
cp ../config.json .
java -cp GLaDOS.jar:jda-utilities-2.1-all.jar:json-20220320.jar:JDA-4.3.0_277-withDependencies.jar:sqlite-jdbc-3.42.0.0.jar Main