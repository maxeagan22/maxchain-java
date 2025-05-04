package com.maxeagan;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey recipient; // the new owner of the coins.
    public float value; // the amount of coins a person owns.
    public String parentTransactionId; // the id of the transaction this output was created in.

    // Constructor
    public TransactionOutput(PublicKey recipient, float value, String parentTransactionId){
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;

        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient)+ value + parentTransactionId);
    }

    // Method to check if coins belong to you
    public boolean isMine(PublicKey publicKey){
        return (publicKey == recipient);
    }
}
