package com.rwby.wh_spider.service;

import java.util.Map;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.entity.Pager;

public interface IPageService {

	//根据关键字查询视频列表信息
	public Pager<Bilibili> getPageByCondition(String pageIndex, int pageSize, Map<String,Object> map);
}
