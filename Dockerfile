# **********************************************************************************************************************
# Copyright  Tomas Inc., 2016.
# All Rights Reserved.
#
# Usage: 
#   docker build -t selenium-test .
# **********************************************************************************************************************
FROM maven:3.3.9-jdk-8
MAINTAINER Maiorino Tomas <tomasmaiorino@gmail.com>

# Set environment variables
ENV GECKO_DRIVER_URL https://github.com/mozilla/geckodriver/releases/download/v0.14.0/geckodriver-v0.14.0-linux64.tar.gz
ENV SELENIUM_TEMP_DIR /sel/tmp/
ENV SELENIUM_DIR /sel/
ENV GECKO_GZ_FILE geckodriver-v0.14.0-linux64.tar.gz
ENV GECKO_DRIVER_NAME geckodriver
ENV APPLICATION_DIR app
ENV APPLICATION_REPO https://github.com/tomasmaiorino/selenium-test.git
ENV APPLICATION_NAME selenium-test

# Install packages required to install Selenium
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y git
RUN DEBIAN_FRONTEND=noninteractive apt-get update

RUN mkdir -p $SELENIUM_TEMP_DIR
RUN cd $SELENIUM_TEMP_DIR
RUN wget $GECKO_DRIVER_URL
RUN tar -xvzf $GECKO_GZ_FILE
RUN cp -v $GECKO_DRIVER_NAME $SELENIUM_DIR

# Project structure
RUN cd $SELENIUM_DIR && mkdir $APPLICATION_DIR && cd $APPLICATION_DIR && pwd
RUN git clone $APPLICATION_REPO
RUN cd $APPLICATION_NAME && pwd && ls
#RUN echo $PATH
RUN mvn -v
RUN which mvn
RUN mvn clean install --file pom.xml