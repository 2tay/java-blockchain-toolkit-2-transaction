import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Script {

    static {
        // Add BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());
    }

    // Creates a P2PKH scriptPubKey
    public static String createP2PKHScriptPubKey(byte[] pubKeyHash) {
        return "OP_DUP OP_HASH160 " + Base64.getEncoder().encodeToString(pubKeyHash) + " OP_EQUALVERIFY OP_CHECKSIG";
    }

    // Creates a scriptSig for P2PKH
    public static String createP2PKHScriptSig(byte[] signature, PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(signature) + " " + Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Helper method to compute SHA-256 hash
    public static byte[] sha256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }

    // Helper method to compute RIPEMD-160 hash
    public static byte[] ripemd160(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest ripemd160Digest = MessageDigest.getInstance("RIPEMD160");
        return ripemd160Digest.digest(input);
    }

    // Computes the public key hash (RIPEMD160(SHA256(publicKey)))
    public static byte[] computePubKeyHash(PublicKey publicKey) throws NoSuchAlgorithmException {
        byte[] sha256Hash = sha256(publicKey.getEncoded());
        return ripemd160(sha256Hash);
    }

    // Method to verify the scriptSig against the scriptPubKey
    public static boolean verifyP2PKHScript(String scriptPubKey, String scriptSig, byte[] message) throws Exception {
        String[] scriptPubKeyParts = scriptPubKey.split(" ");
        String[] scriptSigParts = scriptSig.split(" ");

        System.out.println("ScriptPubKey Parts: " + Arrays.toString(scriptPubKeyParts));
        System.out.println("ScriptSig Parts: " + Arrays.toString(scriptSigParts));

        byte[] pubKeyHash = Base64.getDecoder().decode(scriptPubKeyParts[2]);
        byte[] sigPubKeyHash = computePubKeyHash(KeyUtils.getPublicKeyFromBytes(Base64.getDecoder().decode(scriptSigParts[1])));

        if (!Arrays.equals(pubKeyHash, sigPubKeyHash)) {
            System.out.println("Public key hashes do not match!");
            return false;
        }

        PublicKey publicKey = KeyUtils.getPublicKeyFromBytes(Base64.getDecoder().decode(scriptSigParts[1]));
        byte[] signature = Base64.getDecoder().decode(scriptSigParts[0]);

        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initVerify(publicKey);
        sig.update(message);

        return sig.verify(signature);
    }

    // Utility class for key-related operations
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
            // Generate key pair
            KeyPair keyPair = KeyUtils.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Compute public key hash
            byte[] pubKeyHash = computePubKeyHash(publicKey);

            // Create P2PKH scriptPubKey
            String scriptPubKey = createP2PKHScriptPubKey(pubKeyHash);
            System.out.println("ScriptPubKey: " + scriptPubKey);

            // Create a message and sign it
            byte[] message = "Hello, Bitcoin!".getBytes();
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign(privateKey);
            signature.update(message);
            byte[] sigBytes = signature.sign();

            // Create scriptSig
            String scriptSig = createP2PKHScriptSig(sigBytes, publicKey);
            System.out.println("ScriptSig: " + scriptSig);

            // Verify the script
            boolean isValid = verifyP2PKHScript(scriptPubKey, scriptSig, message);
            System.out.println("Script verification result: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
