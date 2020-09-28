/*
	HttpClientCustomSSL
	注意这里需要更改几个常量:
	
*/
package com.wang.rocketmq.wechatpay.getpublickey;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;


/**
 * http请求
 * @author 小郑
 * @date 2018年01月22日
 * */
public class HttpClientCustomSSL {
	/**
	 * httpClient 请求获取公钥
	 * @return  
	 * @throws Exception
	 */
	public static String httpClientResultGetPublicKey(String xml) throws Exception{
		StringBuffer reultBuffer = new StringBuffer();
		/*
			注意这里的readCustomerSSL是另一个方法，在下面贴出来\
			读取证书的类
		*/
		SSLConnectionSocketFactory sslsf = readCustomSSL();
		//获取支付公钥的请求URL
		HttpPost httpPost = new HttpPost("https://fraud.mch.weixin.qq.com/risk/getpublickey");
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        StringEntity myEntity = new StringEntity(xml,"UTF-8");
        myEntity.setContentType("text/xml;charset=UTF-8");
        myEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
        httpPost.setEntity(myEntity);
        
        CloseableHttpResponse response      = null;
        InputStream inputStream		        = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader       = null;
        try {
        	response = httpclient.execute(httpPost);
        	HttpEntity entity = response.getEntity();
			if (entity!=null){
				inputStream = entity.getContent();
				inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
				bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					reultBuffer.append(str);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

			httpclient.close();
			response.close();
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
		}
     
        return reultBuffer.toString();
	}


	/**
	 *  读取 apiclient_cert.p12 证书
	 * @return
	 * @throws Exception
	 */
	public static SSLConnectionSocketFactory readCustomSSL() throws Exception{
		/**
		 * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		 */
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        /*
	        读取微信证书
        */
		FileInputStream instream = new FileInputStream(new File("D:\\apiclient_cert.p12"));
		try {
			//支付证书的密码,默认为商户号,所以这里填写商户号
			keyStore.load(instream, "1601648649".toCharArray());
		} finally {
			instream.close();
		}
        //同理,160...  为商户号,证书支付密码
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1601648649".toCharArray()).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		return sslsf;
	}
}