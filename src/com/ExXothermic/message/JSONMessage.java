package com.ExXothermic.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import net.minidev.json.*;
import net.minidev.json.parser.ParseException;

public class JSONMessage {
		
		private String action;
		private String id;
		private HashMap<String , Object> data;
		private HashMap<String , Object> parameters;
		
		private static final String LabelAction="action";
		private static final String LabelParameter="parameter";
		private static final String LabelId="id";
		private static final String LabelData="data";
		
		private Logger  logger;
		
		public JSONMessage()
		{
			logger= Logger.getLogger(this.getClass().getName());
			this.data=new HashMap<String, Object>();
			this.parameters=new HashMap<String, Object>();
		}
		
		public boolean parse(String body)
		{
			boolean result=Boolean.TRUE;
			try
			{
				Object obj=JSONValue.parseStrict(body);
				JSONObject array=(JSONObject)obj;				
				this.action=array.get(LabelAction).toString();
				this.id=array.get(LabelId).toString();
				//Object parameter=JSONValue.parseStrict(array.get("parameter").toString());
				this.parameters=array.get("parameter")!=null?construcHash(array.get("parameter").toString()):new HashMap<String, Object>();
				this.data=array.get("data")!=null?construcHash(array.get("data").toString()):new HashMap<String, Object>();		 
			}
			catch(ClassCastException ex)
			{
				logger.error("Error parsing JSON ",ex);
				result=Boolean.FALSE;
			} catch (ParseException ex) {
				// TODO Auto-generated catch block
				logger.error("Error parsing JSON ",ex);
				result=Boolean.FALSE;
			}
			return result;
		}
		
		
		private HashMap<String , Object> construcHash(String values) throws ParseException
		{
			HashMap<String , Object> objreturn=new HashMap<String, Object>();
			Object data=JSONValue.parseStrict(values);
			JSONObject array=(JSONObject)data;
			Iterator iter = array.keySet().iterator();

			while (iter.hasNext())
			{
				
				String key=iter.next().toString();
				objreturn.put(key, array.get(key));		
			}
			return objreturn;
		}

		
		public String getId()
		{
			return id;
		}
		public String getAction()
		{
			return action;
		}
		
		public  HashMap<String , Object> getData()
		{
			return this.data;
		}
		
		public HashMap<String , Object> getParameters()
		{
			return this.parameters;
		}
		
		public String getEnconding(String action, String id,HashMap<String,String> params)
		{
			JSONObject obj = new JSONObject();
			JSONObject paramsJSon= new JSONObject();
			obj.put("action",action);
			obj.put("id",id);
			Set set = params.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me= (Map.Entry)i.next();
				paramsJSon.put(me.getKey().toString(),me.getValue());
			}
			obj.put("parameter",JSONValue.toJSONString(paramsJSon));
			return JSONValue.toJSONString(obj);
		}
}
