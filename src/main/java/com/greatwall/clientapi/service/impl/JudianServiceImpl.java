package com.greatwall.clientapi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.greatwall.clientapi.service.JudianService;
import com.greatwall.recharge.dto.Consume;

@Service("judianService")
public class JudianServiceImpl implements JudianService {

	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String corpid;
	private String ucode;
	private String utype;
	private String uname;
	private String upwd;
	private String packageId;
	private String packageValue;


	public String getHttpUrl() {
		return httpUrl;
	}
	@Value("#{propertiesReader['judian.httpurl']}") 
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	
	public String getCorpid() {
		return corpid;
	}
	@Value("#{propertiesReader['judian.corpid']}")
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getUcode() {
		return ucode;
	}
	@Value("#{propertiesReader['judian.ucode']}")
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	public String getUtype() {
		return utype;
	}
	@Value("#{propertiesReader['judian.utype']}")
	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getUname() {
		return uname;
	}
	@Value("#{propertiesReader['judian.uname']}")
	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}
	@Value("#{propertiesReader['judian.upwd']}")
	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getPackageId() {
		return packageId;
	}
	@Value("#{propertiesReader['judian.packageId']}")
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageValue() {
		return packageValue;
	}
	@Value("#{propertiesReader['judian.packageValue']}")
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
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

			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("corpid", corpid));  
			formparams.add(new BasicNameValuePair("ucode", ucode));  
			formparams.add(new BasicNameValuePair("utype", utype));  
			formparams.add(new BasicNameValuePair("uname", uname));  
			formparams.add(new BasicNameValuePair("upwd", DigestUtils.md5Hex(upwd).toUpperCase()));  
			formparams.add(new BasicNameValuePair("mobile", consume.getConsumePhone()));  
			formparams.add(new BasicNameValuePair("packageId", getPackageIdByValue(consume.getProductValue())));  
			

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
				consume.setRemark(restr);
				if(restr.startsWith("true")){
					return true;
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

	private String getPackageIdByValue(String productValue){
		String[] pv =packageValue.split(",");
		int tag = 0;
		for(int i=0;i<pv.length;i++){
			if(productValue.equals(pv[i])){
				tag = i;
				break;
			}
		}
		
		String[]  pi = packageId.split(",");
		return pi[tag];
	}

}
