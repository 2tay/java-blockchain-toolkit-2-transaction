import java.util.Objects;

public class UTXO {
    private String transactionId;
    private int index;
    private double value;
    private String scriptPubKey;

    public UTXO(String transactionId, int index, double value, String scriptPubKey) {
        this.transactionId = transactionId;
        this.index = index;
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getIndex() {
        return index;
    }

    public double getValue() {
        return value;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UTXO utxo = (UTXO) o;
        return index == utxo.index && Objects.equals(transactionId, utxo.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, index);
    }

    @Override
    public String toString() {
        return "UTXO{" +
                "transactionId='" + transactionId + '\'' +
                ", index=" + index +
                ", value=" + value +
                ", scriptPubKey='" + scriptPubKey + '\'' +
                '}';
    }
}
