# Software Testing Lab

[![example workflow](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml/badge.svg)](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml)

The Book Shop is an application that manages books!

The project is a CRUD web application with Spring Boot.

As part of The Software Testing Lab, We will be performing four levels of tests :

1. Unit Tests
2. Integration Tests
3. System Tests
4. Acceptance Tests

## Step 1 : Unit Tests

REST APIs are usually rigorously tested during integration testing. However, a good developer should test REST endpoints even before integration in their Unit Tests, since they are a vital part of the code since it's the sole access point of every entity wanting to make use of the services in the server.

I used  Mockito to mock the book repository that is used in Unit Testing.

You can run the tests with 

```
mvn clean test
```
