package com.pinextractor;

import javax.net.ssl.X509TrustManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public final class PubKeyManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (chain == null) {
            throw new IllegalArgumentException(
                    "checkServerTrusted: X509Certificate array is null");
        }
        if (!(chain.length > 0)) {
            throw new IllegalArgumentException(
                    "checkServerTrusted: X509Certificate is empty");
        }


        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();

        byte[] publicKeyEncoded = pubkey.getEncoded();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] publicKeySha256 = messageDigest.digest(publicKeyEncoded);
        byte[] publicKeyShaBase64 = Base64.getEncoder().encode(publicKeySha256);

        System.out.println("encoded = " + "sha256/" + new String(publicKeyShaBase64));
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
