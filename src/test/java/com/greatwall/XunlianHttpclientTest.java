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

public class XunlianHttpclientTest {

	public static void main(String[] args) {
		//		sendMsg();
		search();
	}

	public static void search(){
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://121.201.16.229:12010/Third/kzcz/searchOrder.htm");  
		//httpPost.setConfig();  
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("platformname", "TESTONLINE"));  
		formparams.add(new BasicNameValuePair("flag", "2"));  
		formparams.add(new BasicNameValuePair("myordernum", ""));  
//		formparams.add(new BasicNameValuePair("busordernum", "12b28bdb21d846d390ad82465b957a25"));  
		formparams.add(new BasicNameValuePair("busordernum", "12b28bdb21d846d390ad82465b957a25"));
		

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append("CDrenchWM2014TTEG4GKST1009");
		System.out.println(sb.toString());
		formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  

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

	public static void sendMsg(){
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://121.201.16.229:12010/Third/kzcz/rechange.htm");  
		//httpPost.setConfig();  
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("platformname", "TESTONLINE"));  
		//		formparams.add(new BasicNameValuePair("opstreamid", UUID.randomUUID().toString()));  
		formparams.add(new BasicNameValuePair("opstreamid","12345678qwe"));  
		formparams.add(new BasicNameValuePair("agtphone", "13682202050"));  
		formparams.add(new BasicNameValuePair("agtpasswd", DigestUtils.md5Hex("11111111").toUpperCase()));  
		formparams.add(new BasicNameValuePair("optype", "HFCZ"));  
		formparams.add(new BasicNameValuePair("custphone", "13682202050"));  
		formparams.add(new BasicNameValuePair("opmoney", "100"));  
		formparams.add(new BasicNameValuePair("opcount", "20"));  
		formparams.add(new BasicNameValuePair("opsummoney", "2000"));  
		formparams.add(new BasicNameValuePair("busitype", "01"));  
		formparams.add(new BasicNameValuePair("isp", "01"));  
		formparams.add(new BasicNameValuePair("rechargetype", ""));  
		formparams.add(new BasicNameValuePair("servicecode", ""));  
		formparams.add(new BasicNameValuePair("servicesubcode", ""));  
		formparams.add(new BasicNameValuePair("clientip", ""));  
		formparams.add(new BasicNameValuePair("storageid", ""));  
		formparams.add(new BasicNameValuePair("ip", ""));  
		formparams.add(new BasicNameValuePair("notifyurl", ""));  

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append("CDrenchWM2014TTEG4GKST1009");
		System.out.println(sb.toString());
		formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  

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
