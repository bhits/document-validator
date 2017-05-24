# Short Description

The Document Validator is responsible for responsible for validating C32, C-CDA R1 and C-CDA R2 clinical documents.

# Full Description

# Supported Tags and Respective `Dockerfile` Links

[`1.0.0(latest)`](https://github.com/bhits/document-validator/releases/tag/1.0.0)

[`Current Dockerfile`](https://github.com/bhits/document-validator/blob/master/document-validator/src/main/docker/Dockerfile)

For more information about this image, the source code, and its history, please see the [GitHub repository](https://github.com/bhits/document-validator).

# What is Document Validator?

The Document Validator Service is responsible for validating C32, C-CDA R1 and C-CDA R2 clinical documents. It is a RESTFul Web Service wrapper around [MDHT](https://www.projects.openhealthtools.org/sf/projects/mdht/) (Model Driven Health Tools) libraries. It does schema validation for C32 and both schema and schematron validation for C-CDA and returns the validation results from MDHT in the response. Document Validator Service is used directly by [DSS](https://github.com/bhits/dss) (Document Segmentation Service) to validate the document before and after the segmentation.
For more information and related downloads for Consent2Share, please visit [Consent2Share](https://bhits.github.io/consent2share/).

# How to use this image

## Start a Document Validator instance

Be sure to familiarize yourself with the repository's [README.md](https://github.com/bhits/document-validator) file before starting the instance.

document-validator:

`docker run  --name document-validator -d bhits/document-validator:latest <additional program arguments>`

*NOTE: In order for this service to fully function as a microservice in the Consent2Share application, it is required to setup the dependency microservices and the support level infrastructure. Please refer to the Consent2Share Deployment Guide in the corresponding Consent2Share release (see [Consent2Share Releases Page](https://github.com/bhits/consent2share/releases)) for instructions to setup the Consent2Share infrastructure.*

## Environment Variable

When you start the document-validator image, you can edit the configuration of the instance by passing one or more environment variables on the command line. 

### JAVA_OPTS

This environment variable is used to setup JVM argument, such as memory configuration.

`docker run --name document-validator -e "JAVA_OPTS=-Xms512m -Xmx700m -Xss1m" -d bhits/document-validator:latest`

# Supported Docker versions
This image is officially supported on Docker version 1.12.1.

Support for older versions (down to 1.6) is provided on a best-effort basis.

Please see the [Docker installation documentation](https://docs.docker.com/engine/installation/) for details on how to upgrade your Docker daemon.

# License

View [license](https://github.com/bhits/document-validator/blob/master/LICENSE) information for the software contained in this image.

# User Feedback

## Documentation

Documentation for this image is stored in the [bhits/document-validator](https://github.com/bhits/document-validator) GitHub repository. Be sure to familiarize yourself with the repository's README.md file before attempting a pull request.

## Issues

If you have any problems with or questions about this image, please contact us through a [GitHub issue](https://github.com/bhits/document-validator/issues).

