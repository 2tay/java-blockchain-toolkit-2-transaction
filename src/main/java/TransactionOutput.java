public class TransactionOutput {
    private double value;
    private String scriptPubKey;

    public TransactionOutput(double value, String scriptPubKey) {
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    public double getValue() {
        return value;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    @Override
    public String toString() {
        return "TransactionOutput{" +
                "value=" + value +
                ", scriptPubKey='" + scriptPubKey + '\'' +
                '}';
    }
}
