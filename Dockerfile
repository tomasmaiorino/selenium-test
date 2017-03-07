# **********************************************************************************************************************
# Copyright  Tomas Inc., 2016.
# All Rights Reserved.
#
# Usage: 
#   docker build -t selenium-test .
# **********************************************************************************************************************
FROM maven:3.3.9-jdk-8
MAINTAINER Maiorino Tomas <tomasmaiorino@gmail.com>

#APPLICATION CONFIGURATION
ENV APPLICATION_DIR /usr/src/app/
ENV APPLICATION_REPO https://github.com/tomasmaiorino/selenium-test.git
ENV APPLICATION_NAME selenium-test

# APP CONFIGURATION
EXPOSE 8080
RUN mkdir -p $APPLICATION_DIR 
RUN cd $APPLICATION_DIR && pwd && ls -altr
RUN git clone $APPLICATION_REPO
RUN cp -rv $APPLICATION_NAME/* $APPLICATION_DIR/
RUN cd $APPLICATION_DIR && ls -altr
RUN mvn clean install --file $APPLICATION_DIR/pom.xml
CMD ["mvn", "package"]
#CMD \
#     java -jar \
#     $APPLICATION_DIRtarget/selenium-test-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","/usr/src/app/target/selenium-test-0.0.1-SNAPSHOT.jar"]

