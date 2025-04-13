# Banking System

## Initial Setup

Before running the system, you need to configure the configuration files:

1. Copy the configuration example files:
   ```bash
   cp src/main/resources/config.properties-example src/main/resources/config.properties
   cp src/main/resources/META-INF/persistence.xml-example src/main/resources/META-INF/persistence.xml
   ```

2. Edit the configuration files with your settings:
   - `config.properties`: Set administrator credentials and other settings
   - `persistence.xml`: Configure your database connection

3. Make sure both files are in `.gitignore` to avoid versioning your credentials

## Running the System

1. Set up the database according to the settings in `persistence.xml`
2. Run the `App.java` class
3. The system will automatically create the initial administrator if it doesn't exist

### To run the project, it will be necessary to compile
`mvn clean install`

!!!!!!!!!!!!!!!!! Never share the configuration files with your real credentials !!!!!!!!!!!!!!!!!
