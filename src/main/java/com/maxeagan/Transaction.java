package com.maxeagan;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId; // hash of the transaction.
    public PublicKey sender; // senders address/public key.
    public PublicKey recipient; // recipients address/public key.
    public float value;
    public byte[] signature; // digital signature to prevent anyone else from spending from the wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // rough count of how many transactions

    // Constructor
    public Transaction(PublicKey sender, PublicKey recipient, float value, ArrayList<TransactionInput> inputs){
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.inputs = inputs;
    }

    /*
        * This method calculates transaction hash
        * The hash will be used as the transaction id
     */
    private String calculateHash(){
        sequence++; // increase sequence to avoid transactions with the same hash
        //return StringUtil.applySha256()
    }

}
