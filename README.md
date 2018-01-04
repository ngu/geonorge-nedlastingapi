# What is Geonorge Nedlasting-api

It's an implementation in Java of the Geonorge Download API 
Geonorge Download API enables seemless integration of distributes downloadable geographic datasets
from data owners (mostly governmental agencies, municipalities etc) into the geonorge portal.

The official API documentation available at https://nedlasting.geonorge.no/Help


With this download api you can integrate download and query a dataset for
  
  - Its capabilities (polygon selection,area selection etc)
  
  - Which formats the dataset supports
  
  - Which projections the dataset supports
  
  - Order the dataset in a chosen format/projection

## Install and run

This API-implementation is developed using Java, Maven and JUnit. 

### From Source
Checkout the source code from Github and build from source using Maven:
```
sh
$ mvn package 
$ java -jar target/geonorge-nedlastingapi-<version>.jar
$ 
```

### From releases

Download a [release](https://github.com/ngu/geonorge-nedlastingapi/releases) and run using Java

```
sh
$ 
$ java -jar geonorge-nedlastingapi-<version>.jar
$ 
```

This will start an embedded webserver running Jetty from a jar file. 
You can also change packaging in pom.xml from jar to war, and deploy war-file to Tomcat,JBoss etc.

The webserver (CORS-enabled) is running at no.geonorge.download.httpd.DownloadWebServer
The REST-api is autoconfigured with Jersey annotations at no.geonorge.rest.DownloadService

## Example usage

When running the embedded webserver (Jetty), the API will expose itself on your computer default port 10000:
http://<server>:10000/api

## Set up dev environment

Since this API is developed using Java and Maven, you have a range of possibilities.
The code is follows the convensions of Maven and is ignorant to specific Integrated Development Environments.
All you need to start develop is a a recent version of Java Develope Kit and Maven.

### Development using Eclipse, IntelliJ IDEA etc

Maven comes with support for multiple IDEs. You can tell Maven to generate project files and setup classpaths:

- Eclipse: mvn eclipse:eclipse
- IntelliJ IDEA: mvn idea:idea


## Configuration

Configuration can be done using environment variables, java VM arguments *or* a properties file

Here are example parameters as java VM arguments.
````
-Ddatabase.url=jdbc:postgresql://127.0.0.1/db -Ddatabase.driver=org.postgresql.Driver -Ddatabase.username=user -Ddatabase.password=pass
````

Here is an example properties file. It should be located in `./geonorge.properties` or `/etc/geonorge.properties`.
````
database.url=jdbc:postgresql://127.0.0.1/db
database.driver=org.postgresql.Driver
database.username=user
database.password=pass
cors=*
````

## Contributions accepted

We welcome contributions, as code preferrable as pull request here on Github. 

1. Fork our codebase
2. Make a branch for your contribution
3. Commit changes and push your changes to your branch
4. Make a pull request for your change. 

Example contributions: 

  * Extend and add support for more backends
  * File bugs or feature requests using [Issue Tracker](https://github.com/ngu/geonorge-nedlastingapi/issues/new)
  * Improve on API documentation and/or suite of unit tests.

## License and credit

License of this code is MIT - meaning you can do pretty much what you want with it. Full license-clause found in file LICENSE
Contact: Bjorn.Ove.Grotan AT ngu.no

This API is developed at the Geological survey of Norway
with help from [@halset](https://www.github.com/halset) from Electronic Chart Centre. 
Thanks also goes to Dag-Olav at [Arkitektum](http://www.arkitektum.no) for help during development.
