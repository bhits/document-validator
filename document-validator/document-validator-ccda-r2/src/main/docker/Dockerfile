FROM tomcat:8-jre8
# Copy war to container, argument is defined in pom.xml
ARG finalName
COPY $finalName /usr/local/tomcat/webapps/document-validator-ccda-r2.war