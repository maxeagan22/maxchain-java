package com.maxeagan;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // Data will be a simple message.
    private long timeStamp; // Number of milliseceonds since 1/1/1970
    private int nonce;

    /*
        Block constructor
        String hash will store digital signature
        previousHash will hold the previous block's hash
     */
    public Block (String previousHash){
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash();
    }

    // Calculate a new hash based on blocks content.
    public String calculateHash(){
        // Apply sha256
        String calculatedHash = StringUtil.applySha256(
                    previousHash +
                            timeStamp +
                            Integer.toString(nonce) +
                            merkleRoot
                            );
        return calculatedHash;
    }

    // Increase none value until hash target is reached.
    public void mineBlock(int difficulty){
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDifficultyString(difficulty); // Create a string with difficult * "0".

        while(!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined : " + hash);
    }

}
