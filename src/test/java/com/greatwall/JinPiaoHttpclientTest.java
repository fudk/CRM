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

public class JinPiaoHttpclientTest {

	public static void main(String[] args) {
		
//		add();
		search();
	}
	

	public static void search(){
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://122.224.5.41:8080/api.aspx");  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		
		try {  
			String account = "zhiqi";
			String action = "getReports";
			String signKey = "4b27d66b8c91435f81a8852e0a9f247c";
			String count = "2";
			String v = "1.1";
			
			StringBuffer signData = new StringBuffer();
			signData.append("account=");
			signData.append(account);
			signData.append("&count=");
			signData.append(count);
			signData.append("&key=");
			signData.append(signKey);
			
			String sign = DigestUtils.md5Hex(signData.toString());
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("v", v));  
			formparams.add(new BasicNameValuePair("action", action));  
			formparams.add(new BasicNameValuePair("account", account));  
			formparams.add(new BasicNameValuePair("count", count));  
			formparams.add(new BasicNameValuePair("sign", sign));  

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
		HttpPost httpPost = new HttpPost("http://122.224.5.41:8080/api.aspx");  
		
		String action = "charge";
		String v = "1.1";
		String account = "zhiqi";
		String mobile = "15914400880";
		String packageV = "10";
		
		StringBuffer sb = new StringBuffer();
		sb.append("account=");
		sb.append(account);
		sb.append("&mobile=");
		sb.append(mobile);
		sb.append("&package=");
		sb.append(packageV);
		
		String signKey = "4b27d66b8c91435f81a8852e0a9f247c";
		sb.append("&key=");
		sb.append(signKey);
		System.out.println(sb.toString());
		
		String sign = DigestUtils.md5Hex(sb.toString());
		System.out.println(sign);

		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("v", v));  
		formparams.add(new BasicNameValuePair("action", action));  
		formparams.add(new BasicNameValuePair("account", account));  
		formparams.add(new BasicNameValuePair("mobile", mobile));  
		formparams.add(new BasicNameValuePair("package", packageV));  
		formparams.add(new BasicNameValuePair("sign", sign));  

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
