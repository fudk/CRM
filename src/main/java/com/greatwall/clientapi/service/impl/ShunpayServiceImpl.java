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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.clientapi.service.ShunpayService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.RMSConstant;

@Service("shunpayService")
public class ShunpayServiceImpl implements ShunpayService {

	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String searchhttpurl;
	private String key;
	private String platformname;
	private String agtphone;
	private String agtpasswd;
	private String notifyurl;
	private Integer opmoney = 100;

	public String getNotifyurl() {
		return notifyurl;
	}
	@Value("#{propertiesReader['shunpay.notifyurl']}") 
	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public String getSearchhttpurl() {
		return searchhttpurl;
	}
	@Value("#{propertiesReader['shunpay.searchhttpurl']}") 
	public void setSearchhttpurl(String searchhttpurl) {
		this.searchhttpurl = searchhttpurl;
	}
	@Value("#{propertiesReader['shunpay.httpurl']}") 
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getKey() {
		return key;
	}
	@Value("#{propertiesReader['shunpay.key']}") 
	public void setKey(String key) {
		this.key = key;
	}
	public String getPlatformname() {
		return platformname;
	}
	@Value("#{propertiesReader['shunpay.platformname']}") 
	public void setPlatformname(String platformname) {
		this.platformname = platformname;
	}
	public String getAgtphone() {
		return agtphone;
	}
	@Value("#{propertiesReader['shunpay.agtphone']}") 
	public void setAgtphone(String agtphone) {
		this.agtphone = agtphone;
	}
	public String getAgtpasswd() {
		return agtpasswd;
	}
	@Value("#{propertiesReader['shunpay.agtpasswd']}")
	public void setAgtpasswd(String agtpasswd) {
		this.agtpasswd = agtpasswd;
	}

	public String searchState(Consume consume)throws Exception{
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost(searchhttpurl);  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)  
				.setSocketTimeout(3000).build(); 
		httpPost.setConfig(requestConfig);  
		
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("platformname", platformname));  
		formparams.add(new BasicNameValuePair("flag", "2"));  
		formparams.add(new BasicNameValuePair("myordernum", ""));  
		formparams.add(new BasicNameValuePair("busordernum", consume.getConsumeId().trim()));  

		StringBuffer sb = new StringBuffer();
		for(NameValuePair nameValuePair:formparams){
			sb.append(nameValuePair.getName());
			sb.append("=");
			sb.append(nameValuePair.getValue());
			sb.append("&");
		}
		sb.append(key);
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
				String restr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("shunpay searchState response:" + restr);  
				Gson gson = new Gson();
				Map<String,Object> requestMap = gson.fromJson(restr, Map.class);
				if(requestMap!=null&&"10000000".equals(requestMap.get("retcode"))){
					if("9".equals(requestMap.get("state"))){
						return RMSConstant.CONSUME_STATE_SUC;
					}else if("8".equals(requestMap.get("state"))){
						
					}else if("0".equals(requestMap.get("state"))){
						return RMSConstant.CONSUME_STATE_SUC;
					}else if("9".equals(requestMap.get("state"))){
						
					}
					return RMSConstant.CONSUME_STATE_SUC;
				}else{
					consume.setRemark(restr);
				}
			}  
			//释放资源  
//			closeableHttpClient.close();  
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

			int opsummoney = consume.getConsumePrice().intValue() * opmoney;
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("platformname", platformname));  
			formparams.add(new BasicNameValuePair("opstreamid", consume.getConsumeId()));  
			formparams.add(new BasicNameValuePair("agtphone", agtphone));  
			formparams.add(new BasicNameValuePair("agtpasswd", DigestUtils.md5Hex(agtpasswd).toUpperCase()));  
			formparams.add(new BasicNameValuePair("optype", "HFCZ"));  //话费充值
			formparams.add(new BasicNameValuePair("custphone", consume.getConsumePhone()));  
			formparams.add(new BasicNameValuePair("opmoney", opmoney+""));  
			formparams.add(new BasicNameValuePair("opcount", consume.getConsumePrice().intValue()+""));  
			formparams.add(new BasicNameValuePair("opsummoney", opsummoney+""));  
			formparams.add(new BasicNameValuePair("busitype", "01"));  //01手机
			formparams.add(new BasicNameValuePair("isp", this.ispTransformation(consume.getIsp())));  
			formparams.add(new BasicNameValuePair("rechargetype", ""));  
			formparams.add(new BasicNameValuePair("servicecode", ""));  
			formparams.add(new BasicNameValuePair("servicesubcode", ""));  
			formparams.add(new BasicNameValuePair("clientip", ""));  
			formparams.add(new BasicNameValuePair("storageid", ""));  
			formparams.add(new BasicNameValuePair("ip", ""));  
			formparams.add(new BasicNameValuePair("notifyurl", notifyurl));  

			StringBuffer sb = new StringBuffer();
			for(NameValuePair nameValuePair:formparams){
				sb.append(nameValuePair.getName());
				sb.append("=");
				sb.append(nameValuePair.getValue());
				sb.append("&");
			}
			sb.append(key);
			//			System.out.println(sb.toString());
			formparams.add(new BasicNameValuePair("sign", DigestUtils.md5Hex(sb.toString())));  

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
				System.out.println("shunpay sendMsg response:" + restr);  
				Gson gson = new Gson();
				Map<String,Object> requestMap = gson.fromJson(restr, Map.class);
				if(requestMap!=null&&"10000000".equals(requestMap.get("retcode"))){
					return true;
				}else{
					consume.setRemark(restr);
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
		return false;
	}

	private String ispTransformation(String isp){
		if("CM".equals(isp.toUpperCase())){
			return "01";
		}
		if("CU".equals(isp.toUpperCase())){
			return "02";
		}
		if("CT".equals(isp.toUpperCase())){
			return "03";
		}
		return "";
	}

}
