package com.ecloud.framework.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class PropertiesReader{
    private String filePath;
    private Properties configProperties;
    public PropertiesReader(String filePath)
    {
        this.filePath = filePath; 
        loadProperties();
    }

    private void loadProperties()
    {
    	URL url = null;
		try {
			url = new URL(this.filePath);
		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try
        {
            configProperties = new Properties();
            configProperties.load(url.openStream());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
    public static String getProperty(String path,String attribute){
    	URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties  properties =null;
        properties = new Properties();
        try {
			properties.load(url.openStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String returnStr = "";
    	if(attribute!=null && !"".equalsIgnoreCase(attribute) 
    			&& properties.containsKey(attribute))
    	{
    		try
    		{
    			returnStr = new String(properties.getProperty(attribute).getBytes("iso8859-1"),"GBK");
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	return returnStr;
    }

	public String getProperty(String attribute) {
		  String returnStr = "";
	        if(attribute!=null && !"".equalsIgnoreCase(attribute) 
	        	&& configProperties.containsKey(attribute))
	        {
	            try
	            {
	                returnStr = new String(configProperties.getProperty(attribute).getBytes("iso8859-1"),"GBK");
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	                
	            }
	        }
	        return returnStr;
	}
    

}
