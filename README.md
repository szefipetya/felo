## Users api endpoint testing application

### What this project is about?

- Makes HTTP GET calls to the https://jsonplaceholder.typicode.com/users endpoint
- Saves the received Users to the database
- Validates the emails of all users, saving the results to the database
- Tests the endpoint 10 times to make sure it works properly
- The runtime informations are saved to the ```spring.log``` file.

### Prerequisites

- Working postgreSQL server on port 8102
    - You may configure this to yourself in the application.properties file
- Java 11

### Build

- mvn install -DskipTests=true

### Run

- Start your postgreSQL server (replace x with your version)
    - windows: ``` pg_ctl -D "C:\Program Files\PostgreSQL\x\data" start```
    - linux: ```sudo service postgresql start```
- Run the application
    - Navigate to the ```/target``` folder
    - Run ```java -jar felo-0.0.1-SNAPSHOT.jar ```

