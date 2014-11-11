package com.movitech.grande.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.movitech.grande.generic.interfaces.IJsonUtils;

/**
*
* @author: Wu Dayu
* @En_Name: David Wu
* @E-mail: wudayu@gmail.com
* @version: 1.0
* @Created Time: Jun 18, 2014 4:28:21 PM
* @Description: This is David Wu's property.
*
**/
@EBean(scope = Scope.Singleton)
public class JsonUtils implements IJsonUtils{
	
	@Override
	@SuppressWarnings("rawtypes")
	public Object toJSON(Object object) throws JSONException {
		if (object instanceof Map) {
			JSONObject json = new JSONObject();
			Map map = (Map) object;
			for (Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			JSONArray json = new JSONArray();
			for (Object value : ((Iterable) object)) {
				json.put(value);
			}
			return json;
		} else {
			return object;
		}
	}

	@Override
	public boolean isEmptyObject(JSONObject object) {
		
		if(object == null)
		{
			return true;
		}
		
		return object.names() == null;
	}

	@Override
	public Map<String, Object> getMap(JSONObject object, String key)
			throws JSONException {
		return toMap(object.getJSONObject(key));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> toMap(JSONObject object)
			throws JSONException {
		Map<String, Object> map = new HashMap();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)));
		}
		return map;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List toList(JSONArray array) throws JSONException {
		List list = new ArrayList();
		for (int i = 0; i < array.length(); i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}
	
	private Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}

	/**
	 * 获取String
	 * 
	 * @param obj
	 * @param param
	 * @return
	 */
	@Override
	public String getString(JSONObject obj, String param) {
		String result = "";
		if (obj.has(param)) {
			try {
				result = obj.getString(param);
			} catch (JSONException e) {
				Utils.debug("getString : " + e.toString());
			}
		}
		return result;
	}

	/**
	 * 获取Int
	 * 
	 * @param obj
	 * @param param
	 * @return
	 */
	@Override
	public int getInt(JSONObject obj, String param) {
		int result = 0;
		if (obj.has(param)) {
			try {
				result = obj.getInt(param);
			} catch (JSONException e) {
				Utils.debug("getInt : " + e.toString());
			}
		}
		return result;
	}

	/**
	 * 获取Long
	 * 
	 * @param obj
	 * @param param
	 * @return
	 */
	@Override
	public long getLong(JSONObject obj, String param) {
		long result = 0;
		if (obj.has(param)) {
			try {
				result = obj.getLong(param);
			} catch (JSONException e) {
				Utils.debug("getLong : " + e.toString());
			}
		}
		return result;
	}
}
