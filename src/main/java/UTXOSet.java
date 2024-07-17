import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UTXOSet {
    private Map<String, UTXO> utxoMap;

    public UTXOSet() {
        this.utxoMap = new HashMap<>();
    }

    // Add a new UTXO
    public void addUTXO(UTXO utxo) {
        String key = utxo.getTransactionId() + ":" + utxo.getIndex();
        utxoMap.put(key, utxo);
    }

    // Remove a spent UTXO
    public void removeUTXO(String transactionId, int index) {
        String key = transactionId + ":" + index;
        utxoMap.remove(key);
    }

    // Find UTXOs for a given public key hash
    public List<UTXO> findUTXOs(byte[] pubKeyHash) {
        List<UTXO> result = new ArrayList<>();
        for (UTXO utxo : utxoMap.values()) {
            if (utxo.getScriptPubKey().contains(Base64.getEncoder().encodeToString(pubKeyHash))) {
                result.add(utxo);
            }
        }
        return result;
    }

    // Get all UTXOs
    public List<UTXO> getAllUTXOs() {
        return new ArrayList<>(utxoMap.values());
    }
}
