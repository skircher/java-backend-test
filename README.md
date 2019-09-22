# Robotic Cleaner API

## Table of Contents

- [Overview](#overview)
- [Setup](#setup)
    - [Installing Java](#installing-java)
    - [Installing Maven](#installing-maven)
- [Running the Application](#running-the-application)
    - [Additional Information](#additional-information)


## Overview
This application is a Java based web service that navigates an imaginary robotic cleaner through an oil spill in the sea. It accepts JSON POST requests including an `areaSize`, `startingPosition`, list of `oilPatches`, and `navigationInstructions` to aid it during its journey. The app will then let you know where the cleaner has settled and how many spills its cleaned up along the way.

## Setup
his repository contains a simple Java code base which uses Maven and SpringBoot to manage dependencies, build the project, and create a REST client.
In order to run the application, you'll need the following, both of which need to be added to `PATH`:
* Java 11 or higher (the application uses lambda functionality)
* Apache Maven 3.x

Optionally, I have included a [Postman](https://www.getpostman.com/) collection which captures a number of testing scenarios. Feel free to download the application if you'd like to make use of it!

### Installing Java
* **Via Oracle Website** - Download and instructions for all platforms [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html).
* **Via Package Managers** (I am a big fan of OpenJDK)
  * *Mac* - 
    ```
    brew tap AdoptOpenJDK/openjdk
    brew install adoptopenjdk11
    ```
  * *Debian, Ubuntu* -
    `sudo apt-get install openjdk-11-jdk`
  * *RHEL, Fedora, CentOS* -
    `su -c "yum install java-1.11.0-openjdk"`
  * *Arch* - You probably know what you're doing
  * [More info](https://openjdk.java.net/install/)
  
### Installing Maven
* **Via the Apache Website** - Installation info can be found [here](https://maven.apache.org/install.html), and a download [here](https://maven.apache.org/download.cgi)
* **Via Package Managers**
  * *Mac* - `brew install maven`
  * *Debian, Ubuntu* -
    `sudo apt install maven`

### Running the Application
Once you've got things installed, running the application is relatively straightforward. Spring boot exposes `/api/cleaner`, so your request should by default target `localhost:8080/api/cleaner`

To enable, simply:
* `mvn clean install`
* Ensure `localhost:8080`, the default Tomcat port, is not in use.
* `mvn spring-boot:run`
* Submit your POST request:
  * Via Postman
  * Via Curl: `curl --header "Content-Type: application/json" -d "{yourVeryLongJSONRequestHere}" http://localhost:8080/api/cleaner`
  * Requests should follow the following format:
    ```json
    {
      "areaSize" : [5, 5],
      "startingPosition" : [1, 2],
      "oilPatches" : [
        [1, 0],
        [2, 2],
        [2, 3]
      ],
      "navigationInstructions" : "NNESEESWNWW"
    }
    ```
  * Output from above yields:
    ```json
    {
      "finalPosition" : [1, 3],
      "oilPatchesCleaned" : 1
    }
    ```
### Additional Information
The application contains unit tests written in Spock, a testing framework for Java and Groovy. By default, the test results compile with the rest of the application, but you can view the test results alone by running `mvn test`, or by running them in an IDE of your choice.