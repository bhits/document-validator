# Document Validator Service

The Document Validator Service is responsible for validating C32, C-CDA R1.1 and C-CDA R2.1 clinical documents. It is a RESTful Web Service wrapper around [MDHT](https://www.projects.openhealthtools.org/sf/projects/mdht/) (Model Driven Health Tools) libraries. It does schema validation for C32 and both schema and schematron validation for C-CDA and returns the validation results from MDHT in the response. Document Validator Service is used directly by [DSS](https://github.com/bhits/dss) (Document Segmentation Service) to validate the document before and after the segmentation.

## Build

### Prerequisites

+ [Oracle Java JDK 8 with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ [Docker Engine](https://docs.docker.com/engine/installation/) (for building a Docker image from the project)

### Commands

This is a Maven project and requires [Apache Maven](https://maven.apache.org/) 3.3.3 or greater to build it. It is recommended to use the *Maven Wrapper* scripts provided with this project. *Maven Wrapper* requires an internet connection to download Maven and the project dependencies for the very first build.

To build the project, navigate to the folder that contains the [`pom.xml` file](document-validator/pom.xml) using the terminal/command line.

+ External Dependencies:
  MDHT dependencies are needed to build and run Document Validator correctly. Since the jars are not available in Maven central repository, they are provided and attached in the release page. And also there is a batch file tool named `deployMDHTToLocalMavenRepository.bat` that is used to deploy all required jars to local maven repository
           
    + org.openhealthtools.mdht.uml.cda.consol2 | 2.6.3.20170323 | org.openhealthtools.mdht.uml.cda.consol2-2.6.3.20170323.jar
    + org.openhealthtools.mdht.uml.cda.mu2consol | 2.6.3.20170323 | org.openhealthtools.mdht.uml.cda.mu2consol-2.6.3.20170323.jar
    + org.eclipse.mdht.emf.runtime | 3.0.0.201703230502 | org.eclipse.mdht.emf.runtime-3.0.0.201703230502.jar
    + org.eclipse.mdht.uml.cda | 3.0.0.201703230502 | org.eclipse.mdht.uml.cda-3.0.0.201703230502.jar
    + org.eclipse.mdht.uml.hl7.rim | 3.0.0.201703230502 | org.eclipse.mdht.uml.hl7.rim-3.0.0.201703230502.jar
    + org.eclipse.mdht.uml.hl7.datatypes | 3.0.0.201703230502 | org.eclipse.mdht.uml.hl7.datatypes-3.0.0.201703230502.jar
    + org.eclipse.mdht.uml.hl7.vocab | 3.0.0.201703230502 | org.eclipse.mdht.uml.hl7.vocab-3.0.0.201703230502.jar
    + org.eclipse.emf.common | 2.11.1.v20160208-0816 | org.eclipse.emf.common-2.11.1.v20160208-0816.jar
    + org.eclipse.emf.ecore | 2.11.2.v20160208-0816 | org.eclipse.emf.ecore-2.11.2.v20160208-0816.jar
    + org.eclipse.emf.ecore.xmi | 2.11.1.v20160208-0816 | org.eclipse.emf.ecore.xmi-2.11.1.v20160208-0816.jar
    + org.eclipse.ocl | 3.5.0.v20150521-1211 | org.eclipse.ocl-3.5.0.v20150521-1211.jar
    + org.eclipse.ocl.common | 1.3.0.v20150519-0914 | org.eclipse.ocl.common-1.3.0.v20150519-0914.jar
    + org.eclipse.ocl.ecore | 3.5.0.v20150525-1635 | org.eclipse.ocl.ecore-3.5.0.v20150525-1635.jar
    + org.eclipse.uml2.common | 2.1.0.v20160201-0816 | org.eclipse.uml2.common-2.1.0.v20160201-0816.jar
    + org.eclipse.uml2.types | 2.0.0.v20160201-0816 | org.eclipse.uml2.types-2.0.0.v20160201-0816.jar
    + lpg.runtime.java | 2.0.17.v201004271640 | lpg.runtime.java-2.0.17.v201004271640.jar    

  For easy deployment all MDHT dependencies to local maven repository:

    1. Download all required MDHT dependencies that attached in the release page and the `deployMDHTToLocalMavenRepository.bat` script tool.
    2. Put all files in the same folder
    3. Double click to run script tool.

+ To build a WAR:
    + For Windows, run `mvnw.cmd clean install`
    + For *nix systems, run `mvnw clean install`
+ To build a Docker Image (this will create an images with `bhits/document-validator:latest` tags):
    + For Windows, run `mvnw.cmd clean package docker:build`
    + For *nix systems, run `mvnw clean package docker:build`

## Run

### Prerequisites

This is a [Spring Boot](https://projects.spring.io/spring-boot) project. Since running the project within the Spring Boot embedded servlet container has conflicts with [Eclipse Modeling Framework (EMF)](http://www.eclipse.org/modeling/emf/), it requires a external servlet container to run it. [Apache Tomcat 8](http://tomcat.apache.org/) is the recommended application server to run this application. The expected default context path for this service in a development (single application server) environment are /document-validator. In Docker environment, use the same /document-validator context path.

### Deployment

For easy deployment in single application server environment:

1. Find the `war` file located in `\document-validator\target` folder after building the project.
2. Rename the file to document-validator.war
3. Copy it to Tomcat's `webapps` folder
4. Start up Tomcat.

Please refer to [Tomcat Web Application Deployment](http://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html) documentation for more details about Tomcat deployment.

## Configure


This service utilizes [`Configuration Server`](https://github.com/bhits/config-server) which is based on [Spring Cloud Config](https://github.com/spring-cloud/spring-cloud-config) to manage externalized configuration, which is stored in a `Configuration Data Git Repository`. We provide a [`Default Configuration Data Git Repository`]( https://github.com/bhits/c2s-config-data).

This service can run with the default configuration, which is targeted for a local development environment. Default configuration data is from three places: `bootstrap.yml`, `application.yml`, and the data which `Configuration Server` reads from `Configuration Data Git Repository`. Both `bootstrap.yml` and `application.yml` files are located in the `resources` folder of this source code.

We **recommend** overriding the configuration as needed in the `Configuration Data Git Repository`, which is used by the `Configuration Server`.

Also, please refer to [Spring Cloud Config Documentation](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) to see how the config server works, [Spring Boot Externalized Configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) documentation to see how Spring Boot applies the order to load the properties, and [Spring Boot Common Properties](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) documentation to see the common properties used by Spring Boot.

### Enable SSL

Please refer to [Apache Tomcat 8 SSL/TLS Configuration HOW-TO](https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html) documentation for configuring SSL on Tomcat.

In the Docker environment, `$TOMCAT_HOME/conf/server.xml` can be overridden by mounting a volume as `"/path/on/dockerhost/server.xml:/usr/local/tomcat/conf/server.xml"`. The mounted `server.xml` file can refer to a keystore inside the container that can be separately mounted like `"/path/on/dockerhost/ssl_keystore.keystore:/ssl_keystore.keystore"`.

In `docker-compose.yml`, this can be provided as:

```yml
version: '2'
services:
...
  document-validator.c2s.com:
    image: "bhits/document-validator:latest"
    volumes:
      - /path/on/dockerhost/server.xml:/usr/local/tomcat/conf/server.xml
      - /path/on/dockerhost/ssl_keystore.keystore:/ssl_keystore.keystore
...
```

*Example `server.xml` Snippet with `keystore` Configuration:*

```
...
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="200"
           scheme="https" secure="true" SSLEnabled="true"
           keystoreFile="/ssl_keystore.keystore" keystorePass="strongpassword"
           clientAuth="false" sslProtocol="TLS"/>
...
```

### Override Java CA Certificates Store In Docker Environment

Java has a default CA Certificates Store that allows it to trust well-known certificate authorities. For development and testing purposes, one might want to trust additional self-signed certificates. In order to override the default Java CA Certificates Store in a Docker container, one can mount a custom `cacerts` file over the default one in the Docker image as follows: `docker run -d -v "/path/on/dockerhost/to/custom/cacerts:/etc/ssl/certs/java/cacerts" bhits/document-validator:latest`

*NOTE: The `cacerts` references given in the both sides of volume mapping above are files, not directories.*

[//]: # (## API Documentation)

[//]: # (## Notes)

[//]: # (## Contribute)

## License
View [license](https://github.com/bhits/document-validator/blob/master/LICENSE) information for the software contained in this repository.

## Contact

If you have any questions, comments, or concerns please see [Consent2Share](https://bhits.github.io/consent2share/) project site.

## Report Issues

Please use [GitHub Issues](https://github.com/bhits/document-validator/issues) page to report issues.

[//]: # (License)