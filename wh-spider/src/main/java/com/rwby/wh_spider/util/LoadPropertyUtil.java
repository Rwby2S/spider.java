package com.rwby.wh_spider.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 读取配置文件属性工具
 * @author wh
 *
 */
public class LoadPropertyUtil {

	//读取Bilibili配置文件
	public static String getBilibili(String key){
		String value = "";
		Locale locale = Locale.getDefault();
		try{
			ResourceBundle localResource = ResourceBundle.getBundle("Bilibili",
					locale);
			value = localResource.getString(key);
		}catch (MissingResourceException e) {
			
			value = "";
		}
		return value;
	}
	
	//读取公共配置文件
		public static String getConfig(String key){
			String value = "";
			Locale locale = Locale.getDefault();
			try{
				ResourceBundle localResource = ResourceBundle.getBundle("config",
						locale);
				value = localResource.getString(key);
			}catch (MissingResourceException e) {
				
				value = "";
			}
			return value;
		}
		
		public static void main(String[] args) {
			System.out.println(getConfig("threadNum"));
		}
}
