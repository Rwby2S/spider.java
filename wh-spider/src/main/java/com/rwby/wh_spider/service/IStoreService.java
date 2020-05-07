package com.rwby.wh_spider.service;

import com.rwby.wh_spider.entity.Bilibili;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据存储接口
 * @author wh
 *
 */
public interface IStoreService {

	public void store(Bilibili bilibili);
	
//	public Bilibili store(JSONArray array);
	
}
