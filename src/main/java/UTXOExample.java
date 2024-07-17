import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class UTXOExample {

    static class KeyUtils {
        public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(256, new SecureRandom());
            return keyGen.generateKeyPair();
        }

        public static PublicKey getPublicKeyFromBytes(byte[] bytes) throws Exception {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
        }
    }

    public static void main(String[] args) {
        try {
            // Create a new UTXOSet
            UTXOSet utxoSet = new UTXOSet();

            KeyPair keyPair = KeyUtils.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();

            // Compute public key hash
            byte[] myPubKeyHash = Script.computePubKeyHash(publicKey);

            // Create P2PKH scriptPubKey
            String scriptPubKey = Script.createP2PKHScriptPubKey(myPubKeyHash);

            // Create some UTXOs
            UTXO utxo1 = new UTXO("txid1", 0, 0.5, scriptPubKey);
            UTXO utxo2 = new UTXO("txid2", 1, 1.0, "76a91489abcdefabbaabbaabbaabbaabbaabbaabbaabba88ac");
            UTXO utxo3 = new UTXO("txid3", 2, 0.3, "76a914abcdefabcdefabcdefabcdefabcdefabcdefabcdef88ac");

            // Add UTXOs to the UTXOSet
            utxoSet.addUTXO(utxo1);
            utxoSet.addUTXO(utxo2);
            utxoSet.addUTXO(utxo3);

            // Print all UTXOs
            System.out.println("All UTXOs:");
            for (UTXO utxo : utxoSet.getAllUTXOs()) {
                System.out.println(utxo);
            }

            // Example pubKeyHash derived from a scriptPubKey (hex-encoded)
            String examplePubKeyHashHex = "89abcdefabbaabbaabbaabbaabbaabbaabbaabbaabba";

            // Find UTXOs by public key hash
            byte[] pubKeyHash = hexStringToByteArray(examplePubKeyHashHex);
            List<UTXO> foundUTXOs = utxoSet.findUTXOs(myPubKeyHash);

            // Print found UTXOs
            System.out.println("Found UTXOs for pubKeyHash:");
            for (UTXO utxo : foundUTXOs) {
                System.out.println(utxo);
            }

            // Remove a UTXO
            utxoSet.removeUTXO("txid1", 0);

            // Print all UTXOs after removal
            System.out.println("All UTXOs after removal:");
            for (UTXO utxo : utxoSet.getAllUTXOs()) {
                System.out.println(utxo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert a hex string to a byte array
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
