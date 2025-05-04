package com.maxeagan;

public class TransactionInput {
    public String transactionOutputId; // Reference to Transactionoutputs -> transactionID
    public TransactionOutput UTXO; // Contains the unspent transaction output

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
