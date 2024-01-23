# Disclaimer: Infrastructure - Directory

This directory could be handled all relevant Files for running the Instance from local towards Prod.

## Database

To Set up the Database for the connection, its necessary to add the User and also to create the Schema.
Therefore, execute the **inital-db-script.sql** to your Database and the base structure is created.


## Docker
For using the Application is necessary to start the Docker-Infrastructure. Therefore the docker-compose-File exists. Its starting a stack with all necessary docker containers including following systems:

- PostgreSQL-Database (for Keycloak)
- Keycloak (Authentication-Server)
- Application Server (Spring Boot Server) - currently not running inside the stack
- NGX (providing the FE)

#### Important Commands for Setup

- Startup the Docker for the first Time:

```bash 
docker-compose up -d
```

This command has to be executed within the infrastructure Folder. The _-d_-Operator is for closing the logs and run the docker-compose stack in the Background. 

- Shutdown the Stack:

```bash
docker-compose down
```

- Remove mounted Volumes
```bash
 docker volume rm infrastructure_postgres_data
```
This Command deletes the volumes of the postgresql database instance. __Please do this only when the Stack is not running__.
It's useful when a new configuration of the __realm.json__ is available and has to be imported by the Keycloak. Its remove all existing Data inside the Database. So the Database will be empty before the next Keycloak-Startup. 


### Connect psql inside docker postgres on THWS Server
1. ```docker exec -it <docker container name> bash```
2. ```psql -h localhost -p 5432 -U keycloak```
