package com.ecloud.deploy.common;

import com.alibaba.fastjson.JSON;

public class SerializerUtil {

 //private static KryoSerialize kryoSerialization = new KryoSerialize(KryoPoolFactory.getKryoPoolInstance());
    
    public static  byte[] encode(Object t){
    	/*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	try {
			kryoSerialization.serialize(byteArrayOutputStream,t);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	SerializerFeature features ;
		JSON.toJSONBytes(t);
    	return byteArrayOutputStream.toByteArray();*/
    	return JSON.toJSONBytes(t);
    }
    
    
    public static <T> T decode(byte[] content,Class<T> clazz){
    /*	try {
			Object obj = kryoSerialization.deserialize(new ByteArrayInputStream(content));
			return clazz.cast(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    	
		return JSON.parseObject(content, clazz);
		
    }
}
