package com.rwby.wh_spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则表达式匹配工具
 * @author wh
 *
 */

public class RegexUtil {

	public static String getPageInfoByRegex(String content, Pattern pattern, int gropNo){
		
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			return matcher.group(0).trim();
		}
		return "0";
	}
}
