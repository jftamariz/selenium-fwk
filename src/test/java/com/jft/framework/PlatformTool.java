package com.jft.framework;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class PlatformTool {

	public static Boolean isPlatform(String platform){
		
		if(getPlatform().contains(platform))
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	public static Boolean isArchitecture(String architecture){

		
		if(getArchitecture().contains(architecture))
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String getPlatform(){
		return System.getProperties().getProperty("os.name").toLowerCase();
	}
	
	public static String getArchitecture(){
		return System.getProperties().getProperty("os.arch").toLowerCase();
	}
	
	public static String getLinefromMultilineText(Object text, String matchWord){

		
		if(text.getClass() == String.class){
			String textString = (String)text;
			String [] textArray = textString.split(System.getProperty("line.separator"));
	    	for(String line : textArray){
	    	    
	    		if(line.contains(matchWord)){
	    			
	    			return line;
	    			
	    		}
	    	}
		}else if(text.getClass() == List.class){
			List<String> textList = (List<String>)text;
			for(String line : textList){
				if(line.contains(matchWord)){
					return line;
				}
			}
		}
		
		return null;
		
	}
	
	
	public static Properties loadConfigurations(){
		Properties prop = null;
		InputStream inputstream = null;
		
		try{
			prop = new Properties();
			inputstream = PlatformTool.class.getClass().getResourceAsStream("/resources/config.properties");
			if(inputstream == null){
				System.out.println("[ERROR} Property file not found.");
				return null;
			}
			
			prop.load(inputstream);
			return prop;
			
			}catch(IOException e){
				e.printStackTrace();
				return null;
			}
		
		
	}
}

