package com.wang.rocketmq.wechatpay.utils;

import com.wang.rocketmq.wechatpay.utils.entity.TransfersDto;
import org.apache.commons.cli.oss.KeyStores;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;



public class HttpRequestHandler {

    private int socketTimeout = 10000;
    private int connectTimeout = 30000;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;

    private static void initCert(String path, TransfersDto transfer)
        throws IOException, KeyStoreException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyManagementException {
        KeyStore keyStore = KeyStores.getInstance("PKCS12", path, transfer.map());
        FileInputStream instream = new FileInputStream(new File(path));
        try {
            keyStore.load(instream, transfer.getMchid().toCharArray());
        }
        catch (CertificateException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,
            transfer.getMchid().toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
            new String[] {"TLSv1"}, null,
            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
    }

    public static String httpsRequest(String url, String xmlObj, TransfersDto model, String path)
        throws IOException, KeyStoreException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyManagementException {
        initCert(path, model);
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }
        catch (ConnectionPoolTimeoutException e) {
        }
        catch (ConnectTimeoutException e) {
        }
        catch (SocketTimeoutException e) {
        }
        catch (Exception e) {
        }
        finally {
            httpPost.abort();
        }
        return result;
    }
}
