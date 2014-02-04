package com.codepath.apps.twittertimeline.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel implements Serializable{
	private static final long serialVersionUID = -5201252827316379082L;
	protected transient JSONObject jsonObject;
	protected String rawJSONObject;

    public String getJSONString() {
        return getJSONObject().toString();
    }

    protected String getString(String name) {
        try {
            return getJSONObject().getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected long getLong(String name) {
        try {
            return getJSONObject().getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected int getInt(String name) {
        try {
            return getJSONObject().getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected double getDouble(String name) {
        try {
            return getJSONObject().getDouble(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected boolean getBoolean(String name) {
        try {
            return getJSONObject().getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
    
  
  protected JSONObject getJSONObject() {
  	if (jsonObject == null && rawJSONObject != null) { 
  		try {
    			  jsonObject = new JSONObject(rawJSONObject);
  		} catch (JSONException e) {
  			e.printStackTrace();
  		} 
  	}
  	return jsonObject;
  }
}