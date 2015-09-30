package com.greatwall.api.service.impl;

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
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.api.service.CallbackNotifyService;
import com.greatwall.recharge.dto.ConsumeConditions;

@Service("callbackNotifyService")
public class CallbackNotifyServiceImpl implements CallbackNotifyService {

	public Boolean callbackNotify(ConsumeConditions consume,String opstatus) throws Exception{
		if(consume == null || consume.getNotifyUrl() == null){
			return false;
		}
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(consume.getNotifyUrl());  
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
			    .setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		try {  
			String consumeId = consume.getConsumeId();
			String orderId = consume.getOrderId();
			
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
				System.out.println("callback response:" + restr);  
				Gson gson = new Gson();
				Map<String,Object> requestMap = gson.fromJson(restr, Map.class);
				if(requestMap!=null&&"01".equals(requestMap.get("retcode"))){
					return true;
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
		return false;
	}
}
