package com.maxeagan;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main (String[] args) {
        // Creating an arraylist to store our blocks.
        ArrayList<Block> blockchain = new ArrayList<Block>();

        // Add blocks to arraylist
        blockchain.add(new Block("First block", "0"));
        blockchain.add(new Block("Second block", blockchain.getLast().hash));
        blockchain.add(new Block("Third block", blockchain.getLast().hash));

        // Print our blocks in json
        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockChainJson);




    }
}
