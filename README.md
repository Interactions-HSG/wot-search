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
java -jar build/libs/crawler-0.0-SNAPSHOT-fat.jar -conf src/main/conf/config1.json
~~~

## Use it
To get started on using the Semantic Hypermedia Search Engine, here is a Postman collection: https://www.postman.com/collections/97c36583a597a77b7168

## Precompiled Corese because of compatibility issue
This repo currently uses a precompiled corese version located in the 'corese-jars' directory.
The corese version is based on the following PR which fixes a runtime error:
https://github.com/Wimmics/corese/pull/65
Once the PR has been merged and the change is reflected in a new corese version, the official maven dependency can be used again in the build.gradle file.


curl -X POST 'http://localhost:9090/searchEngine' -H 'Content-Type: application/sparql-query' --data-raw '@prefix td: <https://www.w3.org/2019/wot/td#>. select ?x where { ?x a td:Thing }'

curl -X POST http://localhost:9090/crawler/links -H "Content-Type: application/json" -d '{"link": "https://purl.org/hmas/"}'

curl -X POST http://localhost:9090/crawler/links -H "Content-Type: application/json" -d '{"link": "https://purl.org/hmas/"}'


curl -X 'POST' 'http://localhost:9090/crawler/registrations' -H 'accept: */*' -H 'Content-Type: application/json' -d '"http://localhost:8899/workspaces/61"'