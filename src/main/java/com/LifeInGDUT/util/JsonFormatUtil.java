package com.LifeInGDUT.util;

public class JsonFormatUtil {
	public static String JsonFormat(Object o){
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(o);
		System.out.println(json.toString());
		if("[]".equals(json.toArray())){
			return null;
		}else{
			return json.toString();
		}
	}
	
}
