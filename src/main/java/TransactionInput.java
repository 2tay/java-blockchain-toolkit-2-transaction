// Represents an input in a Bitcoin transaction
public class TransactionInput {
    private String previousTransactionId;
    private int outputIndex;
    private String scriptSig;

    // Constructor
    public TransactionInput(String previousTransactionId, int outputIndex, String scriptSig) {
        this.previousTransactionId = previousTransactionId;
        this.outputIndex = outputIndex;
        this.scriptSig = scriptSig;
    }

    // Getters and Setters
    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    public void setPreviousTransactionId(String previousTransactionId) {
        this.previousTransactionId = previousTransactionId;
    }

    public int getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(int outputIndex) {
        this.outputIndex = outputIndex;
    }

    public String getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(String scriptSig) {
        this.scriptSig = scriptSig;
    }
}



