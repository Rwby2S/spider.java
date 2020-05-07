package com.rwby.wh_spider.entity;
/**
 * 取出up主页视频的aid，
 * @author wh
 *
 */
public class AccessInfo {
			
	/**
	 * 视频av号
	 */
	private String aid;
	
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	@Override
	public String toString() {
		return "AccessInfo [aid=" + aid + "]";
	}
}
