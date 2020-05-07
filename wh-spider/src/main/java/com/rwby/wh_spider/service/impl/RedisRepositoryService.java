package com.rwby.wh_spider.service.impl;

import org.apache.commons.lang.StringUtils;

import com.rwby.wh_spider.service.IRepositoryService;
import com.rwby.wh_spider.util.RedisUtil;
import com.sun.tools.doclint.Checker.Flag;

public class RedisRepositoryService implements IRepositoryService{

	RedisUtil redisUtil = new RedisUtil();
	
	public String poll() {
		// TODO Auto-generated method stub
		String url = redisUtil.poll(RedisUtil.highkey);
		return url;
	}

	public void addHighLevel(String url) {
		
		if(redisUtil.GetLLEN(url) <= SpiderTest.MAX_PAGENM) {
			redisUtil.add(RedisUtil.highkey, url);
		}else {
			return;
		}		
	}

	public boolean addLowLevel(String url) {
		
		redisUtil.add(RedisUtil.lowkey, url);
		if (getLlen("spider.lowlevel") > 0) {
			return true;
		}
		return false;
	}

	public Long getLlen(String key){
		
		return redisUtil.GetLLEN(key);
		
	}
	
	public String pollInfo() {
		// TODO Auto-generated method stub
		return redisUtil.poll(RedisUtil.lowkey);

	}
}
