# Pilotes Management
Manage Pilotes orders using RESTful APIs.

## Overview
This is a Spring Boot application for managing Pilotes orders, released under the main branch. It is Dockerized for easy deployment and includes a Docker Compose configuration for running the application with an H2 in-memory database.

## Building and Running the Application
### Docker
1. Build the Docker Image

Ensure you are in the directory with the Dockerfile, then run:

```bash
docker build -t my-spring-boot-pilotes-app .
```
2. Run with Docker Compose

Ensure you have the docker-compose-server-h2.yml file, then start the application using:

```bash
docker-compose -f docker-compose-server-h2.yml up -d
```
## Local Setup
1. Run the Application

To run the application without Docker, execute the SpringBootH2Application class located in the root of the source directory. You can do this from your IDE or via the command line:

```bash
./mvnw spring-boot:run
```

## Testing
### Initial Data Setup
To test the application, you can use the SOAP UI project located in the soap-ui folder. Start with the pilotes-add-customer-api-soapui-project.xml file to save initial data into the H2 in-memory database.

### Generating JWT Tokens
There is a utility API available for generating JWT tokens. These tokens are required for authorizing requests to the getter APIs.

## Notes
The application includes a comprehensive suite of tests for various cases.
Ensure that Docker and Docker Compose are installed on your machine to use the Docker setup.

## Troubleshooting
If you encounter issues:

* Verify that Docker and Docker Compose are running correctly.

* Check the logs of your Docker containers for any errors using:

```bash
docker-compose logs
```
Ensure that the SpringBootH2Application class is correctly configured and runs without errors if running locally.

## Contact
For further assistance, please reach out to the felipe.ariasfarfan@gmail.com.