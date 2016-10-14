# Document Validator  API

Document Validator API is responsible for validating CCDA R1 OR R2 clinical documents. It is a Web Service wrapper around [MDHT](https://www.projects.openhealthtools.org/sf/projects/mdht/) (Model Driven Health Tools) library. It does schermatron and schema validation for CCDA R1 and R2 and only 0scherma validation for C32. Document Validator API is used directly by [DSS](https://github.com/FEISystems/dss-api/tree/dev) (Document Segmentation Service) to validate the document before and after segmentation.

## Build

### Prerequisites

+ [Oracle Java JDK 8 with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ [Docker Engine](https://docs.docker.com/engine/installation/) (for building a Docker image from the project)

### Commands

This is a Maven project and requires [Apache Maven](https://maven.apache.org/) 3.3.3 or greater to build it. It is recommended to use the *Maven Wrapper* scripts provided with this project. *Maven Wrapper* requires internet connection to download Maven and project dependencies for the very first build.

To build the project, navigate to the folder that contains the [**parent** `pom.xml` file](document-validator/pom.xml) using terminal/command line.

+ To build a JAR:
    + For Windows, run `mvnw.cmd clean install`
    + For *nix systems, run `mvnw clean install`
+ To build a Docker Image (this will create an image with `bhits/document-validator:latest` tag):
    + For Windows, run `mvnw.cmd clean install & cd web & ..\mvnw.cmd clean package docker:build & cd..`
    + For *nix systems, run `mvnw clean install; cd ./web; ../mvnw clean package docker:build; cd ..`


## Run

### Commands

This API is a [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) project that requires a separate application server to run it. [Apache Tomcat 8](http://tomcat.apache.org/) is the recommended application server to run this API. The expected default context path for this API are `/documentValidator/r1` and `/documentValidator/r2`.

### Deployment

For easy deployment:

1. Find the `war` file located in `\document-validator\document-validator-ccda-r1\target` or `\document-validator\document-validator-ccda-r2\target` (for CCDA-R1 and CCDA-R2 respectfully) folder after building the project.
2. Rename the file to `document-validator-ccda-r1.war` or `document-validator-ccda-r2.war` if it has a different name.
3. Copy `document-validator-ccda-r1.war` or `document-validator-ccda-r2.war`to Tomcat's `webapps` folder
5. Start up Tomcat.

Please refer to [Tomcat Web Application Deployment](http://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html) documentation for more details about Tomcat deployment.

### Examples for Overriding a Configuration in Spring Boot

#### Override a Configuration Using Program Arguments While Running as a Docker Container for document-validator-ccda-r1:

+ `docker run -d bhits/document-validator-ccda-r1:latest --server.port=80 `

+ In a `docker-compose.yml`, this can be provided as:
```yml
version: '2'
services:
...
  document-validator-ccda-r1.c2s.com:
    image: "bhits/document-validator-ccda-r1:latest"
    command: ["--server.port=80"]
...
```

*NOTE: Please note taht thesame applies for document-validator-ccda-r2.
Also, these additional arguments will be appended to the default `ENTRYPOINT` specified in the `Dockerfile` unless the `ENTRYPOINT` is overridden.*

### Enable SSL

For simplicity in development and testing environments, SSL is **NOT** enabled by default configuration. SSL can easily be enabled following the examples below:


Please refer to [Apache Tomcat 8 SSL/TLS Configuration HOW-TO](https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html) documentation for configuring SSL on Tomcat.

#### Enable SSL While Running as a Docker Container

In Docker environment, `$TOMCAT_HOME/conf/server.xml` can be overridden by mounting a volume as `"/path/on/dockerhost/server.xml:/usr/local/tomcat/conf/server.xml"`. The mounted `server.xml` file can refer to a keystore inside the container that can be separately mounted like `"/path/on/dockerhost/ssl_keystore.keystore:/ssl_keystore.keystore"`.

In a `docker-compose.yml`, this can be provided as:

```yml
version: '2'
services:
...
  document-validator-ccda-r1.c2s.com:
    image: "bhits/document-validator-ccda-r1:latest"
    volumes:
      - /path/on/dockerhost/server.xml:/usr/local/tomcat/conf/server.xml
      - /path/on/dockerhost/ssl_keystore.keystore:/ssl_keystore.keystore
...
```

*Example `server.xml` Snippet with `keystore` Configuration:*
```xml
...
<!-- Define a SSL Coyote HTTP/1.1 Connector on port 8443 -->
<Connector
           protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="200"
           scheme="https" secure="true" SSLEnabled="true"
           keystoreFile="/ssl_keystore.keystore" keystorePass="changeit"
           clientAuth="false" sslProtocol="TLS"/>
...
```

### Override Java CA Certificates Store In Docker Environment

Java has a default CA Certificates Store that allows it to trust well-known certificate authorities. For development and testing purposes, one might want to trust additional self-signed certificates. In order to override the default Java CA Certificates Store in a Docker container, one can mount a custom `cacerts` file over the default one in the Docker image as `docker run -d -v "/path/on/dockerhost/to/custom/cacerts:/etc/ssl/certs/java/cacerts" bhits/document-validator-ccda-r1:latest`

*NOTE: The `cacerts` references given in the both sides of volume mapping above are files, not directories.*

[//]: # (## API Documentation)


[//]: # (## Notes)

[//]: # (## Contribute)

## Contact

If you have any questions, comments, or concerns please see [Consent2Share]() project site.

## Report Issues

Please use [GitHub Issues](https://github.com/bhits/document-validator-api/issues) page to report issues.

[//]: # (License)