package com.maxeagan;

import com.google.gson.GsonBuilder;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class StringUtil {

    // Apply Sha256 to a string and return result.
    public static String applySha256(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Appies sha256 algorithm to out input
            byte[] hash = messageDigest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // Contain hash as a hexadecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Apply ECDSA signature and returns the result as bytes
    public static byte[] applyECSASig(PrivateKey privateKey, String input){
        Signature dsa;
        byte[] output = new byte[0];

        try{
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    // Method that verifies the signature
    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature){
        try{
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Short hand helper to turn Object into a json string
    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }

    //Returns difficulty string target, to compare to hash.
    // E.g, difficulty of 5 will return "00000"
    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static String getStringFromKey(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // Calculates the Merkle Root from a list of transactions
    public static String getMerkleRoot(ArrayList<Transaction> transactions) {
        // Create initial list of transaction IDs (leaf nodes)
        ArrayList<String> previousTreeLayer = new ArrayList<>();
        for (Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.transactionId);
        }

        // Continue hashing pairs of elements until one root hash remains
        while (previousTreeLayer.size() > 1) {
            ArrayList<String> treeLayer = new ArrayList<>();

            // Go through the current layer two items at a time
            for (int i = 0; i < previousTreeLayer.size(); i += 2) {
                String left = previousTreeLayer.get(i);

                // If there's an odd number of elements, duplicate the last one
                String right = (i + 1 < previousTreeLayer.size()) ? previousTreeLayer.get(i + 1) : left;

                // Hash the concatenated pair and add it to the new layer
                String combinedHash = applySha256(left + right);
                treeLayer.add(combinedHash);
            }

            // Move up one level in the tree
            previousTreeLayer = treeLayer;
        }

        // Final remaining hash is the Merkle Root
        return (previousTreeLayer.size() == 1) ? previousTreeLayer.get(0) : "";
    }
}

