package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.enums.AccountStatus;
import br.com.compass.enums.AccountType;
import br.com.compass.enums.TransactionsType;
import br.com.compass.models.Account;
import br.com.compass.dao.TransactionDAO;
import br.com.compass.models.Transaction;
import br.com.compass.dao.UserDAO;
import br.com.compass.models.Transfers;
import br.com.compass.dao.TransfersDAO;

import java.util.*;
import java.util.stream.Collectors;


public class AccountService {
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;
    private final UserService userService;
    private final TransactionDAO transactionDAO;
    private final TransfersDAO transfersDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userDAO = new UserDAO();
        this.userService = new UserService();
        this.transactionDAO = new TransactionDAO();
        this.transfersDAO = new TransfersDAO();
    }

    public void createAccount(String name, Date birthDate, String cpf, String phone, String accountType, String password, String email, String role) {
        var user = userService.createUser(name, birthDate, cpf, phone, password, email, role);

        if (user == null) {
            throw new RuntimeException("User already exists, use another email or login for creating bank account.");
        }
        if (userService.validateAccountType(email, accountType)){
            throw new RuntimeException("Email already registered for type: "+accountType+" account.");
        }

        Long idUserAccount = user.getId();
        Account account = new Account();
        account.setUserId(idUserAccount);
        account.setAccountType(accountType);
        account.setStatus(AccountStatus.ACTIVE.name());
        accountDAO.save(account);

        System.out.println("Account created successfully");
    }

    public List<String> validateTypeAccountCreate(Long idUser) {
        List<Account> userAccounts = showAccounts(idUser);

        Set<String> accountTypesUser = userAccounts.stream()
                .map(Account::getAccountType)
                .collect(Collectors.toSet());

        List<String> availableAccountTypes = Arrays.stream(AccountType.values())
                .filter(type -> !accountTypesUser.contains(type))
                .map(Enum::name)
                .collect(Collectors.toList());


        if (availableAccountTypes.isEmpty()) {
            System.out.println("No account types available for user: "+idUser);
            throw new RuntimeException("No account types available for user.");
        } else {
            return availableAccountTypes;
        }
    }

    public List<Account> showAccounts(Long idUser){
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountDAO.findById(idUser));

        return accounts;
    }

    public Account showAccountById(Long idAccount) {
        return accountDAO.findById(idAccount);
    }

    public void checkBalance(Long accountId) {
        System.out.println("Current balance: R$ " + accountDAO.findById(accountId).getBalance());
    }

    public void deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
        accountDAO.save(account);

        saveTransaction(account, amount, TransactionsType.DEPOSITS.name(), null);
        bankStatement(account, amount, TransactionsType.DEPOSITS.name(), null);

        System.out.println("Account deposited successfully.");
    }

    public void withdraw(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountDAO.save(account);

        saveTransaction(account, amount, TransactionsType.WITHDRAWAL.name(), null);
        bankStatement(account, amount, TransactionsType.WITHDRAWAL.name(), null);

        System.out.println("Account withdrawn successfully.");
    }

    public void transfer(Account fromAccount, Long toAccountId, double amount) {

        if (fromAccount.getBalance() < amount || amount <= 0) {
            throw new RuntimeException("Insufficient balance");
        }

        if (accountDAO.findById(toAccountId) == null) {
            throw new RuntimeException("Target account not found.");
        }

        Account toAccount = accountDAO.findById(toAccountId);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountDAO.save(fromAccount);
        accountDAO.save(toAccount);

        Transfers transfers = new Transfers();
        transfers.setAmount(amount);
        transfers.setSourceAccountId(fromAccount.getId());
        transfers.setDestinationAccountId(toAccount.getId());
        transfersDAO.save(transfers);

        //owner
        saveTransaction(fromAccount, amount, TransactionsType.TRANSFERS.name(), transfers.getId());

        //to
        saveTransaction(toAccount, amount, TransactionsType.TRANSFERS.name(), transfers.getId());

        bankStatement(fromAccount, amount, TransactionsType.TRANSFERS.name(), toAccount.getId());
    }

    public void saveTransaction(Account account, double amount, String transactionType, Long transfersId) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transactionDAO.save(transaction);
    }

    public void bankStatement(Account account, double amount, String transactionType, Long transfersId) {
        System.out.println("=== Bank Statement ===");
        System.out.println("| Amount: " + amount);
        System.out.println("| Type: " + transactionType);
        System.out.println("| Account ID: " + account.getId());
        if (transfersId != null) {
            System.out.println("| Target Account ID: " + transfersId);
        }
        System.out.println("======================");
    }
}
