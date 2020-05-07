package com.rwby.wh_spider.crontab;

import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rwby.wh_spider.util.RedisUtil;


/**
 * 向redis 添加分类url
 * @author wh
 *
 */
public class AddUrlJob implements Job {
	RedisUtil redisUtil = new RedisUtil();
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		List<String> list = redisUtil.lrange(RedisUtil.starturl, 0, -1);
		for(String url : list){
			redisUtil.add(redisUtil.highkey, url);
		}
	}

}
