package com.pinextractor;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        // domain names here
        String[] urls = new String[]{
        };

        for (int i = 0; i < urls.length; i++) {
            SSLSocketFactory factory = getSocketFactoryContext();
            System.out.println("url = " + urls[i]);
            receiveUrl(factory, "https://" + urls[i]);
            System.out.println("");
        }
    }

    private static void receiveUrl(SSLSocketFactory factory, String urlStr) throws IOException {
        InputStream instream = null;
        try {
            URL url = new URL(urlStr);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(factory);
            instream = urlConnection.getInputStream();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (instream != null) {
                instream.close();
            }
        }
    }

    private static SSLSocketFactory getSocketFactoryContext() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager tm[] = {new PubKeyManager()};
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tm, null);
        return context.getSocketFactory();
    }
}
