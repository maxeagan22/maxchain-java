package com.maxeagan;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    // Only UTXO owned by this wallet
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public Wallet(){
        generateKeyPair();
    }

    public void generateKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = new SecureRandom().getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            // Initialize the key generator and key pair
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            // Set the public and private keys from the key pair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Return balance and store the UTXO's owned by this wallet in this.UTXOs
    public float getBalance(){
        float total = 0;

        for (Map.Entry<String, TransactionOutput> item : MaxChain.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
                UTXOs.put(UTXO.id, UTXO); // Add to list of unspent transactions.
                total += UTXO.value;
            }
        }
        return total;
    }

    // Generate and return a new transaction from this wallet.
    public Transaction sendFunds(PublicKey recipient, float value){
        //Gather balance and check funds.
        if(getBalance() < value){
            System.out.println("Not enough funds to send transaction.");
            return null;
        }
        // Create array list of inputs
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value ) break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput i : inputs){
            UTXOs.remove(i.transactionOutputId);
        }
        return newTransaction;
    }
}
