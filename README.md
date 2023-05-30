README - Pet Shop Application

This project is implemented using the Spring framework.
Getting Started

To start using the application, follow the instructions below.
Prerequisites

Make sure you have the following installed:

    Java 17
    PostgreSQL
    Docker (optional, only required if using the Docker container)

Setting Up the Database

If you're using PostgreSQL directly, create a database according to the configuration in the docker-compose-postgres.yml file. Alternatively, if you're using the Docker container, follow these steps:

    Install DBeaver or any other PostgreSQL client.
    Start the Docker container by running the command docker-compose -f docker-compose-postgres.yml up.

Running the Application

To run the application, perform the following steps:

    Clone the repository to your local machine.
    Open the project in your preferred Java IDE.
    Configure the database connection in the application.yml file, if necessary.
    Build and run the project.

Accessing the Actuator

The Actuator is configured to provide information and metrics about the application. You can access it using the following URLs:

    General information: http://localhost:8081/actuator
    Application status: http://localhost:8081/actuator/health
    Detailed endpoint information: http://localhost:8081/actuator/info
    Application metrics: http://localhost:8081/actuator/metrics

Accessing the Swagger Documentation

The application provides Swagger documentation for the APIs. To access it, follow these steps:

    Start the application.
    Open your web browser and navigate to http://localhost:8080/swagger-ui/index.html#/.

Running Unit Tests

The project includes unit tests to ensure the correctness of the implemented functionality. You can run the unit tests using your IDE or by running the appropriate Gradle/Maven command.
Contributing

If you'd like to contribute to this project, please follow these guidelines:

    Fork the repository.
    Create a new branch for your feature or bug fix.
    Commit your changes with descriptive commit messages.
    Push your changes to your forked repository.
    Submit a pull request to the main repository.

Please ensure that your code follows the established coding style and that you have added appropriate tests for your changes.

Acknowledgments

We would like to acknowledge the following resources and frameworks used in this project:

    Spring Framework
    PostgreSQL
    Docker
    Swagger
