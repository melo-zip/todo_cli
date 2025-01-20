# todo_cli

## Project details
A simple Command-Line TO-DO-LIST

I've made this project to practice what i've learned about Java Database Connectivity (JDBC) API, I also decided to implement some testing practice in this project to familiarize myself with JUnit and Mockito.

## Running the PostgreSQL Service with Docker

This project uses Docker Compose to set up a PostgreSQL database.

### Prerequisites
- Docker and Docker Compose installed on your machine.

### Steps to Run
1. Clone this repository and navigate to the project directory:
   ```bash
   git clone git@github.com:melo-zip/todo_cli.git
   cd todo_cli/
   ```
2. Start the services with Docker Compose:
3.  ```bash
    docker-compose up -d
    ```
    
### PostgreSQL Database Creation

When you start the PostgreSQL service with Docker Compose, the container will automatically create the database specified in the `POSTGRES_DB` environment variable, which is `todo_db` in this case. This is done during the container's initialization.

The following environment variables are used to configure the PostgreSQL instance:
- **POSTGRES_USER**: `postgres` (the default PostgreSQL user)
- **POSTGRES_PASSWORD**: `root` (the password for the default user)
- **POSTGRES_DB**: `todo_db` (the name of the database to be created)
  
A `tasks` table will be created in the `todo_db` database during the initialization of the container. This table will store task data for the application.

The database data is persisted in the db-data volume, ensuring that your task data will remain intact even if the container is stopped or removed.

### Running the Application

The main entry point for this project is located in the `MenuController` class. To run the project, make sure all dependencies are set up, then run the `MenuController.main()` method.