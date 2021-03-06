package com.greatwall.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.api.service.CallbackNotifyService;
import com.greatwall.platform.system.service.LogService;
import com.greatwall.recharge.dto.ConsumeConditions;

@Service("callbackNotifyService")
public class CallbackNotifyServiceImpl implements CallbackNotifyService {

	Logger logger = Logger.getLogger(CallbackNotifyServiceImpl.class);
	
	@Autowired
	private LogService logService;
	
	public Boolean callbackNotify(ConsumeConditions consume,String opstatus) throws Exception{
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  
		logger.error(consume.getNotifyUrl()+" 回调函数开始");
		if(consume == null || StringUtils.isBlank(consume.getNotifyUrl())){
			return false;
		}
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(consume.getNotifyUrl());  
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(5000).setConnectTimeout(5000)  
			    .setSocketTimeout(5000).build(); 
		httpPost.setConfig(requestConfig);  
		try {  
			String consumeId = consume.getConsumeId();
			String orderId = consume.getOrderId();
			if(orderId!=null&&orderId.contains("_")){
				orderId = orderId.split("_")[1];
			}
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("consumeId", consumeId));  
			formparams.add(new BasicNameValuePair("orderId", orderId));  
			formparams.add(new BasicNameValuePair("opstatus", opstatus));  
			
			StringBuffer sb = new StringBuffer();
			for(NameValuePair nameValuePair:formparams){
				sb.append(nameValuePair.getName());
				sb.append("=");
				sb.append(nameValuePair.getValue());
				sb.append("&");
			}
			sb.append(consume.getSessionKey());
//			System.out.println(sb.toString());
			formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse = closeableHttpClient.execute(httpPost);  

			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				String restr = EntityUtils.toString(httpEntity, "UTF-8");
				logger.error(consume.getNotifyUrl()+" 回调函数返回："+restr);
				logService.execLog("callback", consume.getNotifyUrl(), startTimeMillis, formparams.toString()+" response:" + restr);
				Gson gson = new Gson();
				Map<String,Object> requestMap = gson.fromJson(restr, Map.class);
				if(requestMap!=null&&"01".equals(requestMap.get("retcode"))){
					return true;
				}
			}  

		} catch (Exception e) {  
			logger.error("", e);
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
