[![Build Status](https://travis-ci.org/empowerhack/hub.svg?branch=dev)](https://travis-ci.org/empowerhack/hub)

| Environment | Url | Comments |
| ----------- | --- | -------- |
| Production  | hub.empowerhack.io | Data is kept |
| Development | dev.hub.empowerhack.io | Data is NOT kept between deployments |

# EmpowerHack Hub

Family Members, Project list, Calendar etc...

Please read [Contribution](CONTRIBUTING.md) documentation

---

### Development setup

Using **[Spring boot](http://projects.spring.io/spring-boot/)** with **Maven**

Requirements

* [Java v1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven v3+](https://maven.apache.org/download.cgi)
* Open the project in a maven supporting IDE (Intellij by Jetbrains highly recommended)

more to follow

### Project structure

Standard **Spring boot** structure.

* `pom.xml` project dependencies
* `src/main/java` java source files
* `src/main/resources/static` static files (css, javascript etc)
* `src/main/resources/templates` html files
* `src/test` test files



### Feature development

* For each feature, we will iterate through the following tasks:
`Prototype > Wireframes > Implement Wireframes > Design > Implement Design > Done`


* Reviews will occur at each step of the process `(Task > Pull Request > Review > Commit)`


* **NOTE:** The Prototype branch is meant as a proof of concept and  must not (ever!) be merged into mainstream 

![empowerhack-hub](https://cloud.githubusercontent.com/assets/624760/14483645/178fe2ba-0141-11e6-87e3-850e6d841679.gif)
