# Software Testing Lab

[![example workflow](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml/badge.svg)](https://github.com/ShathaCodes/SoftwareTestingLab/actions/workflows/maven.yml)

The Book Shop is an application that manages books!

The project has a basic Frontend/Backend application with basic CRUD features. The main focus is to apply all different test levels as well as create a CI/CD pipeline for the project.

## DevOps Lab

As part of The DevOps lab, I built a CI/Cd pipeline using Github actions.

### Step 1 : Build and Test

1.  Build and Test the backend with maven and save the generated jar file to use it later.
2.  Build the Frontend with maven as well and save the generated jar file to use it later.

### Step 2 : Package

Package both the frontend and the backend seperatly into two docker images using the generated jar files and push them to DockerHub.

The images are tagged with The commit SHA. 

### Step 3 : End to End Tests

Create a ``.env`` file with the commit SHA which will be used by the ``docker-compose.yml`` file to pull the newly pushed Docker images.

Build the cluster with docker-compose inside the Runner.

Run the Cypress tests and save the generated video file as an artifact.

### Step 4 : Deploy

Todo


## Software Testing Lab

As part of The Software Testing Lab, We will be performing four levels of tests :

1. Unit Tests
2. Integration Tests
3. System Tests
4. Acceptance Tests

You can view the status of the tests in the pipeline or run the tests manually.

### Step 1 : Unit Tests

In this step, I focused on testing the different actions (functions) in the BookController in the **Backend**.

I used Mockito to mock the calls for the book repository that is used in the Unit Tests. That way, we can make sure we are trully isolating the functions and testing only the functionalities of the controller's actions.

You can run the test manually with :
```
cd Back
mvn test
```

### Step 2 : Integration Tests

Here we test the integration of different parts of the Backend.

I will be using a seperate database for testing so that it won't affect the actual Database. 

For that, I used the **Testcontainers** Library. This Java library is used to create a lightweight, throwaway instance of MySQL database using a Docker image. The container can be controlled with Java code which is very convenient.

You can run the test manually with :
```
cd Back
mvn test
```

### Step 3 : System Tests

I used Cypress to do an End-to-End test for the whole application.

I have 3 different scenarios:

1.  Create a new book
2.  Edit a book
3.  Delete a book

You can run the test manually with :
```
cd e2e-tests
npm run cypress:run
```

### Step 4 : Acceptance Tests

I used a User Acceptance Test Template.
