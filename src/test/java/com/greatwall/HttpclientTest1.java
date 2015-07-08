package com.greatwall;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpclientTest1 {

	public static void main(String[] args) {
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://115.29.221.49:88/srvdemo/MobileSend.ashx");  
		//httpPost.setConfig();  
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("corpid", "39"));  
		formparams.add(new BasicNameValuePair("ucode", "10020"));  
		formparams.add(new BasicNameValuePair("utype", "1"));  
		formparams.add(new BasicNameValuePair("uname", "625668668"));  
		formparams.add(new BasicNameValuePair("upwd", DigestUtils.md5Hex("000000").toUpperCase()));  
		formparams.add(new BasicNameValuePair("mobile", "13832100123"));  
		formparams.add(new BasicNameValuePair("packageId", "22"));  


		UrlEncodedFormEntity entity;  
		try {  
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  

			//getEntity()  
			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				//打印响应内容  
				System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));  
			}  
			//释放资源  
			closeableHttpClient.close();  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}

}
