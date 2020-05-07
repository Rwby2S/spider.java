package com.rwby.wh_spider.solr;

import org.apache.commons.lang.StringUtils;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.util.HbaseUtil;
import com.rwby.wh_spider.util.RedisUtil;
import com.rwby.wh_spider.util.SolrUtil;
import com.rwby.wh_spider.util.ThreadUtil;

public class SolrJob {
	private static final String SOLR_TV_INDEX = "solr_tv_index";
	static RedisUtil redis = new RedisUtil();
	
	
	public static void buildIndex(){
		String aid = "";
		try {
			System.out.println("开始建立索引！！！");
			HbaseUtil hbaseUtil = new HbaseUtil();
			aid = redis.poll(SOLR_TV_INDEX);
			System.out.println("aid: " + aid);
			while (!Thread.currentThread().isInterrupted()) {
				if(StringUtils.isNotBlank(aid)){
					Bilibili bilibili = hbaseUtil.get(HbaseUtil.TABLE_NAME, aid);
					if(bilibili !=null){
						SolrUtil.addIndex(bilibili);
					}
					aid = redis.poll(SOLR_TV_INDEX);
				}else{
					System.out.println("目前没有需要索引的数据，休息一会再处理！");
					ThreadUtil.sleep(5000);
				}
			}
		} catch (Exception e) {
			redis.add(SOLR_TV_INDEX, aid);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		buildIndex();
	}
}

//添加视频索引:4299204
//13
//添加视频索引:4262893
//13
//添加视频索引:4250516
//13
//添加视频索引:4241605
//13
//添加视频索引:4240322
//13
//添加视频索引:4232076
//13
//添加视频索引:4226310
