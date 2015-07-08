<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.recharge.crypt.*"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.Date,java.text.*"%>
<%@ page import="java.util.HashMap"%>
<%@ include file="globalParam.jsp"%>
<html>
	<head>
		<title>订单查询</title>
		<link href="sdk.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	</head>

	<body>
		<%
			String itf_code = "flx_result_query";

			request.setCharacterEncoding("GBK");
			try {
				String inq_req_id = request.getParameter("inq_req_id");
				String inq_req_dt = request.getParameter("inq_req_dt");
				
				 //-- 签名原报文
                String signData = char_set +  merc_id+ req_id + inq_req_id + inq_req_dt 
                        + sign_typ + itf_code + ver_no;
                out.println("signData="+signData);
				
				HiiposmUtil util = new HiiposmUtil();
				String hmac = util.MD5Sign(signData, signKey);

				//-- 请求报文
				 String buf = "char_set=" + char_set  + "&req_id=" + req_id + "&inq_req_id="
                    + inq_req_id+"&inq_req_dt="+ inq_req_dt + "&itf_code=" + itf_code + "&ver_no=" + ver_no
                    + "&merc_id=" + merc_id + "&signKey=" + signKey+"&sign_typ="+sign_typ;
				buf = "hmac=" + hmac + "&" + buf;

				//发起http请求，并获取响应报文
				String res = util.sendAndRecv(req_url, buf, char_set);

				//获取平台返回的签名消息摘要，用来验签
				String hmac1 = util.getValue(res, "hmac");
				String vfsign = util.getValue(res, "merc_id")
						+ util.getValue(res, "msg_code")
						+ util.getValue(res, "msg_inf")
						+ util.getValue(res, "mbl_no")	
						+ util.getValue(res, "flx_typ")
						+ util.getValue(res, "flx_num")
						+ util.getValue(res, "chg_sts");
						
				String code = util.getValue(res, "msg_code");
				out.println("code：" + code);
				String re_code=code.substring(3);
				out.println("re_code：" + re_code);
				
				if (!re_code.equals("00000")) {
					out.println("错误码：" + code);
					out.println("错误信息："
							+ URLDecoder.decode(util.getValue(res, "msg_inf"),
									"UTF-8"));
					return;
				}

				// -- 验证签名
				boolean flag = false;
				flag = util.MD5Verify(vfsign, hmac1, signKey);

				if (!flag) {
					out.println("验签失败");
                    return;
				}

				out.println("================");
				out.println("<br/>");
				out.println("交易成功");
				out.println("<br/>");
				out.println("================");
				out.println("商户编号：" + util.getValue(res, "merc_id"));
				out.println("<br/>");				
				out.println("返回码:" + util.getValue(res, "msg_code"));
				out.println("<br/>");
				out.println("返回码描述信息:" + URLDecoder.decode(util.getValue(res, "msg_inf"),"UTF-8"));
				out.println("<br/>");	
				
				out.println("被充值手机号：" + util.getValue(res, "mbl_no"));
				out.println("<br/>");
				out.println("流量包类型：" + util.getValue(res, "flx_typ"));
				out.println("<br/>");
				out.println("流量包值：" + util.getValue(res, "flx_num"));	
				out.println("<br/>");
				out.println("充值状态：" + util.getValue(res, "chg_sts"));
				out.println("<br/>");							
				out.println("签名数据：" + util.getValue(res, "hmac"));
				out.println("<br/>");
				out.println("" + util.getValue(res, ""));
				out.println("<br/>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		%>

	</body>
</html>
