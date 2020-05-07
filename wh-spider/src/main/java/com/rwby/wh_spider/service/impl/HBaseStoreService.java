package com.rwby.wh_spider.service.impl;

import java.io.IOException;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.service.IStoreService;
import com.rwby.wh_spider.util.HbaseUtil;
import com.rwby.wh_spider.util.RedisUtil;

public class HBaseStoreService implements IStoreService {

	HbaseUtil hbaseUtil = new HbaseUtil();
	RedisUtil redisUtil = new RedisUtil();
	
	public void store(Bilibili bilibili) {
		// TODO Auto-generated method stub
		String aid = bilibili.getAid();
//		try {
//			hbaseUtil.deleteData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, "sex");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		redisUtil.add("solr_tv_index", aid);
		try {
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_TID, bilibili.getTid());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_TNAME, bilibili.getTname());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_PIC, bilibili.getPic());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_TITLLE, bilibili.getTitle());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_MID, bilibili.getMid());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_MNAME, bilibili.getAuthor());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_VIEW, bilibili.getView());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_DANMAKU, bilibili.getDanmaku());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_REPLY, bilibili.getReply());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_FAVORITE, bilibili.getFavorite());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_COIN, bilibili.getCoin());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_SHARE, bilibili.getShare());
			hbaseUtil.putData(HbaseUtil.TABLE_NAME, aid, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_LIKE, bilibili.getLike());
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}

}
