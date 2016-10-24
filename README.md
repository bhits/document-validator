# Document Validator  API

The Document Validator API is responsible for validating C-CDA R1 and C-CDA R2 clinical documents. It is a RESTful Web Service wrapper around [MDHT](https://www.projects.openhealthtools.org/sf/projects/mdht/) (Model Driven Health Tools) libraries. It does both schema and schematron validation and returns the validation results from MDHT in the response. Document Validator API is used directly by [DSS](https://github.com/bhits/dss-api) (Document Segmentation Service) to validate the document before and after the segmentation.

## Build

### Prerequisites

+ [Oracle Java JDK 8 with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ [Docker Engine](https://docs.docker.com/engine/installation/) (for building a Docker image from the project)

### Structure

This project contains a [**parent** `pom.xml` file](document-validator/pom.xml) that aggregates two web service projects. One of these web services is for C-CDA R1 and the other one is for C-CDA R2 validation.

### Commands

This is a Maven project and requires [Apache Maven](https://maven.apache.org/) 3.3.3 or greater to build it. It is recommended to use the *Maven Wrapper* scripts provided with this project. *Maven Wrapper* requires an internet connection to download Maven and the project dependencies for the very first build.

To build the project, navigate to the folder that contains the [**parent** `pom.xml` file](document-validator/pom.xml) using terminal/command line.

+ To build a JAR:
    + For Windows, run `mvnw.cmd clean install`
    + For *nix systems, run `mvnw clean install`
+ To build a Docker Image (this will create two images with `bhits/document-validator-ccda-r1:latest` and `bhits/document-validator-ccda-r2:latest` tags):
    + For Windows, run `mvnw.cmd clean install & cd document-validator-ccda-r1 & ..\mvnw.cmd clean package docker:build & cd.. & cd document-validator-ccda-r2 & ..\mvnw.cmd clean package docker:build & cd..`
    + For *nix systems, run `mvnw clean install; cd ./document-validator-ccda-r1; ../mvnw clean package docker:build; cd ..; cd ./document-validator-ccda-r2; ../mvnw clean package docker:build; cd ..`

+ External Dependencies:
  MDHT dependencies are needed to build and run Document Validator correctly. Since the jars are not available in Maven central repository, they are provided and attached in the release page. The following jars (listed in the order of ${groupId}  | ${artifactId}  | ${version}  | ${jarName} ) need to be manually installed by the following command : mvn install:install-file -Dfile=${pathToJar}/${jarName}.jar -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar
           
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.cda.consol   | 2.5.8.20160323   | org.openhealthtools.mdht.uml.cda.consol-2.5.8.20160323.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.cda.consol2   | 2.5.8.20160323   | org.openhealthtools.mdht.uml.cda.consol2-2.5.8.20160323.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.cda   | 2.5.19.201603232017   | org.openhealthtools.mdht.uml.cda-2.5.19.201603232017.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.emf.runtime   | 2.5.19.201603232017   | org.openhealthtools.mdht.emf.runtime-2.5.19.201603232017.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.hl7.rim   | 2.5.19.201603232017   | org.openhealthtools.mdht.uml.hl7.rim-2.5.19.201603232017.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.hl7.datatypes   | 2.5.19.201603232017   | org.openhealthtools.mdht.uml.hl7.datatypes-2.5.19.201603232017.jar
    + org.mdht.dependencies   | org.openhealthtools.mdht.uml.hl7.vocab   | 2.5.19.201603232017   | org.openhealthtools.mdht.uml.hl7.vocab-2.5.19.201603232017.jar
    + org.mdht.dependencies   | org.eclipse.emf.common   | 2.9.2.v20131212-0545   | org.eclipse.emf.common-2.9.2.v20131212-0545.jar
    + org.mdht.dependencies   | org.eclipse.emf.ecore   | 2.9.2.v20131212-0545   | org.eclipse.emf.ecore-2.9.2.v20131212-0545
    + org.mdht.dependencies   | org.eclipse.emf.ecore.xmi   | 2.9.2.v20131212-0545   | org.eclipse.emf.ecore.xmi-2.9.1.v20131212-0545.jar
    + org.mdht.dependencies   | org.eclipse.ocl   | 3.3.0.v20140120-1508   | org.eclipse.ocl-3.3.0.v20140120-1508.jar
    + org.mdht.dependencies   | org.eclipse.ocl.common   | 1.1.0.v20130531-0544   | org.eclipse.ocl.common-1.1.0.v20130531-0544.jar
    + org.mdht.dependencies   | org.eclipse.ocl.ecore   | 3.3.0.v20130520-1222   | org.eclipse.ocl.ecore-3.3.0.v20130520-1222.jar
    + org.mdht.dependencies   | org.eclipse.uml2.common   | 1.8.2.v20140202-2055   | org.eclipse.uml2.common-1.8.2.v20140202-2055.jar
    + org.mdht.dependencies   | org.eclipse.uml2.types   | 1.1.0.v20140202-2055   | org.eclipse.uml2.types-1.1.0.v20140202-2055.jar
    + org.mdht.dependencies   | lpg.runtime.java   | 2.0.17.v201004271640   | lpg.runtime.java-2.0.17.v201004271640.jar    

## Run

### Prerequisites

These APIs are [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) projects that require a separate application server to run it. [Apache Tomcat 8](http://tomcat.apache.org/) is the recommended application server to run these APIs. The expected default context paths for these APIs in a development (single application server) environment are `/documentValidator/r1` for C-CDA R1 and `/documentValidator/r2` for C-CDA R2 validators. In Docker environment, they both use the same `/documentValidator` context path.

### Deployment

For easy deployment in single application server environment:

1. Find the `war` file located in `\document-validator\document-validator-ccda-r1\target` or `\document-validator\document-validator-ccda-r2\target` (for CCDA-R1 and CCDA-R2 respectfully) folder after building the project.
2. Rename the file to `documentValidator#r1.war` or `documentValidator#r2.war`
3. Copy `documentValidator#r1.war` or `documentValidator#r2.war` to Tomcat's `webapps` folder
4. Start up Tomcat.

Please refer to [Tomcat Web Application Deployment](http://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html) documentation for more details about Tomcat deployment.

## Configure

These APIs are self-contained services and do not require any application configuration.

### Enable SSL

Please refer to [Apache Tomcat 8 SSL/TLS Configuration HOW-TO](https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html) documentation for configuring SSL on Tomcat.

In the Docker environment, `$TOMCAT_HOME/conf/server.xml` can be overridden by mounting a volume as `"/path/on/dockerhost/server.xml:/usr/local/tomcat/conf/server.xml"`. The mounted `server.xml` file can refer to a keystore inside the container that can be separately mounted like `"/path/on/dockerhost/ssl_keystore.keystore:/ssl_keystore.keystore"`.

In `docker-compose.yml`, this can be provided as:

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
           keystoreFile="/ssl_keystore.keystore" keystorePass="strongpassword"
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

If you have any questions, comments, or concerns please see [Consent2Share](https://bhits.github.io/consent2share/) project site.

## Report Issues

Please use [GitHub Issues](https://github.com/bhits/document-validator/issues) page to report issues.

[//]: # (License)