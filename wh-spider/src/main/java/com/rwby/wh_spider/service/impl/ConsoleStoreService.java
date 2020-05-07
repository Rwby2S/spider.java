package com.rwby.wh_spider.service.impl;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.service.IStoreService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据存储接口实现类
 * @author wh
 *
 */
public class ConsoleStoreService implements IStoreService {

	public void store(Bilibili bilibili){
		
		System.out.println("aid:  " + bilibili.getAid());
		System.out.println("tid:  " + bilibili.getTid());
		System.out.println("tname:  " + bilibili.getTname());
		System.out.println("pic:  " + bilibili.getPic());
		System.out.println("title:  " + bilibili.getTitle());
		System.out.println("view: " + bilibili.getView());
		System.out.println("mid:  " + bilibili.getMid());
		System.out.println("mname:  " + bilibili.getAuthor());
		System.out.println("danmu:  " + bilibili.getDanmaku());
		System.out.println("reply:  " + bilibili.getReply());
		System.out.println("facorite:  " + bilibili.getFavorite());
		System.out.println("coin:  " + bilibili.getCoin());
		System.out.println("share:  " + bilibili.getShare());
		System.out.println("like:  " + bilibili.getLike());
	}

//	public Bilibili store(JSONArray array) {
//		
//		int l = array.size();
//		JSONObject object = null;
//		Bilibili bilibili = new Bilibili();
//		
//		for(int i = 0; i < l; i++){
//			object = array.getJSONObject(i);
//			JSONObject obj1 = (JSONObject) object.get("data");
//			JSONObject obj2 = (JSONObject) obj1.get("stat");
//			bilibili.setTid(Integer.parseInt(obj1.get("tid").toString()));
//			bilibili.setTname(obj1.get("tname").toString());
//			bilibili.setPic(obj1.get("pic").toString());
//			bilibili.setTitle(obj1.get("title").toString());
//			
//			JSONObject obj4 = (JSONObject) obj1.get("owner");
//			bilibili.setMid(obj4.get("mid").toString());
//			bilibili.setAuthor(obj4.get("name").toString());
//			bilibili.setAid(Integer.parseInt(obj2.get("aid").toString()));
//			bilibili.setView(Integer.parseInt(obj2.get("view").toString()));
//			bilibili.setDanmaku(obj2.get("danmaku").toString());
//			bilibili.setReply(obj2.get("reply").toString());
//			bilibili.setFavorite(Integer.parseInt(obj2.get("favorite").toString()));
//			bilibili.setCoin(Integer.parseInt(obj2.get("coin").toString()));
//			bilibili.setShare(Integer.parseInt(obj2.get("share").toString()));
//			bilibili.setLike(Integer.parseInt(obj2.get("like").toString()));			
//		}
//		System.out.println(bilibili.toString());
//		return bilibili;
//	}
}
