# Semantic Hypermedia Search Engine

Implementation of a semantic hypermedia search engine to crawl and index the hypermedia layer of a distributed WoT system.
It enalbes autonomous agents to discover the environment and interact with it.

## Build it
Make sure to have gradle and Java 8+ installed, then run
~~~
./gradlew
~~~

## Run it
After building it, execute the following command:
~~~
java -jar build/libs/crawler-0.0-SNAPSHOT-fat.jar -conf src/main/conf/confi.json
~~~