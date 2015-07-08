<%
   
    String localAddr = "http://58.20.47.79:17001/sit_ptc_mcgdemo02";

    String char_set = "00"; //00--GBK;01--GB2312;02--UTF-8

   
    String notify_url = localAddr + "/notify_url.jsp";
    
 
    String clientIp = request.getHeader("x-forwarded-for");
    if ((clientIp == null) || (clientIp.length() == 0)
            || ("unknown".equalsIgnoreCase(clientIp))) {
        clientIp = request.getHeader("Proxy-Client-IP");
    }
    if ((clientIp == null) || (clientIp.length() == 0)
            || ("unknown".equalsIgnoreCase(clientIp))) {
        clientIp = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((clientIp == null) || (clientIp.length() == 0)
            || ("unknown".equalsIgnoreCase(clientIp))) {
        clientIp = request.getRemoteAddr();
    }
    

    String ipAddress = clientIp;
    
     String merc_id = (String)request.getParameter("merc_id"); 

	if(merc_id == null)
    {
		merc_id = (String)request.getSession().getAttribute("merc_id");
    }
		
		if( merc_id != null ) {
        request.getSession().setAttribute("merc_id", merc_id);
   } else {
	  merc_id = "8800002000000764";  //UAT
	// merc_id = "8800002000000802"; 
        request.getSession().setAttribute("merc_id", merc_id);
   }
   
    
    
    String req_id = String.valueOf(System.currentTimeMillis());
  
    String sign_typ = "MD5";
    String ver_no = "1.0.0";
   
    String req_url = "http://115.28.169.113:31011/webgate/iphoneCharge";//UAT-PTC
    
    //String req_url = "http://42.121.252.207/webgate/iphoneFlowCharge"; //PRD
          
	  String signKey = (String)request.getParameter("signKey"); 

	if(signKey == null)
    {
       signKey = (String)request.getSession().getAttribute("signKey");
    }
   
   if( signKey != null ) {
        request.getSession().setAttribute("signKey", signKey);
   } else {
       signKey = "111111111123eeeeerrrrr";
        //signKey = "222222222222222222rrr";
        
        request.getSession().setAttribute("signKey", signKey);
   }
     
%>

