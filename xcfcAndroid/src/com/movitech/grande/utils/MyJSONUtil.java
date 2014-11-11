package com.movitech.grande.utils;

import org.json.JSONObject;

public class MyJSONUtil {
	
	public String getUpLoadImageUname(String json) throws Exception {
//		{"result":true,"value":"","objValue":
//			{"id":"aa1d443543db43779e7578e38b0fb9a9","relationId":"",
//			"oname":"222.png","uname":"d3ba220d-9c07-4f62-bad2-32e3f32c7757.png"}
//		}
		JSONObject jsonObj = new JSONObject(json);
		JSONObject obj =jsonObj.getJSONObject("objValue");
		String id;
		if (obj.has("uname")) {
			id = obj.getString("uname");
		} else {
			id = "";
		}
		return id;

	}

}
