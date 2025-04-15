package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.dto.UserDTO;
import br.com.compass.enums.AccountStatus;
import br.com.compass.enums.AccountType;
import br.com.compass.enums.TransactionsType;
import br.com.compass.models.*;
import br.com.compass.dao.TransactionDAO;
import br.com.compass.dao.TransfersDAO;
import br.com.compass.dao.RefundDAO;

import java.util.*;
import java.util.stream.Collectors;

public class AccountService {
    private final AccountDAO accountDAO;
    private final UserService userService;
    private final TransactionDAO transactionDAO;
    private final TransfersDAO transfersDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userService = new UserService();
        this.transactionDAO = new TransactionDAO();
        this.transfersDAO = new TransfersDAO();
    }

    public void createAccount(UserDTO userDTO, String accountType, boolean userAlreadyExists) {
        Users user = null;
        if (userAlreadyExists){
            user = userService.takeUserInformationByEmail(userDTO.email());
        }else{
            user = userService.createUser(userDTO);
            if (user == null) {
                throw new RuntimeException("User already exists, use another email or login for creating bank account.");
            }
        }

        if (userService.validateAccountType(userDTO.email(), accountType)){
            throw new RuntimeException("Email already registered for type: "+accountType+" account.");
        }

        Account account = new Account();
        account.setUserId(user.getId());
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
        return accountDAO.findByUserIDAllAccounts(idUser);
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
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
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

    public void saveTransaction(Account account, double amount, String transactionType, Long transfersIdSave) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransfersId(transfersIdSave);
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

    public void showTransactionsChargeback(Long idUser){
        List<Transaction> transactions = transactionDAO.findByAllTransactionIdChargeback(idUser);

        if(transactions == null){
            throw new RuntimeException("No transactions chargeback for user.");
        }

        for (Transaction transaction : transactions) {
            System.out.println("| ID: " + transaction.getId()
                    + " | TRANSACTION TYPE: " + transaction.getTransactionType()
                    + " | AMOUNT: " + transaction.getAmount());
        }
    }

    public String generateCSVTransactions(Long idUser) {

        List<Transaction> transactions = transactionDAO.findByAllTransactionID(idUser);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for user: "+idUser);
        }
        StringBuilder csv = new StringBuilder();

        csv.append("id;account_id;transaction_type;amount;date;transfers_id").append("\n");

        for (Transaction transaction : transactions) {

            csv.append(transaction.getId()).append(";")
                    .append(transaction.getAccountId()).append(";")
                    .append(transaction.getTransactionType()).append(";")
                    .append(transaction.getAmount()).append(";")
                    .append(transaction.getTransactionDate()).append(";")
                    .append(transaction.getTransfersId())
                    .append("\n");
        }

        return csv.toString();
    }
}
