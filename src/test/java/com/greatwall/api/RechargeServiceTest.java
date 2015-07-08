package com.greatwall.api;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.greatwall.api.service.RechargeService;

public class RechargeServiceTest {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();   
	    //注册WebService接口   
	    factory.setServiceClass(RechargeService.class);   
	    //设置WebService地址   
	    factory.setAddress("http://localhost/ws/rechargeService");        
	    RechargeService rechargeService = (RechargeService)factory.create();
	    
	    System.out.println(rechargeService.add(1, 2));
	    
	    
	    
//		JaxWsDynamicClientFactory  factory =JaxWsDynamicClientFactory.newInstance();
//		Client client =factory.createClient("http://localhost/ws/rechargeService?wsdl");
//
//		try {
//			Object[] obj =client.invoke("add",1,2);
//			System.out.println("resp:"+obj[0]);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
