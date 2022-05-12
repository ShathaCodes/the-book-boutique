# Software Testing Lab

[![example workflow](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml/badge.svg)](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml)

The Book Shop is an application that manages books!

The project is a CRUD web application with Spring Boot.

*** 
As part of DevOps lab, I built a CI/Cd pipeline using Github actions.

## Step 1 : Build and Test

Build and Test the application with maven. I saved the generated jar file to use it later.

## Step 2 : Package

Package the application into a docker image using the generated jar file and push it to DockerHub.

## Step 3 : Deploy

Todo

***

As part of The Software Testing Lab, We will be performing four levels of tests :

1. Unit Tests
2. Integration Tests
3. System Tests
4. Acceptance Tests

You can view the status of the tests in the pipeline or run the tests manually with 

```
mvn clean test
```

## Step 1 : Unit Tests

In this step, I focused on testing the different actions (functions) in the BookController.

I used Mockito to mock the book repository that is used in Unit Testing. That way, we can make sure we are trully testing the functionalities of the actions themselves.

## Step 2 : Integration Tests

Here we test the integration of different parts of the system.

I will be using a seperate database for testing so that it won't affect the actual Database. For that, I used the Testcontainers Library.

## Step 3 : System Tests
