package com.greatwall.clientapi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
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
import com.greatwall.clientapi.service.QiutongService;
import com.greatwall.platform.system.service.LogService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.CryptUtil;
import com.greatwall.util.RMSConstant;

@Service("qiutongService")
public class QiutongServiceImpl implements QiutongService  {

	Logger logger = Logger.getLogger(QiutongServiceImpl.class);
	@Autowired
	private LogService logService;
	
	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String userid;
	private String signKey;
	
	

	public String getHttpUrl() {
		return httpUrl;
	}
	@Value("#{propertiesReader['qiutong.httpurl']}")
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getUserid() {
		return userid;
	}
	@Value("#{propertiesReader['qiutong.userid']}")
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSignKey() {
		return signKey;
	}
	@Value("#{propertiesReader['qiutong.signKey']}")
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	
	public String searchState(Consume consume) throws Exception{
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(httpUrl+"/queryOrderInfoAPI");  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		try {  
			String orderid = consume.getOrderId();
			String userid = this.userid;
			
			StringBuffer signData = new StringBuffer();
			signData.append(userid);
			signData.append(orderid);
			signData.append(signKey);
			String key = DigestUtils.md5Hex(signData.toString());
			System.out.println(key);
			
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
//				System.out.println("liul searchState response:" + restr);  
				logService.execLog("call", "QiutongSearchState", startTimeMillis, formparams.toString()+" response:" + restr);
				
				consume.setRemark(restr);
				if(restr.contains("orderstatus")){
					Gson gson = new Gson();
					Map m = gson.fromJson(restr, Map.class);
					Map data = gson.fromJson(m.get("data").toString(), Map.class);
					if(data.get("orderstatus").toString().contains("1")){
						return RMSConstant.CONSUME_STATE_SENDED_WAIT;
					}else if(data.get("orderstatus").toString().contains("2")){
						return RMSConstant.CONSUME_STATE_SUC;
					}else if(data.get("orderstatus").toString().contains("3")){
						return RMSConstant.CONSUME_STATE_SENDED_FAIL;
					}
					
				    /*if("U".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_WAIT;
				    }else  if("P".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_PROCESSING;
				    }else  if("S".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SUC;
				    }else  if("F".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_FAIL;
				    }else{
				    	return RMSConstant.CONSUME_STATE_SENDED_ERROR;
				    }*/
				}else{
					return RMSConstant.CONSUME_STATE_SENDED_ERROR;
				}
				
			}
			
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

		HttpPost httpPost = new HttpPost(httpUrl+"/sendOrderAPI");  
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
			    .setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		try {  
			
			String productid = consume.getProductParam();
			String productnum = "1";
			String useract = consume.getConsumePhone();
			String userid = this.userid;
			
			StringBuffer signData = new StringBuffer();
			signData.append(userid);
			signData.append(productid);
			signData.append(productnum);
			signData.append(useract);
			signData.append(signKey);
			
			String key = DigestUtils.md5Hex(signData.toString());
			System.out.println(key);
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("productid", productid));  
			formparams.add(new BasicNameValuePair("productnum", productnum));  
			formparams.add(new BasicNameValuePair("useract", useract));  
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
				logService.execLog("call", "QiutongSend", startTimeMillis, formparams.toString()+" response:" + restr);
//				System.out.println("response:" + restr);  
				consume.setRemark(restr);
				if(restr.contains("result")){
					Gson gson = new Gson();
					Map m = gson.fromJson(restr, Map.class);
					if("ok".equals(m.get("result"))){
						consume.setOrderId(m.get("msg").toString().replace("订单ID：", ""));
						return true;
					}
				}
			}  

			//					closeableHttpClient.close();  
		} catch (Exception e) {  
			logger.error("求同充值接口调用错误：", e);
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
