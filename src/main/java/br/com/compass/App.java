package br.com.compass;

import br.com.compass.controller.AccountController;
import br.com.compass.controller.UserController;
import br.com.compass.enums.AccountType;
import br.com.compass.enums.RoleType;
import br.com.compass.models.Account;

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
                    Long idUser = userController.loginValidation(emailLogin, passwordLogin);

                    if (idUser == null) {
                        break;
                    } else {

                        if (Objects.equals(userController.validateScreenByUser(emailLogin), "COMMON_USER")){
                            List<Account> accounts = accountController.showAccountsUser(idUser);

                            System.out.println("|| Choose the account ID you want to log in to: ||");
                            for (Account account : accounts) {
                                System.out.println("|| "+account.getId()+". "+account.getAccountType()+" ||");
                            }
                            Long idAccount = scanner.nextLong();

                            bankMenuCommonUser(scanner, idAccount, accountController);
                            break;
                        } else{
                            //manager e adm
                            bankMenuManager(scanner, idUser, userController, emailLogin);
                            running = false;
                            break;
                        }
                    }

                case 2:
                    System.out.println("============ Account Opening =============");
                    System.out.println("|| 1. I already have a registered login ||");
                    System.out.println("|| 2. Create login                      ||");
                    System.out.println("|| 0. Exit                              ||");
                    System.out.println("==========================================");
                    System.out.print("Choose an option: ");

                    int optionCaseAccountOpening = scanner.nextInt();

                    switch (optionCaseAccountOpening) {
                        case 1:
                            System.out.println("Write your email address");
                            String emailLoginAccountOpening = scanner.next();
                            System.out.println("Write your password");
                            String passwordLoginAccountOpening = scanner.next();

                            Long idUserAccountOpening = userController.loginValidation(emailLoginAccountOpening, passwordLoginAccountOpening);

                            if (idUserAccountOpening == null) {
                                break;
                            }

                            if (Objects.equals(userController.validateScreenByUser(emailLoginAccountOpening), RoleType.COMMON_USER.name())){
                                var valuesScreenAccount = accountController.validateTypeAccountCreate(idUserAccountOpening);

                                if (valuesScreenAccount == null) {
                                    break;
                                }

                                for (String value : valuesScreenAccount){
                                    System.out.println(value);
                                }

                            }else{
                                System.out.print("This email is not authorized to create bank accounts.");
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
                            int accountType = scanner.nextInt();
                            String accountTypeName = AccountType.values()[accountType].name();

                            System.out.println("Write your birth date (dd/MM/yyyy)");
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

                            accountController.createAccount(name, birthDateFormat, cpf, phone, accountTypeName, password, email, RoleType.COMMON_USER.name());

                            // bankMenuCommonUser(scanner, accountController);
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid option! Please try again.");
                    }

                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenuCommonUser(Scanner scanner, Long idUser, AccountController accountController) {
        boolean running = true;

        Account account = accountController.showAccountById(idUser);

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Check Balance        ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Transfer             ||");
            System.out.println("|| 4. Chargeback           ||");
            System.out.println("|| 5. Bank Statement       ||");

            if (!Objects.equals(account.getAccountType(), AccountType.PAYROLL.name())) {
                System.out.println("|| 6. Deposit              ||");
            }
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Check Balance.");
                    accountController.checkBalance(idUser);
                    break;
                case 2:
                    System.out.println("Withdraw.");
                    System.out.print("Enter the withdrawal amount:");
                    double amount = scanner.nextDouble();
                    accountController.withdraw(account, amount);
                    break;
                case 3:
                    System.out.println("Transfer.");

                    System.out.print("Enter the amount you want to transfer:");
                    double amountTransfers = scanner.nextDouble();

                    System.out.print("Enter the target account ID:");
                    Long toId = scanner.nextLong();

                    accountController.transfer(account, toId, amountTransfers);

                    break;
                case 4:
                    System.out.println("Chargeback.");
                    // ToDo...
                    break;
                case 5:
                    System.out.println("Bank Statement.");
                    break;
                case 6:
                    System.out.println("Deposit.");
                    System.out.print("Enter the deposit amount:");
                    double amountDeposit = scanner.nextDouble();
                    accountController.deposit(account, amountDeposit);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenuManager(Scanner scanner, Long idManager, UserController userController, String email) {
        boolean running = true;

        while (running){
            System.out.println("========== Bank Menu ==========");
            System.out.println("|| 1. List blocked accounts  ||");
            System.out.println("|| 2. Unlock accounts        ||");

            if (Objects.equals(userController.validateScreenByUser(email), RoleType.ADMINISTRATOR.name())){
                System.out.println("|| 3. Create manager account ||");
            }

            System.out.println("|| 0. Exit                   ||");
            System.out.println("===============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    userController.showUsersBlock();
                    break;
                case 2:
                    System.out.println("Enter the ID of the user you want to unlock:");
                    Long id = Long.valueOf(scanner.next());
                    userController.unlockUser(id);
                    break;
                case 3:
                    if (!Objects.equals(userController.validateScreenByUser(email), RoleType.ADMINISTRATOR.name())){
                        System.out.println("Invalid option! Please try again.");
                    }

                    System.out.println("Enter the manager's email address:");
                    String emailManager = scanner.next();

                    System.out.println("Create a password for the account:");
                    String password = scanner.next();
                    scanner.nextLine();

                    System.out.println("Write the manager's full name:");
                    String name = scanner.nextLine();

                    System.out.println("Write the manager's date of birth (dd/MM/yyyy)");
                    String birthDate = scanner.next();
                    Date birthDateFormat;
                    try {
                        SimpleDateFormat bdf = new SimpleDateFormat("dd/MM/yyyy");
                        birthDateFormat = bdf.parse(birthDate);
                    } catch (ParseException e) {
                        System.out.println("Erro: " + e.getMessage());
                        throw new RuntimeException("Erro");
                    }

                    System.out.println("Write the manager's CPF:");
                    String cpf = scanner.next();
                    cpf = cpf.replaceAll("[^\\d]", "");

                    System.out.println("Write the manager's phone number:");
                    String phone = scanner.next();
                    phone = phone.replaceAll("[^\\d]", "");

                    userController.createUserManager(name, birthDateFormat, cpf, phone, password, emailManager, RoleType.MANAGER.name());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
