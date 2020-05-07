package com.rwby.wh_spider.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 操作redis数据库的工具类
 *  Created by dajiangtai
 *
 */
public class RedisUtil {
	
	//redis中列表key的名称
	public static String highkey = "spider.highlevel";
	public static String lowkey = "spider.lowlevel";
	
	public static String starturl="start.url";
	JedisPool jedisPool = null;
	public RedisUtil(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(10);
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxWaitMillis(10000);
		poolConfig.setTestOnBorrow(true);
		jedisPool = new JedisPool(poolConfig, "hadoop102", 6379);
	}
	
	/**
	 * 查询
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key,int start,int end){
		Jedis resource = jedisPool.getResource();
		
		List<String> list = resource.lrange(key, start, end);
		jedisPool.returnResourceObject(resource);
		return list;
		
	}
	
	/**
	 * 添加
	 * @param Key
	 * @param url
	 */
	public void add(String Key, String url) {
		Jedis resource = jedisPool.getResource();
		resource.lpush(Key, url);
		jedisPool.returnResourceObject(resource);
	}
	
	/**
	 * 获取出队 元素
	 * @param key
	 * @return
	 */
	public String poll(String key) {
		Jedis resource = jedisPool.getResource();
		String result = resource.rpop(key);
		jedisPool.returnResourceObject(resource);
		return result;
	}
	
	/**
	 * 获取列表长度
	 * @param key
	 */
	public Long GetLLEN(String key) {
		Jedis resource = jedisPool.getResource();
		Long llen = resource.llen(key);
		jedisPool.returnResourceObject(resource);
		return llen;
	}
	
	public static void main(String[] args) {
		RedisUtil redisUtil = new RedisUtil();		
		String url = "1";
		//redisUtil.add(starturl, url);
		redisUtil.add(highkey, url);


	}
	
}