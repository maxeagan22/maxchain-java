package com.maxeagan;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Main {

    // Create an arraylist to store our blockchain
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static void main (String[] args) {
        // Add blocks to arraylist
        blockchain.add(new Block("First block", "0"));
        blockchain.add(new Block("Second block", blockchain.getLast().hash));
        blockchain.add(new Block("Third block", blockchain.getLast().hash));

        // Print our blocks in json
        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockChainJson);

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
        for (int i = 0; i < blockchain.size(); i++ ){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            // Compare registered hash and calculated hash
            if (!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current hashes are not equal");
                return false
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
