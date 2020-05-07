package com.rwby.wh_spider.service;
/**
 * 存储 url仓库接口
 * @author wh
 *
 */
public interface IRepositoryService {
	
	public String poll();
	
	public String pollInfo();
	
	public void addHighLevel(String url);
	
	public boolean addLowLevel(String url);
	
	public Long getLlen(String key);
}
