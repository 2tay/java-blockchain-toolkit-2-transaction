public class TransactionInput {
    private String transactionId;
    private int index;
    private String scriptSig;
    private String signature;

    public TransactionInput(String transactionId, int index, String scriptSig) {
        this.transactionId = transactionId;
        this.index = index;
        this.scriptSig = scriptSig;
        this.signature = "";
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getIndex() {
        return index;
    }

    public String getScriptSig() {
        return scriptSig;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "TransactionInput{" +
                "transactionId='" + transactionId + '\'' +
                ", index=" + index +
                ", scriptSig='" + scriptSig + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
