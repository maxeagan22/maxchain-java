package com.maxeagan;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String data; // Data will be a simple message.
    private long timeStamp; // Number of milliseceonds since 1/1/1970

    /*
        Block constructor
        String hash will store digital signature
        previousHash will hold the previous block's hash
     */
    public Block (String data, String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        // Apply sha256
        String calculatedHash = StringUtil.applySha256(
                    previousHash +
                            timeStamp +
                            data
                            );
        return calculatedHash;
    }

}
