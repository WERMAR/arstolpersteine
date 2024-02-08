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


### Curl Keylcoak on Linux Server
```bash 
      
      curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic YmFja2VuZC1jbGllbnQ6dGx6UmlFUmdjQ2VweEVRaVh5cjJpUGM1R3B3Q2pvR3c=" \
  -H "User-Agent: PostmanRuntime/7.36.0" \
  -H "Accept: */*" \
  -H "Cache-Control: no-cache" \
  -H "Postman-Token: 2517ff5e-1e29-4592-96af-0301c9f7126b" \
  -H "Host: 10.100.4.1:5432" \
  -H "Accept-Encoding: gzip, deflate, br" \
  -H "Connection: keep-alive" \
  -d "grant_type=password&username=test-user&password=password" \
  http://localhost:5432/realms/arStolpersteine/protocol/openid-connect/token > token.txt
```

### Setup Information's for Prod / Local

1. Default.conf
   
The default.conf has to be modified to the server target api server-address. The default.conf is used to configure the nginx-proxy. 
```text
 proxy_pass http://<target-address>/api/;
```

2. application.yml

The Application.yml has to be modified on to that address, which reaches keycloak in prod. 

Example: Keycloak is located at 10.100.4.1:5432
```yaml
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: ${backend.keycloak.base}/certs
          issuer-uri: http://10.100.4.1:5432/realms/${backend.keycloak.realm}
```

3. keycloak-init.factory.ts

Here also the production url of keycloak has to be written down.

```ts
    ...
    config: {
        url: 'http://<your-keycloak-address>:<keycloak-port>',
...
```

4. Running on different Environments

Towards the system is running on docker you can complete setup the runtime in every environment. For that using the Docker-Files to build your own image for your target. Currently there are the Images "wermar/arproject-be" and "wermar/arproject-fe". Both have an Tag "linux" and Tag "latest". 

- linux -> Build for Linux Distributions with amd64 Arch
- latest -> Build for Mac Distributions with the arm64 Arch

> Note 
>
> Modify the docker-compose file if you want to use a different Image on your target environment.


