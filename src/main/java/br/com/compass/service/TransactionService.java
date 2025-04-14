package br.com.compass.service;

import br.com.compass.dao.TransactionDAO;
import br.com.compass.models.Users;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }


}
