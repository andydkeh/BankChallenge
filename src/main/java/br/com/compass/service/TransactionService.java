package br.com.compass.service;

import br.com.compass.dao.*;
import br.com.compass.enums.RefundStatus;
import br.com.compass.enums.TransactionsType;
import br.com.compass.models.*;
import br.com.compass.dao.AccountDAO;

import java.util.List;
import java.util.Objects;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final RefundDAO refundDAO;
    private final AccountDAO accountDAO;
    private final TransfersDAO transfersDAO;


    public TransactionService() {
        this.accountDAO = new AccountDAO();
        this.transfersDAO = new TransfersDAO();
        this.transactionDAO = new TransactionDAO();
        this.refundDAO = new RefundDAO();
    }

    public void requestRefund(Long transactionId){
        RefundRequest refund = new RefundRequest();
        refund.setTransactionId(transactionId);
        refund.setStatus(RefundStatus.PENDING.name());
        refundDAO.save(refund);

        System.out.println("Refund sent for approval/rejection.");
    }

    public void showRefunds(){
        List<RefundRequest> refunds = refundDAO.findByAllRefundPending();

        if (refunds == null){
            System.out.print("No refund pending.");
            throw new RuntimeException("No refund pending.");
        }

        for (RefundRequest refund : refunds){
        System.out.println("| ID: "+refund.getId()
                + " | REQUEST DATE: "+refund.getRequestDate()
                + " | TRANSACTION ID: " + refund.getTransactionId()
                + " | STATUS: "+refund.getStatus());
        }
    }

    public void makeRefund(Long refundId){
        RefundRequest refundResult = refundDAO.findById(refundId);
        Transaction transaction = transactionDAO.findById(refundResult.getTransactionId());

        if (transaction == null){
            throw new RuntimeException("Transaction not found.");
        }

        Account account = accountDAO.findById(transaction.getAccountId());

        if (Objects.equals(transaction.getTransactionType(), TransactionsType.DEPOSITS.name())){

            var balanceAccount = account.getBalance();

            if (balanceAccount < transaction.getAmount()){
                rejectRefund(refundResult);
                throw new RuntimeException("Amount not enough to refund.");
            }else if (balanceAccount >= transaction.getAmount()){
                account.setBalance(balanceAccount - transaction.getAmount());
                accountDAO.save(account);

                acceptRefund(refundResult);
                System.out.println("Refund accepted.");
            }
        }else if (Objects.equals(transaction.getTransactionType(), TransactionsType.TRANSFERS.name())){
            Long idTransfersTransaction = Long.valueOf(transaction.getTransfersId());
            Transfers transfer = transfersDAO.findByTransfersID(idTransfersTransaction);

            Account accountDestination = accountDAO.findById(transfer.getDestinationAccountId());

            if (accountDestination.getBalance() < transaction.getAmount()){
                rejectRefund(refundResult);
                throw new RuntimeException("Amount not enough to refund.");
            }

            Account accountSource = accountDAO.findById(transfer.getSourceAccountId());

            accountDestination.setBalance(accountDestination.getBalance() - transaction.getAmount());
            accountDAO.save(accountDestination);

            accountSource.setBalance(accountSource.getBalance() + transaction.getAmount());
            accountDAO.save(accountSource);

            acceptRefund(refundResult);
            System.out.println("Refund accepted.");
        }
    }

    public void acceptRefund(RefundRequest refund){
        refund.setStatus(RefundStatus.APPROVED.name());
        refundDAO.save(refund);
    }

    public void rejectRefund(RefundRequest refund){
        refund.setStatus(RefundStatus.REJECTED.name());
        refundDAO.save(refund);
    }
}
