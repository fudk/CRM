package com.greatwall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.greatwall.util.CryptUtil;

public class QiutongHttpclientTest {

	public static void main(String[] args) {
		
//		add();
//		search();
		queryOrder();
	}
	

	public static void search(){
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://www.tqcard.com/queryProductsAPI");  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		
		try {  
			String fid1 = "2";
			String wayid = "2";
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("fid1", fid1));  
			formparams.add(new BasicNameValuePair("wayid", wayid));  

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  

			//getEntity()  
			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				//打印响应内容  
				
				String restr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("response:" + restr);  
				
				Gson gson = new Gson();
				Map map = gson.fromJson(restr, Map.class);
				if("0".equals(map.get("Code"))){
				}
			}  
			//					closeableHttpClient.close();  
		} catch (Exception e) {  
			e.printStackTrace();
		}  finally {  
			try {
				//释放资源  
				closeableHttpClient.close();
			} catch (IOException e) {
			}  
		}  
			
	}
	
	public static void queryOrder(){
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
		
		HttpPost httpPost = new HttpPost("http://www.tqcard.com/queryOrderInfoAPI");  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		
		try {  
			String orderid = "TB160205170240065";
			String userid = "160126155245176";
			
			StringBuffer sb = new StringBuffer();
			sb.append(userid);
			sb.append(orderid);
			
			String signKey = "0af4c9b90ab239bc90ceea1c5d75957b";
			sb.append(signKey);
			System.out.println(sb.toString());
			
			String key = DigestUtils.md5Hex(sb.toString());
			System.out.println(key);
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("orderid", orderid));  
			formparams.add(new BasicNameValuePair("userid", userid));  
			formparams.add(new BasicNameValuePair("key", key)); 
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");  
			httpPost.setEntity(entity);  
			
			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  
			
			//getEntity()  
			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				//打印响应内容  
				
				String restr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("response:" + restr);  
				
				Gson gson = new Gson();
				Map map = gson.fromJson(restr, Map.class);
				if("0".equals(map.get("Code"))){
				}
			}  
			//					closeableHttpClient.close();  
		} catch (Exception e) {  
			e.printStackTrace();
		}  finally {  
			try {
				//释放资源  
				closeableHttpClient.close();
			} catch (IOException e) {
			}  
		}  
		
	}
	public static void add(){

		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
		HttpPost httpPost = new HttpPost("http://www.tqcard.com/sendOrderAPI");  
		
//		String productid = "151214171346459";
		String productid = "20150626111758484";
		String productnum = "1";
		String useract = "13533914580";
		String userid = "160126155245176";
//		String userid = "13682202050";
//		String recvphone = "18173116167";
		
		StringBuffer sb = new StringBuffer();
		sb.append(userid);
		sb.append(productid);
		sb.append(productnum);
		sb.append(useract);
		
		String signKey = "0af4c9b90ab239bc90ceea1c5d75957b";
		sb.append(signKey);
		System.out.println(sb.toString());
		
		String key = DigestUtils.md5Hex(sb.toString());
		System.out.println(key);

		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("productid", productid));  
		formparams.add(new BasicNameValuePair("productnum", productnum));  
		formparams.add(new BasicNameValuePair("useract", useract));  
		formparams.add(new BasicNameValuePair("userid", userid));  
		formparams.add(new BasicNameValuePair("key", key));  

		System.out.println(formparams.toString());
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
