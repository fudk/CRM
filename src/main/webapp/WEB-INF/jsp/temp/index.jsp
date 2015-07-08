<%@ page language="java" import="java.net.*,java.util.*,java.text.*" pageEncoding="gbk"%>
<%@ include file="globalParam.jsp" %>

<html>
    <head>
        <title>充流量商户接入演示</title>
        <link href="css/sdk.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    </head>

    <body>
        <center>           
            <table>
                <tr>
                    <th>
                        <p>
                            <h4>
                               流量充值接入演示Demo
                            </h4>
                        </p>
                        <hr>
                    </th>
                </tr>
                        
                <tr>
					<td align="center">
					<form action="index.jsp">
							商户号:
							<input name="merchantId" value="<%=merc_id%>" />
							<!--input name="merchantId"/-->
							<br/>
							商户密钥:
							<input name="signKey" value="<%=signKey%>" size="64"/>
							<br/>
							<input type="submit" value="设置商户号" />
					</form>
					</td>
				</tr>
				
				
                <tr>
                    <td>
                        <a href="flx_request_input.jsp">流量充值请求</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="flx_result_query_input.jsp">流量充值结果查询</a>
                    </td>
                </tr>
                 <tr>
                    <td>
                        <a href="flx_key_update_input.jsp">密钥更新</a>
                    </td>
                </tr>
            </table>
        </center>
    </body>
</html>
