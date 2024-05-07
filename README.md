# Employee Management System

Designed by N. W. L. U. R. D. Nanayakkara  (Umendya "mendydias" Dias)
Purpose: MP for EEY5466 

## Introduction

### Overview

This document describes the overall design draft for the Employee Management System. It covers basic information like functional requirements for a few use cases, diagrams to further illustrate how the various parts of system work together, and the structure of the database.

### Product overview

This project concerns a basic employee management system. This is not production ready, this is just a proof of concept.

## Use cases

1. As an employee, I should be able to register so that I can participate in projects.

2. As an employee, I should be able to deregister, so that I can remove myself from a project.

3. As the head of a department, I should be able to assign an employee to a department so that I can delegate tasks to them.

4. As an employee, I should be able to request to work on a project so that I can contribute to projects that I like.

5. As the head of the department, I should be able to read and react to employee work requests.

6. As the head of the department, I should be able to assign an employee to a project so that the project can make progress.

7. As the head of the department, I should be able to see a report on the number of employees in my department so that I can make sound business decisions.

8. As the head of the department, I should be able to see a report on the projects that my department is involved in so that I can see how many of my employees are working on whcih projects.

## Identified Stakeholders

- Employee
- Project
- Department
    - Head of the department

## Functional Requirements

1. The system shall provide basic crud facilities for the identified entities.

2. The system shall generate basic reports and views on the data.

## Entity Relationship Diagram

@startuml
@startchen

entity Employee {

}

entity Department {

}

entity Project {

}

@endchen
@enduml
