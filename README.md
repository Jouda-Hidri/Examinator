# EXAMINATOR 

This is a Spring MVC project 
where Maven is used as a Build Management Tool 
and JPA as an ORM Standard.

This is a web application that simulates an exam 
where the user can answer multiple choice questions 
and see the result.

## What is this repository for? 

In this respository you can see the implementation of different features of the project.

### Quick summary
On this exam simulator the user can   
-Create their own exam,   
-See the list of available exams,   
-Have an exam and then see the result   
(a new exam instance will be created and saved as an "evaluation"),   
-See the list of evaluations,   
-See the result of an accomplished evaluation,   
-Resume an unaccomplished evaluation.


## How do I set it up? 

### Set up
Maven is the build tool used for this project.   
You should have Maven installed on your computer.   
Download and import this project as a maven project into your editor.   
Then run the project on the server.   
You can also install pgAdmin as a user interface for the database.   

### Dependencies
-Java 1.8,   
-Spring MVC 4.3.4.RELEASE,   
-Hibernate 4,   
-JPA 2.0,   
-PostgreSQL 9.4-1200-jdbc41.   

### Database Configuration
-Install pgadmin,   
-Create a new database,   
-Go to examinator / src / main / ressources / META-INF / persistence.xml,   
-Update database name (examinatordb), username (postgres) and password (admin)   
based on what you chose during the installation of pgAdmin.
````
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/examinatordb" />   
<property name="javax.persistence.jdbc.user" value="postgres" /> <!-- DB User -->   
<property name="javax.persistence.jdbc.password" value="admin" /> <!-- DB Password -->
````

## Who do I talk to?

### Repo Owner
Jouda Hidri   
mail me on hidrijouda@gmail.com
