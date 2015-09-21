package com.greatwall;

import java.util.Map;

import com.google.gson.Gson;

public class StrTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "{'Code':'0','Message':'充值提交成功','TaskID':5}";
		Gson gson = new Gson();
		Map map = gson.fromJson(str, Map.class);
		System.out.println(map.get("Code"));
	}

}
