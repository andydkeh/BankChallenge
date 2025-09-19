package br.com.andydkeh.controller;


import br.com.andydkeh.service.TransactionService;


public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController() {
        this.transactionService = new TransactionService();
    }

    public void requestRefund(Long transactionId){
        try{
            transactionService.requestRefund(transactionId);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void showRefunds(){
        try{
            transactionService.showRefunds();
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void makeRefund(Long refundId){
        try{
            transactionService.makeRefund(refundId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}