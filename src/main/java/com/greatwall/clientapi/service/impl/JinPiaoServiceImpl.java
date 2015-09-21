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
import com.greatwall.clientapi.service.JinPiaoService;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.CryptUtil;
import com.greatwall.util.RMSConstant;

@Service("jinPiaoService")
public class JinPiaoServiceImpl implements JinPiaoService {

	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String account;
	private String signKey;
	
	

	public String getHttpUrl() {
		return httpUrl;
	}
	@Value("#{propertiesReader['jinpiao.httpurl']}")
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getAccount() {
		return account;
	}
	@Value("#{propertiesReader['jinpiao.account']}")
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSignKey() {
		return signKey;
	}
	@Value("#{propertiesReader['jinpiao.signKey']}")
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	
	/*public String searchState(Consume consume) throws Exception{
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
			String reqId = UUID.randomUUID().toString().replaceAll("-", "");
			String inqReqId = consume.getConsumeId();
			String inqReqDt = DateFormatUtils.format(consume.getCreateTime(), "yyyyMMdd");
			String itf_code = "flx_result_query";
			
			StringBuffer signData = new StringBuffer();
			signData.append(charSet);
			signData.append(mercId);
			signData.append(reqId);
			signData.append(inqReqId);
			signData.append(inqReqDt);
			signData.append(signTyp);
			signData.append(itfCode);
			signData.append(verNo);
			
//			String signData = charSet +  mercId+ reqId + inqReqId + inqReqDt 
//	                + signTyp + itf_code + verNo;
			CryptUtil util = new CryptUtil();
			String hmac = util.MD5Sign(signData.toString(), signKey);
			
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("char_set", charSet));  
			formparams.add(new BasicNameValuePair("merc_id", mercId));  
			formparams.add(new BasicNameValuePair("req_id", reqId));  
			formparams.add(new BasicNameValuePair("inq_req_id", inqReqId));  
			formparams.add(new BasicNameValuePair("inq_req_dt", inqReqDt));  
			formparams.add(new BasicNameValuePair("sign_typ", signTyp));  
			formparams.add(new BasicNameValuePair("itf_code", itf_code));  
			formparams.add(new BasicNameValuePair("ver_no", verNo));  
			formparams.add(new BasicNameValuePair("hmac", hmac));  
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "GBK");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  

			//getEntity()  
			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				//打印响应内容  
				
				String restr = EntityUtils.toString(httpEntity, "GBK");
				
				System.out.println("liul searchState response:" + restr);  
				consume.setRemark(restr);
				if(restr.contains("FLX00000")){
					String[] resArr = StringUtils.split(restr, "&");

				    Map<String,String> resMap = new HashMap<String,String>();
				    for (int i = 0; i < resArr.length; i++) {
				      String data = resArr[i];
				      int index = StringUtils.indexOf(data, '=');
				      String nm = StringUtils.substring(data, 0, index);
				      String val = StringUtils.substring(data, index + 1);
				      resMap.put(nm, val);
				    }
				    String chgSts = (String)resMap.get("chg_sts") == null ? "" : (String)resMap.get("chg_sts");
				    if("U".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_WAIT;
				    }else  if("P".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_PROCESSING;
				    }else  if("S".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SUC;
				    }else  if("F".equals(chgSts.toUpperCase())){
				    	return RMSConstant.CONSUME_STATE_SENDED_FAIL;
				    }else{
				    	return RMSConstant.CONSUME_STATE_SENDED_ERROR;
				    }
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
	}*/
	
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
			String action = "charge";
			String v = "1.1";
			String packageValue = consume.getProductValue().replaceAll("M", "");
			String mobile = consume.getConsumePhone();
			
			
			
//			sb.append("&v=");
//			sb.append(v);
//			String reqId = consume.getConsumeId();
//			String reqDt = DateFormatUtils.format(new Date(), "yyyyMMdd");
//			String mblNo = consume.getConsumePhone();
//			String flxNum = consume.getProductValue();
//			String flxTyp = consume.getProductValidity();
//			if(consume.getProductName().contains("月包")){
//				flxTyp = "M";
//			}else if(consume.getProductName().contains("季包")){
//				flxTyp = "Q";
//			}else if(consume.getProductName().contains("半年包")){
//				flxTyp = "H";
//			}
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
			System.out.println(sign);
			
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
				consume.setRemark(restr);
				Gson gson = new Gson();
				Map map = gson.fromJson(restr, Map.class);
				if("0".equals(map.get("Code"))){
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


}
