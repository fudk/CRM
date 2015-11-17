package com.greatwall;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class StrTest {

	public static void main(String[] args) {
		System.out.println("F".matches("[A-Z]"));
		
		/*
		String str = "{'Code':'0','Message':'OK','Reports':[{'TaskID':5,'Mobile':'15914400880','Status':5,'ReportTime':'2015-09-24 18:15:18','ReportCode':'S:006:不支持广东移动'},{'TaskID':14,'Mobile':'13533914580','Status':5,'ReportTime':'2015-09-24 18:15:18','ReportCode':'S:006:不支持广东移动'}]}";
		Gson gson = new Gson();
		Map map = gson.fromJson(str, Map.class);
		System.out.println(map.get("Reports").toString());

		List list = (List) map.get("Reports");
		for(int i=0;i<list.size();i++){
			Map m = (Map)list.get(i);
			System.out.println(String.format("%.0f ", m.get("TaskID")));
			System.out.println(String.format("%.0f ", m.get("Status")));
//			System.out.println(list.get(i).getClass());
		}*/
		
		/*JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(map.get("Reports").toString());
		JsonArray jsonArray = null;
		if(el.isJsonArray()){
			jsonArray = el.getAsJsonArray();
		}

		Iterator it = jsonArray.iterator();
		while(it.hasNext()){
			JsonElement e = (JsonElement)it.next();
			//JsonElement转换为JavaBean对象
			Map field = gson.fromJson(e, Map.class);
			System.out.println(field.get("TaskID"));
		}
*/
	//		List<Map> list = gson.fromJson(str, List.class);
	//		for(int i=0;i<list.size();i++){
	//			System.out.println(list.get(i).get("TaskID"));
	//		}
}

}
