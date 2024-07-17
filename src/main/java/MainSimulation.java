import java.security.*;
import java.util.Base64;

import utils.KeyUtils;

public class MainSimulation {

    public static void main(String[] args) {
        try {
            // Generate key pair
            KeyPair keyPair = KeyUtils.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Compute public key hash
            byte[] pubKeyHash = Script.computePubKeyHash(publicKey);

            // Create P2PKH scriptPubKey
            String scriptPubKey = Script.createP2PKHScriptPubKey(pubKeyHash);
            System.out.println("ScriptPubKey: " + scriptPubKey);

            // Create UTXO
            UTXO utxo1 = new UTXO("txid1", 0, 10, scriptPubKey);

            // Add UTXO to the UTXO set
            UTXOSet utxoSet = new UTXOSet();
            utxoSet.addUTXO(utxo1);

            // Create a transaction
            Transaction transaction = new Transaction();

            // Add input referencing UTXO utxo1
            // Example: assuming utxo1 is being spent, construct a valid scriptSig
            String scriptSig = "OP_DUP OP_HASH160 " + Base64.getEncoder().encodeToString(pubKeyHash) + " OP_EQUALVERIFY OP_CHECKSIG";
            TransactionInput input1 = new TransactionInput("txid1", 0, scriptSig);
            transaction.addInput(input1);

            // Add output for the transaction
            TransactionOutput output = new TransactionOutput(2, scriptPubKey);
            transaction.addOutput(output);

            // Sign transaction inputs
            transaction.signInputs(privateKey);

            // Verify transaction
            boolean isValid = transaction.verifyTransaction();
            System.out.println("Transaction validity: " + isValid);

            // Output transaction details
            System.out.println("Transaction details:");
            System.out.println(transaction);

            // Output updated UTXOs
            System.out.println("Updated UTXOs:");
            for (UTXO utxo : utxoSet.getAllUTXOs()) {
                System.out.println(utxo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
