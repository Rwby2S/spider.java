package com.rwby.wh_spider.service.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.rwby.wh_spider.entity.AccessInfo;
import com.rwby.wh_spider.service.IRepositoryService;
import org.apache.commons.lang.StringUtils;

/**
 * url仓库实现类Queue
 * @author wh
 *
 */
public class QueueRepositoryService implements IRepositoryService {

	//高优先级队列
	private Queue<String> highLevelQueue = new ConcurrentLinkedDeque<String>();
	//低优先级队列
	private Queue<String> LowLevelQueue = new ConcurrentLinkedDeque<String>();
	
	public String poll(){
		
		//先解析高优先级队列，然后再解析低优先级队列
		String url = highLevelQueue.poll();
		if(StringUtils.isBlank(url)){
			url = LowLevelQueue.poll();
		}
		return url;
	}

	public void addHighLevel(String url) {
		// TODO Auto-generated method stub
		this.highLevelQueue.add(url);
	}

	public boolean addLowLevel(String url) {
		// TODO Auto-generated method stub
		this.LowLevelQueue.add(url);
		return true;
	}

	public String pollInfo() {
		// TODO Auto-generated method stub
		return null;
	}


	public Long getLlen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
