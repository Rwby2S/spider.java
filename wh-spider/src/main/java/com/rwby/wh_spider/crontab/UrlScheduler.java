package com.rwby.wh_spider.crontab;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;



/**
 * 分类url定时抓取job
 *
 */
public class UrlScheduler {
	public static void main(String[] args) {
		try {
			//获取默认调度器
			Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			//开启调度器
			defaultScheduler.start();
			
			//被调度的任务
			JobDetail jobDetail = new JobDetail("url-job", Scheduler.DEFAULT_GROUP, AddUrlJob.class);
			
			//定时执行任务  			
			CronTrigger trigger = new CronTrigger("url-job", Scheduler.DEFAULT_GROUP, "00 34 10 * * ?");
			//添加调度任务
			defaultScheduler.scheduleJob(jobDetail , trigger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
