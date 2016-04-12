package com.greatwall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LocalHttpclientTest {
	
	private static String hosts = "http://115.29.43.62:8080";
//	private static String hosts = "http://127.0.0.1:81";

	public static void main(String[] args) {
//		regPhone();
		 regFlow();
//		requesQuery();
	}

	public static void regPhone(){
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(hosts+"/rechargeapi/recharge");  
		  
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 

		//话费测试
		formparams.add(new BasicNameValuePair("platId", "ANQING"));  
		formparams.add(new BasicNameValuePair("timestamp", new Date().getTime()+""));  
		formparams.add(new BasicNameValuePair("orderId", UUID.randomUUID().toString()));  
		formparams.add(new BasicNameValuePair("opType", "phone"));  
		formparams.add(new BasicNameValuePair("flxTyp", "")); 
		formparams.add(new BasicNameValuePair("custPhone", "18173116167"));  
		formparams.add(new BasicNameValuePair("opPrice", "10"));  
		formparams.add(new BasicNameValuePair("opNum", "1"));  
//		formparams.add(new BasicNameValuePair("notifyUrl", hosts+"/rechargeapi/callback"));  
		formparams.add(new BasicNameValuePair("notifyUrl",hosts+"/rechargeapi/callback"));  

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append("");
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

	public static void regFlow(){
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		//				HttpPost httpPost = new HttpPost("http://115.29.43.62:8080/rechargeapi/recharge");  
		HttpPost httpPost = new HttpPost( hosts+"/rechargeapi/recharge");  
//		HttpPost httpPost = new HttpPost(hosts+"/rechargeapi/wt/recharge");
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 

		//流量测试
		formparams.add(new BasicNameValuePair("platId", "ADMIN"));  
		formparams.add(new BasicNameValuePair("timestamp", new Date().getTime()+""));  
		formparams.add(new BasicNameValuePair("orderId", UUID.randomUUID().toString()));  
		formparams.add(new BasicNameValuePair("opType", "flow"));  
		formparams.add(new BasicNameValuePair("flxTyp", "M")); 
		formparams.add(new BasicNameValuePair("custPhone", "18173116167"));  
		formparams.add(new BasicNameValuePair("opPrice", "5M"));  
		formparams.add(new BasicNameValuePair("opNum", "1"));  
		formparams.add(new BasicNameValuePair("notifyUrl", hosts+"/rechargeapi/callback"));  


		/*formparams.add(new BasicNameValuePair("platId", "YHNNIK"));  
				formparams.add(new BasicNameValuePair("timestamp", "1430104553"));  
				formparams.add(new BasicNameValuePair("orderId", "11111111"));  
				formparams.add(new BasicNameValuePair("opType", "flow"));  
				formparams.add(new BasicNameValuePair("flxTyp", "M")); 
				formparams.add(new BasicNameValuePair("custPhone", "18825137275"));  
				formparams.add(new BasicNameValuePair("opPrice", "10M"));  
				formparams.add(new BasicNameValuePair("opNum", "1"));  
				formparams.add(new BasicNameValuePair("notifyUrl", ""));  */

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append("C691575047B00001799167E0EEE0A940");
		//				sb.append("C6AF41651F70000132CD17C41C70A5D0");
		formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  
		System.out.println("====");


//		[{"platId":"288100","timestamp":"1444703462094","orderId":"20151013103059000001",
//		"opType":"flow","flxTyp":"M","custPhone":"13905000513","opPrice":"30M","opNum":1,"notifyUrl":"","sign":"fb923e9ba408d7cb5791f145f8845e7c"}] 

		/*List<NameValuePair> formparamstest = new ArrayList<NameValuePair>(); 
		formparamstest.add(new BasicNameValuePair("platId", "288100"));  
		formparamstest.add(new BasicNameValuePair("timestamp", "1444703462094"));  
		formparamstest.add(new BasicNameValuePair("orderId", "20151013103059000001"));  
		formparamstest.add(new BasicNameValuePair("opType", "flow"));  
		formparamstest.add(new BasicNameValuePair("flxTyp", "M")); 
		formparamstest.add(new BasicNameValuePair("custPhone", "13905000513"));  
		formparamstest.add(new BasicNameValuePair("opPrice", "30M"));  
		formparamstest.add(new BasicNameValuePair("opNum", "1"));  
		formparamstest.add(new BasicNameValuePair("notifyUrl","")); 
		formparamstest.add(new BasicNameValuePair("sign", "fb923e9ba408d7cb5791f145f8845e7c")); */ 
		
		UrlEncodedFormEntity entity;  
		try {  
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");  
//			entity = new UrlEncodedFormEntity(formparamstest, "UTF-8");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  

			Header[] ss = httpResponse.getAllHeaders();
			
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

	public static void requesQuery(){
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		//		HttpPost httpPost = new HttpPost("http://115.29.43.62:8080/rechargeapi/recharge");  
		HttpPost httpPost = new HttpPost( hosts+"/rechargeapi/resultQuery");  
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 

		//流量测试
		formparams.add(new BasicNameValuePair("platId", "TEST1"));  
		formparams.add(new BasicNameValuePair("timestamp", new Date().getTime()+""));  
		formparams.add(new BasicNameValuePair("orderId", "d3638659-33b1-4ab4-93e4-4b61a5e88936"));  

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append("C6914624EB90000116D71D90141B3FC0");
		formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  
		System.out.println("====");


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
