package com.greatwall.clientapi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.clientapi.service.JinPiaoService;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.clientapi.service.ShService;
import com.greatwall.platform.system.service.LogService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.CryptUtil;
import com.greatwall.util.RMSConstant;

@Service("shService")
public class ShServiceImpl implements ShService {

	Logger logger = Logger.getLogger(ShServiceImpl.class);
	@Autowired
	private LogService logService;
	
	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String account;
	private String signKey;
	
	

	public String getHttpUrl() {
		return httpUrl;
	}
	@Value("#{propertiesReader['sh.httpurl']}")
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getAccount() {
		return account;
	}
	@Value("#{propertiesReader['sh.account']}")
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSignKey() {
		return signKey;
	}
	@Value("#{propertiesReader['sh.signKey']}")
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	@Override
	public String searchState() throws Exception{
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://122.224.5.41:8080/api.aspx/api.aspx");  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		
		try {  
			String action = "getReports";
			String count = "10";
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
//				System.out.println("response:" + restr);  
				logService.execLog("call", "jinpiaoSearchState", startTimeMillis, formparams.toString()+" response:" + restr);
				
				Gson gson = new Gson();
				Map map = gson.fromJson(restr, Map.class);
				if("0".equals(map.get("Code"))){
					return restr;
				}
			}  
			//					closeableHttpClient.close();  
		} catch (Exception e) {  
			throw new Exception(e);
		}  finally {  
			try {
				//释放资源  
				closeableHttpClient.close();
			} catch (IOException e) {
			}  
		}  
		return "";
	}
	
	
	public Boolean sendMsg(Consume consume) throws Exception{
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(httpUrl);  
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
			    .setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		try {  
			String action = "charge";
			String v = "1.1";
			String packageValue = consume.getProductValue().replaceAll("M", "");
			String mobile = consume.getConsumePhone();
			
			StringBuffer signData = new StringBuffer();
			signData.append("account=");
			signData.append(account);
			signData.append("&mobile=");
			signData.append(mobile);
			signData.append("&package=");
			signData.append(packageValue);
			signData.append("&key=");
			signData.append(signKey);
			
			String sign = DigestUtils.md5Hex(signData.toString());
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("v", v));  
			formparams.add(new BasicNameValuePair("action", action));  
			formparams.add(new BasicNameValuePair("account", account));  
			formparams.add(new BasicNameValuePair("mobile", mobile));  
			formparams.add(new BasicNameValuePair("package", packageValue));  
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
//				System.out.println("response:" + restr);  
				logService.execLog("call", "jinpiaoSend", startTimeMillis, formparams.toString()+" response:" + restr);
				
				Gson gson = new Gson();
				Map map = gson.fromJson(restr, Map.class);
				if("0".equals(map.get("Code"))){
					consume.setRemark(String.format("%.0f ", map.get("TaskID")).trim());
					return true;
				}
			}  
			//					closeableHttpClient.close();  
		} catch (Exception e) {  
			logger.error("金飘流量充值接口错误：", e);
			throw new Exception(e);
		}  finally {  
			try {
				//释放资源  
				closeableHttpClient.close();
			} catch (IOException e) {
			}  
		}  
		return false;
	}


}
