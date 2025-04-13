package br.com.compass;

import br.com.compass.controller.AccountController;
import br.com.compass.controller.UserController;
import br.com.compass.enums.AccountType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountController accountController = new AccountController();
        UserController userController = new UserController();

        mainMenu(scanner, accountController, userController);
        
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner, AccountController accountController, UserController userController) {
        boolean running = true;

        userController.createAdministrator();

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Write your email address");
                    String emailLogin = scanner.next();
                    System.out.println("Write your password");
                    String passwordLogin = scanner.next();
                    List<Long> ids = userController.loginValidation(emailLogin, passwordLogin);

                    if (ids == null) {
                        break;
                    } else {
                        switch (userController.validateScreenByUser(ids)){
                            case "MANAGER":
                               // bankMenuManager(scanner);
                            case "ADMINISTRATOR":
                               // bankMenuAdminstrator(scanner, );
                            case "COMMON_USER":
                                //bankMenuCommonUser(scanner, accountController);
                        }
                    }

                case 2:
                    System.out.println("Write your email address:");
                    String email = scanner.next();

                    System.out.println("Write your password:");
                    String password = scanner.next();
                    scanner.nextLine();

                    System.out.println("Write your complete name");
                    String name = scanner.nextLine();

                    System.out.println("Choose the number of an account type:");
                    for (AccountType type : AccountType.values()) {
                        System.out.println(type.ordinal() + ". " + type.name());
                    }
                    Integer accountType = scanner.nextInt();
                    String accountTypeName = AccountType.values()[accountType].name();

                    System.out.println("Write your birth date (format dd/MM/yyyy)");
                    String birthDate = scanner.next();
                    Date birthDateFormat;
                    try {
                        SimpleDateFormat bdf = new SimpleDateFormat("dd/MM/yyyy");
                        birthDateFormat = bdf.parse(birthDate);
                    } catch (ParseException e) {
                        System.out.println("Erro: " + e.getMessage());
                        throw new RuntimeException("Erro");
                    }

                    System.out.println("Write your CPF:");
                    String cpf = scanner.next();
                    cpf = cpf.replaceAll("[^\\d]", "");
                    System.out.println("Write your phone number:");
                    String phone = scanner.next();
                    phone = phone.replaceAll("[^\\d]", "");

                    accountController.createAccount(name, birthDateFormat, cpf, phone, accountTypeName, password, email);

                    System.out.println("Account created successfully");
                    System.out.println("----------------------------");
                   // bankMenuCommonUser(scanner, accountController);

                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public void bankMenuCommonUser(Scanner scanner, AccountController accountController) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    Long accountId = Long.valueOf(123123); //remover dps
                    System.out.print("Enter the deposit amount: ");
                    double amount = scanner.nextDouble();
                    accountController.deposit(accountId, amount);
                    break;
                case 2:
                    // ToDo...
                    System.out.println("Withdraw.");
                    break;
                case 3:
                    Long accountIdTest = Long.valueOf(123123); //remover dps
                    accountController.checkBalance(accountIdTest);
                    break;
                case 4:
                    // ToDo...
                    System.out.println("Transfer.");
                    break;
                case 5:
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public void bankMenuManager(Scanner scanner) {
    }
    public void bankMenuAdministrator() {
        boolean running = true;
    }
}
