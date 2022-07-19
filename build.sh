#!/bin/bash

sed -i.bak "s|dictionary.directory_path=.*|dictionary.directory_path=$PWD\/src\/main\/resources\/dictionary|" xml-replies/src/main/resources/replies-processor.properties
echo "Xml-replies configuration updated"

./gradlew bootJar

cp -r src/main/resources/dictionary build/libs
