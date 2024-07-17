import java.util.List;

// Represents a Bitcoin transaction
public class Transaction {
    private String transactionId;
    private List<TransactionInput> inputs;
    private List<TransactionOutput> outputs;

    // Constructor
    public Transaction(String transactionId, List<TransactionInput> inputs, List<TransactionOutput> outputs) {
        this.transactionId = transactionId;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TransactionOutput> outputs) {
        this.outputs = outputs;
    }
}


