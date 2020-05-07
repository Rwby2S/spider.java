package com.rwby.wh_spider.service.impl;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.service.IDownLoadService;

import net.sf.json.JSONObject;
/**
 * 页面下载实现类
 * @author wh
 *
 */
public class HttpClientDownLoadService implements IDownLoadService {
//pic tid title tname
	public Bilibili download(JSONObject object) {
		
		JSONObject obj1 = (JSONObject) object.get("data");
		JSONObject obj2 = (JSONObject) obj1.get("stat");
		
		Bilibili bilibili = new Bilibili();
		bilibili.setTid(obj1.get("tid").toString());
		bilibili.setTname(obj1.get("tname").toString());
		bilibili.setPic(obj1.get("pic").toString());
		bilibili.setTitle(obj1.get("title").toString());
		
		JSONObject obj4 = (JSONObject) obj1.get("owner");
		bilibili.setMid(obj4.get("mid").toString());
		bilibili.setAuthor(obj4.get("name").toString());
		bilibili.setAid(obj2.get("aid").toString());
		bilibili.setView(obj2.get("view").toString());
		bilibili.setDanmaku(obj2.get("danmaku").toString());
		bilibili.setReply(obj2.get("reply").toString());
		bilibili.setFavorite(obj2.get("favorite").toString());
		bilibili.setCoin(obj2.get("coin").toString());
		bilibili.setShare(obj2.get("share").toString());
		bilibili.setLike(obj2.get("like").toString());
		
		return bilibili;
	}
}
