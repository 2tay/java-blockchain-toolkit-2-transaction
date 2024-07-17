import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import utils.KeyUtils;

public class Transaction {
    private List<TransactionInput> inputs;
    private List<TransactionOutput> outputs;

    public Transaction() {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

    // Add an input to the transaction
    public void addInput(TransactionInput input) {
        inputs.add(input);
    }

    // Add an output to the transaction
    public void addOutput(TransactionOutput output) {
        outputs.add(output);
    }

    // Sign the transaction inputs
    public void signInputs(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);

        for (TransactionInput input : inputs) {
            signature.update(input.getTransactionId().getBytes());
            signature.update(String.valueOf(input.getIndex()).getBytes());
            signature.update(input.getScriptSig().getBytes());
            byte[] sigBytes = signature.sign();
            input.setSignature(Base64.getEncoder().encodeToString(sigBytes));
        }
    }

    // Verify the transaction
    public boolean verifyTransaction() {
        try {
            for (TransactionInput input : inputs) {
                Signature signature = Signature.getInstance("SHA256withECDSA");
                
                // Debug output to check scriptSig value before decoding
                System.out.println("ScriptSig before decoding: " + input.getScriptSig());
                
                // Decode scriptSig
                byte[] decodedScriptSig = Base64.getDecoder().decode(input.getScriptSig());
                
                // Continue verification logic
                PublicKey publicKey = KeyUtils.getPublicKeyFromBytes(decodedScriptSig);
                signature.initVerify(publicKey);
                signature.update(input.getTransactionId().getBytes());
                signature.update(String.valueOf(input.getIndex()).getBytes());
                byte[] sigBytes = Base64.getDecoder().decode(input.getSignature());
                if (!signature.verify(sigBytes)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

    // Get transaction inputs
    public List<TransactionInput> getInputs() {
        return inputs;
    }

    // Get transaction outputs
    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "inputs=" + inputs +
                ", outputs=" + outputs +
                '}';
    }
}
