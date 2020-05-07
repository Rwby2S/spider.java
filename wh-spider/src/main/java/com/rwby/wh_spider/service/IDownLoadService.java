package com.rwby.wh_spider.service;
/**
 * 页面下载接口
 * @author wh
 *
 */

import com.rwby.wh_spider.entity.Bilibili;

import net.sf.json.JSONObject;

public interface IDownLoadService {

	public Bilibili download(JSONObject object);
}
