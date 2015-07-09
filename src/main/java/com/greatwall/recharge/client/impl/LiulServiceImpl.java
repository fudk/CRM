package com.greatwall.recharge.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.greatwall.recharge.client.LiulService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.CryptUtil;
import com.greatwall.util.RMSConstant;

@Service("liulService")
public class LiulServiceImpl implements LiulService {

	/** 
	 * @Fields httpUrl : 接口调用地址 
	 */ 
	private String httpUrl;
	private String charSet;
	private String notifyUrl;
	private String mercId;
	private String signTyp;
	private String itfCode;
	private String verNo;
	private String signKey;
	
	

	public String getHttpUrl() {
		return httpUrl;
	}
	@Value("#{propertiesReader['liul.httpurl']}")
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getCharSet() {
		return charSet;
	}
	@Value("#{propertiesReader['liul.charset']}")
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	@Value("#{propertiesReader['liul.notifyurl']}")
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMercId() {
		return mercId;
	}
	@Value("#{propertiesReader['liul.mercid']}")
	public void setMercId(String mercId) {
		this.mercId = mercId;
	}

	public String getSignTyp() {
		return signTyp;
	}
	@Value("#{propertiesReader['liul.signtyp']}")
	public void setSignTyp(String signTyp) {
		this.signTyp = signTyp;
	}

	public String getItfCode() {
		return itfCode;
	}
	@Value("#{propertiesReader['liul.itfcode']}")
	public void setItfCode(String itfCode) {
		this.itfCode = itfCode;
	}

	public String getVerNo() {
		return verNo;
	}
	@Value("#{propertiesReader['liul.verno']}")
	public void setVerNo(String verNo) {
		this.verNo = verNo;
	}

	public String getSignKey() {
		return signKey;
	}
	@Value("#{propertiesReader['liul.signKey']}")
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String searchState(Consume consume) throws Exception{
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
			
			String signData = charSet +  mercId+ reqId + inqReqId + inqReqDt 
	                + signTyp + itf_code + verNo;
			CryptUtil util = new CryptUtil();
			String hmac = util.MD5Sign(signData, signKey);
			System.out.println(hmac);
			
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
				
//				System.out.println("response:" + restr);  
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
				    }
				    return RMSConstant.CONSUME_STATE_FAIL;
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
		return RMSConstant.CONSUME_STATE_FAIL;
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
			String reqId = consume.getConsumeId();
			String reqDt = DateFormatUtils.format(new Date(), "yyyyMMdd");
			String mblNo = consume.getConsumePhone();
			String flxNum = consume.getProductValue();
			String flxTyp = "";
			if(consume.getProductName().contains("月包")){
				flxTyp = "M";
			}
			
			String signData = charSet + notifyUrl + mercId+ reqId + reqDt
	                 + signTyp + itfCode + verNo +mblNo+flxTyp+flxNum;
			
			CryptUtil util = new CryptUtil();
			String hmac = util.MD5Sign(signData, signKey);
			
			// 创建参数队列  
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("char_set", charSet));  
			formparams.add(new BasicNameValuePair("notify_url", notifyUrl));  
			formparams.add(new BasicNameValuePair("merc_id", mercId));  
			formparams.add(new BasicNameValuePair("req_id", reqId));  
			formparams.add(new BasicNameValuePair("req_dt", reqDt));  
			formparams.add(new BasicNameValuePair("sign_typ", signTyp));  
			formparams.add(new BasicNameValuePair("itf_code", itfCode));  
			formparams.add(new BasicNameValuePair("ver_no", verNo));  
			formparams.add(new BasicNameValuePair("mbl_no", mblNo));  
			formparams.add(new BasicNameValuePair("flx_typ", flxTyp));  
			formparams.add(new BasicNameValuePair("flx_num", flxNum));  
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
				
//				System.out.println("response:" + restr);  
				consume.setRemark(restr);
				if(restr.contains("FLX00000")){
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
