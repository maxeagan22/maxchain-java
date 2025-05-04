package com.maxeagan;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class MaxChain {

    // Create an arraylist to store our blockchain
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    //List of all unspent transactions
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();

    public static int difficulty = 5;
    public static float minimumTransaction = 0.1f;
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main (String[] args) {
        // Setup bouncy castle as our security provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();

        // Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        // Test transaction from walletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        // verify the signature works and verify it from the public key
        System.out.println("Is signature verified?");
        System.out.println(transaction.verifySignature());
    }

    /*
       * Method to check the integrity of our blockchain
       * This method will loop thorough all the blocks in the chain
       * Checks and compares the hash variable is equal to the calculated hash
     */
    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;

        // Loop through the blockchain and check hashes
        for (int i = 1; i < blockchain.size(); i++ ){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            // Compare registered hash and calculated hash
            if (!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current hashes are not equal");
                return false;
            }

            // compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous hashes are not equal");
                return false;
            }
        }
        return true;

    }
}
