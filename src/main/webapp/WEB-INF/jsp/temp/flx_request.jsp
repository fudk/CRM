<%@ page language="java" contentType="text/html; charset=gbk"%>
<%@ page import="com.recharge.crypt.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.Date,java.text.*"%>
<%@ include file="globalParam.jsp"%>
<html>
    <head>
        <title>流量充值</title>
        <link href="sdk.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    </head>

    <body>
        <%
            String itf_code = "flx_request";
            String sessionId = "";
            HashMap urlMap = new HashMap();

            //编码设置            
            try {
                //获取表单提交的各参数
                String mbl_no = request.getParameter("mbl_no");
                String flx_typ = request.getParameter("flx_typ");
                String flx_num = request.getParameter("flx_num");               
                String req_dt = request.getParameter("req_dt");
 
                //-- 签名原报文
                String signData = char_set + notify_url + merc_id+ req_id + req_dt
                        + sign_typ + itf_code + ver_no +mbl_no+flx_typ+flx_num;

                //签名，并获得签名后的消息摘要
                 out.println("signData="+signData);
                 out.println("signKey="+signKey);
                HiiposmUtil util = new HiiposmUtil();
                String hmac = util.MD5Sign(signData, signKey);
                out.println("hmac="+hmac);
                 
                //-- 请求报文
                  String buf = "char_set=" + char_set + "&notify_url="
                    + notify_url + "&req_id=" + req_id + "&req_dt="
                    + req_dt + "&itf_code=" + itf_code + "&ver_no=" + ver_no
                    + "&merc_id=" + merc_id + "&signKey=" + signKey
                    + "&mbl_no=" + mbl_no + "&flx_typ=" + flx_typ+ "&flx_num=" + flx_num+"&sign_typ="+sign_typ;
                //-- 带上消息摘要
                buf = "hmac=" + hmac + "&" + buf;
                 out.println("buf="+buf);
                   out.println("req_url="+req_url);

                   
                //发起http请求，并获取响应报文
                String res = null;
                try{
                	res = util.sendAndRecv(req_url, buf, char_set);
                }catch(Exception e){
                        out.println(e.getMessage());
                        e.printStackTrace();
                	out.println("\n发起http请求报错");
                	return;
                }
                out.println("res="+res);
                //获取充值平台响应的消息摘要
                String hmac1 = util.getValue(res, "hmac");
                String vfsign = util.getValue(res, "merc_id")                     
                        + util.getValue(res, "msg_code")
                        + util.getValue(res, "msg_inf")
                        + util.getValue(res, "sys_dt");
                        
                String code = util.getValue(res, "msg_code");
                 out.println("code：" + code);
                String re_code=code.substring(3);
                out.println("re_code：" + re_code);
                
                //判断返回码是否是000000，
                if (!re_code.equals("00000")) {
                   out.println("================================");       
                   out.println("交易失败:"
                            + code
                            + URLDecoder.decode(util.getValue(res, "message"),"UTF-8"));
                   out.println("================================");
                    return;
                }

                // -- 验证签名
                boolean flag = false;
                out.println(vfsign + "  " + hmac1);
                flag = util.MD5Verify(vfsign, hmac1, signKey);

                if (!flag) {
                    //request.getSession().setAttribute("message", "验证签名失败！");
                    out.println("验签失败");
                    return;
                }
                out.println("<br/>");
                out.println("================");
                out.println("<br/>");
                out.println("交易成功");
                out.println("<br/>");
                out.println("返回码:" + util.getValue(res, "returnCode"));
                out.println("<br/>");
                out.println("返回码描述信息:" + URLDecoder.decode(util.getValue(res, "message"),"UTF-8"));
                out.println("================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </body>
</html>
