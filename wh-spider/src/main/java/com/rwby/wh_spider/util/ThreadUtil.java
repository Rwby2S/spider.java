package com.rwby.wh_spider.util;

public class ThreadUtil {

	@SuppressWarnings("static-access")
	public static void sleep(long millions){
		try {
			Thread.currentThread().sleep(millions);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
