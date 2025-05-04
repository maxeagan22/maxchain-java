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
    public Transaction(PublicKey sender, PublicKey recipient, float value, ArrayList<TransactionInput> inputs) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.inputs = inputs;
    }

    /*
     * This method calculates transaction hash
     * The hash will be used as the transaction id
     */
    private String calculateHash() {
        sequence++; // increase sequence to avoid transactions with the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        Float.toString(value) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);
        signature = StringUtil.applyECSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);

        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    // Returns true if a transaction can be created.
    public boolean processTransaction() {

        if (verifySignature() == false) {
            System.out.println("Transaction signature failed to verify");
            return false;
        }

        // Gather transaction inputs, making sure they are unspent)
        for (TransactionInput i : inputs) {
            i.UTXO = MaxChain.UTXOs.get(i.transactionOutputId);
        }

        // Check if transaction is valid
        if (getInputsValue() < MaxChain.minimumTransaction){
            System.out.println("Transaction inputs to small: " + getInputsValue());
            return false;
        }

        // Generate transaction outputs
        float leftOver = getInputsValue() - value; // Gets value of inputs then left over change
        transactionId = calculateHash();
        outputs.add(new TransactionOutput(this.recipient, value, transactionId)); // Send value to recipient
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); // Send leftover change back to sender

        // Add outputs to unspent list
        for (TransactionOutput o : outputs){
            MaxChain.UTXOs.put(o.id, o);
        }

        // Remove transaction inputs from UTXO lists as spent
        for (TransactionInput i : inputs){
            if(i.UTXO == null) continue; // Skip if transaction is not found
            MaxChain.UTXOs.remove(i.UTXO.id);
        }
        return true;

    }

    // Returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; // If transaction cant be found
            total += i.UTXO.value;
        }
        return total;
    }

    // Return sum of outputs
    public float getOutputsValue(){
        float total = 0;
        for(TransactionOutput o : outputs){
            total += o.value;
        }
        return total;
    }
}


