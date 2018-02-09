# geonorge-nedlastingapi

An implementation in Java of the Geonorge Download API 
Geonorge Download API enables seemless integration of distributes downloadable geographic datasets
from data owners (mostly governmental agencies, municipalities etc) into the geonorge portal.

The official API documentation available at https://nedlasting.geonorge.no/Help



With this download api you can integrate download and query a dataset for
  
  - Its capabilities (polygon selection,area selection etc)
  
  - Which formats the dataset supports
  
  - Which projections the dataset supports
  
  - Order the dataset in a chosen format/projection


This API-implementation is developed using Java, Maven and JUnit.

Checkout the source code and build from source using Maven:
```
sh
$ mvn package 
$ java -jar target/geonorge-nedlastingapi-<version>.jar
$ 
```


This will start an embedded webserver running Jetty from a jar file. 
You can also change packaging in pom.xml from jar to war, and deploy war-file to Tomcat,JBoss etc.

The webserver (CORS-enabled) is running at no.geonorge.download.httpd.DownloadWebServer
The REST-api is autoconfigured with Jersey annotations at no.geonorge.rest.DownloadService



Use with your favourite IDE:

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

Contact: Bjorn.Ove.Grotan AT ngu.no
