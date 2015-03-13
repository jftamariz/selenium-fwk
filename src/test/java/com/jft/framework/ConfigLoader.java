package com.jft.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigLoader {

	private static final String pathDir = new java.io.File("").getAbsolutePath();
	private static final String CONFIGFILE = pathDir+ "/src/test/resources/config.properties";
	
	public static String getProperty(String key){
		
		Properties prop = new Properties();
		String value = "";
	
		try{

	    	prop.load(ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties"));

		}catch(IOException e){
			System.err.println(" [Error] Config File:  Can't open it.");
			e.printStackTrace();
		}
		
		if (key != null)
		{
			value = prop.getProperty(key);
		}
		
		
		return value;
	}
}

