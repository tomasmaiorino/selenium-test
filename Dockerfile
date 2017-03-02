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

#CHROME CONFIGURATION
ENV CHROMEDRIVER_VERSION 2.27

RUN wget --no-verbose -O /tmp/chromedriver_linux64.zip http://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip && \
    mkdir -p /opt/chromedriver-${CHROMEDRIVER_VERSION} && \
    unzip /tmp/chromedriver_linux64.zip -d /opt/chromedriver-${CHROMEDRIVER_VERSION} && \
    chmod +x /opt/chromedriver-${CHROMEDRIVER_VERSION}/chromedriver && \
    rm /tmp/chromedriver_linux64.zip && \
    ln -fs /opt/chromedriver-${CHROMEDRIVER_VERSION}/chromedriver /usr/local/bin/chromedriver

# APP CONFIGURATION
RUN mkdir -p $APPLICATION_DIR 
RUN cd $APPLICATION_DIR && pwd && ls -altr
RUN git clone $APPLICATION_REPO
RUN cp -rv $APPLICATION_NAME/* $APPLICATION_DIR/
RUN cd $APPLICATION_DIR && ls -altr
RUN mvn clean install --file $APPLICATION_DIR/pom.xml