package com.lgs.eden.api.auth;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Handles encryption
 */
public class EncryptionUtils {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String RSA_PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAxfSmrQE1ROvH9LnnOnB/" +
            "CR0Ivz1kpOYsfhXfopyqfLwOpup2oinLCTXGNncRuWnihI7d2LGpkwcmr6w1+WnQ" +
            "94HmPAzbZ5re+6/VqE2b0HqwjfYz9X52DsU6OXChTjdy/oHHUZM69Pix7pmLIYnj" +
            "WC3tkI6jCf9ts50yK4AGdFiLv0GNFs6WMGDnys2VB1ttRs+UMGNALFgSnpfLqxHA" +
            "qvLh+qjZXyOVc5B4KAxCE8lwEJAng2BIkZjZYKD5NyghYp9MjscxxlTHCXtHtyPZ" +
            "Ir9Loxgi/M6o3qZTgxryCZLIVkhst4ckhrHi6Mb9aG1L+Vu796nR8SNm4TwIMZA7" +
            "CSjYl3rLp1PesAxU3B/hCxk6QsvyURJqrU9+IBmGQnf8mBwIPZMhM4lRKTgwcbJk" +
            "vgDRXnLKiWJBguSGbynvnnzn3Tv5fSyUZ0erF1ooSyJL9vkMvYoe8pG7EPj+KnDI" +
            "Y9RQ0H0SAVqPsDncz2AFluEmXKuE8Ms9hcsOGRGTOIGmyR/u4BOC9kC2ppc0SQN/" +
            "cK30FiYH9mq/C3CLUjIs/o0T3X573V6wbA55IvEL1lhgVDOOGKV2Lre8/456WtFt" +
            "EPsGvPnPLAkjU7nYjVWIvnH30JI03zXm2/6I95SXSFDrB6XZEoKnifW6lfU1egqB" +
            "xq8Nz4fzf81tu6YVDTC4vusCAwEAAQ==";

    /* Since the code is open-source, we can't use a private-key with AES (it seems)
     * so we will keep using this.
     */
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding"; //lgtm [java/weak-cryptographic-algorithm]

    private static PublicKey getPublicKey(){
        try{
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(EncryptionUtils.RSA_PUBLIC_KEY.getBytes()));
            KeyFactory rsa = KeyFactory.getInstance(TRANSFORMATION);
            return rsa.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    private static byte[] encrypt(String data) throws IllegalStateException {
        try {
            // return encrypted
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException("can't generates key");
        }
    }

    public static String getEncrypted(String password) {
        try {
            return Base64.getEncoder().encodeToString(encrypt(password));
        } catch (IllegalStateException e) {
            return password;
        }
    }
}
