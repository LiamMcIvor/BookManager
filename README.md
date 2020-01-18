# BookManager

In fulfilment of the solo project assignment due Monday 20/01/2020 at QA Consulting.

## Index
[Overview](#overview)
   * [My Product](#product)

[Architecture](#architecture)
   * [Entity Relationship Diagrams](#erd)
   * [Overall Architecture](#overall)

[Technologies](#tech)
   * [CI/CD Pipeline](#cicd)

[Testing And Code Review](#test)
   * [Reports](#reports)

[Front-End Design](#design)
   * [Wireframes](#wireframes)
   * [Actual Appearance](#finalLook)

[Project review](#review)
   * [Journey](#journey)
   * [Self-Reflection](#insight)
   * [Future Improvements](#future)

[Authors](#authors)

[Acknowledgements](#acks)

<a name="overview"></a>
## Overview

To design an application with a topic of my choice that - as a minimum - utilises CRUD (Create, Read, Update, Delete) functionality. This application should incorporate the core technologies and teachings covered in the training up to that point and access at least 2 database tables.

<a name="product"></a>
### My Product

For the product I took inspiration from my desire to effectively keep track of the books I am reading at any given time.

I want to be able to manage my entire collection and have a record of any I want to get in the future.


<a name="architecture"></a>
## Architecture

<a name="erd"></a>
### Entity Relationship Diagrams

![InitialERD](./documentation/InitialERD.png)
Initial ERD

![FinalERD](./documentation/FinalERD.png)
Final ERD

<a name="overall"></a>
### Overall Architecture

![Architecture](./documentation/Architecture.png)


<a name="tech"></a>
## Technologies

* H2 In-Memory Database
* Java - Business Logic
* Spring Boot - Opinionated Application Framework
* HTML5, CSS and Javascript - Front-End
* ![GitHub](https://github.com/lukecottenham/BookManager) - VCS
* ![Trello Board](https://trello.com/b/GmzuBWTd/book-manager) - Project Tracking
* Jenkins - CI Server
* Maven - Build Tool and Dependency Management
* JUnit and Mockito - Unit Testing
* Selenium - Front-End Testing
* SonarQube - Static Testing
* EclEmma and Surefire - Test Reporting
* AWS - Live Environment
* Tomcat - Deployment

<a name="cicd"></a>
### Continuous Integration/Continuous Development Pipeline

![Pipeline](./documentation/cicd.png)


<a name="test"></a>
## Testing And Code Review

<a name="reports"></a>
### Reports

![Surefire Report](./documentation/surefire-report.pdf)

The test coverage for the back-end is 88% and there are 4 separate Selenium tests that cover the workings of the CRUD functionality and the page navigation.

![Test Coverage](./documentation/testCoverage.png)


<a name="design"></a>
## Front-End Design

<a name="wireframes"></a>
### Wireframes

<a name="finalLook"></a>
### Actual Appearance


<a name="review"></a>
## Project review

<a name="journey"></a>
### Journey

<a name="insight"></a>
### Self-Reflection

<a name="future"></a>
### Future Improvements


<a name="authors"></a>
## Authors


<a name="acks"></a>
## Acknowledgements

* All the trainers that kindly helped me solve the bugs that would've kept me up at night.
* The rest of my cohort that helped me spot the tiny mistakes that somehow broke the whole application.




