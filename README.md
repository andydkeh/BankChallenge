# BankChallenge

Banking system with essential features: account opening, login, balance check, deposits, transfers, transaction reversals, statement viewing and report export.

## Features

- Login system
- Open new accounts
- Check balance
- Password recovery
- Deposits
- Withdrawals
- Transfers between accounts
- Transaction history
- Statement generation
- Password encryption
- Access control
- Transaction validation

## Technologies

- Java 21
- PostgreSQL
- JPA/Hibernate
- Maven

## Compilation

To run the project, it will be necessary to compile:
```bash
  mvn clean install
```

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