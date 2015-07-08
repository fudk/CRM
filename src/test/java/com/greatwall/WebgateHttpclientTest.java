package com.greatwall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.greatwall.util.CryptUtil;

public class WebgateHttpclientTest {

	public static void main(String[] args) {
		//创建HttpClientBuilder  
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		//HttpClient  
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  

		HttpPost httpPost = new HttpPost("http://42.121.252.207/webgate/iphoneCharge");  
		//httpPost.setConfig();  
//		String char_set = "GBK";
		String char_set = "00";
		String notify_url = "http://";
		String merc_id = "800865";
		String req_id = UUID.randomUUID().toString().replaceAll("-", "");
		String req_dt = DateFormatUtils.format(new Date(), "yyyyMMdd");
		String sign_typ = "MD5";
		String itf_code = "flx_request";
		String ver_no = "1.0.0";
		String mbl_no = "13682202050";
		String flx_typ = "M";
		String flx_num = "10M";
		String signData = char_set + notify_url + merc_id+ req_id + req_dt
                 + sign_typ + itf_code + ver_no +mbl_no+flx_typ+flx_num;
		
		String signKey = "3562456624578012";
		CryptUtil util = new CryptUtil();
		String hmac = util.MD5Sign(signData, signKey);
		System.out.println(hmac);

//		 String hmac = DigestUtils.md5Hex(signData).toUpperCase();
		 
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("char_set", char_set));  
		formparams.add(new BasicNameValuePair("notify_url", notify_url));  
		formparams.add(new BasicNameValuePair("merc_id", merc_id));  
		formparams.add(new BasicNameValuePair("req_id", req_id));  
		formparams.add(new BasicNameValuePair("req_dt", req_dt));  
		formparams.add(new BasicNameValuePair("sign_typ", sign_typ));  
		formparams.add(new BasicNameValuePair("itf_code", itf_code));  
		formparams.add(new BasicNameValuePair("ver_no", ver_no));  
		formparams.add(new BasicNameValuePair("mbl_no", mbl_no));  
		formparams.add(new BasicNameValuePair("flx_typ", flx_typ));  
		formparams.add(new BasicNameValuePair("flx_num", flx_num));  
		formparams.add(new BasicNameValuePair("hmac", hmac));  

		UrlEncodedFormEntity entity;  
		try {  
			entity = new UrlEncodedFormEntity(formparams, "GBK");  
			httpPost.setEntity(entity);  

			HttpResponse httpResponse;  
			//post请求  
			httpResponse = closeableHttpClient.execute(httpPost);  

			//getEntity()  
			HttpEntity httpEntity = httpResponse.getEntity();  
			if (httpEntity != null) {  
				//打印响应内容  
//				System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));  
				System.out.println("response:" + EntityUtils.toString(httpEntity, "GBK"));  
			}  
			//释放资源  
			closeableHttpClient.close();  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}

}
