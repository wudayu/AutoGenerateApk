package com.movitech.grande.generic.interfaces;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
*
* @author: Wu Dayu
* @En_Name: David Wu
* @E-mail: wudayu@gmail.com
* @version: 1.0
* @Created Time: Jun 18, 2014 4:49:28 PM
* @Description: This is David Wu's property.
*
**/
public interface IJsonUtils {
	public Object toJSON(Object object) throws JSONException;

	public boolean isEmptyObject(JSONObject object);

	public Map<String, Object> getMap(JSONObject object, String key)
			throws JSONException;

	public Map<String, Object> toMap(JSONObject object) throws JSONException;

	@SuppressWarnings("rawtypes")
	public List toList(JSONArray array) throws JSONException;

	public String getString(JSONObject obj, String param);

	public int getInt(JSONObject obj, String param);

	public long getLong(JSONObject obj, String param);
}
