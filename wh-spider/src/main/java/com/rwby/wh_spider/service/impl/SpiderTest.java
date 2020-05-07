package com.rwby.wh_spider.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import com.rwby.wh_spider.entity.AccessInfo;
import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.service.IDownLoadService;
import com.rwby.wh_spider.service.IRepositoryService;
import com.rwby.wh_spider.service.IStoreService;
import com.rwby.wh_spider.util.GetJsonUtil;
import com.rwby.wh_spider.util.LoadPropertyUtil;
import com.rwby.wh_spider.util.RedisUtil;
import com.rwby.wh_spider.util.ThreadUtil;
import com.sun.tools.doclint.Checker.Flag;

import io.netty.util.internal.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SpiderTest {
	
	private IDownLoadService downLoadSerivce ;
	private IStoreService storeService;
	private IRepositoryService repositoryService;
	
	//访问个人主页发布视频的URL
	static String jsonURL1 = LoadPropertyUtil.getBilibili("jsonURL1");
	static String jsonURL2 = LoadPropertyUtil.getBilibili("jsonURL2");
	
	//访问视频JSON的URL
	static String vedioJsonURL = LoadPropertyUtil.getBilibili("vedioJsonURL");
	//static ArrayList<AccessInfo> accessInfos = new ArrayList<AccessInfo>();
	//页面提取aid 的队列（由aid可得到视频完整的url)
	private static Queue<AccessInfo> urlQueue = new ConcurrentLinkedDeque<AccessInfo>();

	//设置队列的最大存储容量
	public static final int MAX_SIZE = 30;
	public static int MAX_PAGENM = Integer.MAX_VALUE;
	public static int mask = 0;	//mask表示url已经全部插入了一遍，url不再插入第二遍
	//固定线程池
	private ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Integer.parseInt(LoadPropertyUtil.getConfig("threadNum")));
			
	/**
	 * 程序启动入口
	 */
//	public void programStart(){
//
//		Thread biliThread = new BiliThread();
//		biliThread.start();
//	}
	
	public void startSpider(){
		while(true){
			
			//数据仓库提取解析url
			final String url = repositoryService.poll();
		
			//判断url是否为空
			if(StringUtils.isNotBlank(url) || repositoryService.getLlen(new RedisUtil().lowkey) > 0){
				newFixedThreadPool.execute(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("当期第" + Thread.currentThread().getId() + "个线程");
						//下载
							//获取当前页的视频的aid(即视频的url)
						try {
							if(StringUtils.isNotBlank(url)){
								String content1 = getAccessJson(Integer.parseInt(url));
								hadnleJsonTest(content1, url);
								//此时aid都存放在lowlevel中
								System.out.println("第" + url + "页的信息已经提取");	
							}
							
							if(StringUtils.isNotBlank(url) || repositoryService.getLlen(new RedisUtil().lowkey) > 0){
								String content2 = getVedioJson();
								handleVedioJson(content2);				
							}	
							ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getConfig("millions_3")));
				
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}							
					}
				});
			}else {
				System.out.println("队列中的视频url解析完毕，请等待！");
				ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getConfig("millions_5")));
			}			
		}
	}
	
	
	// 发送请求得到up主页的json文件
		@Test
		public String getAccessJson(int pagenum) throws IOException{
			
			HttpGet req = new HttpGet(jsonURL1 + pagenum + jsonURL2);
			GetJsonUtil accessjson = new GetJsonUtil();
			return accessjson.getJson(jsonURL1, jsonURL2, req);
		}
		
			
		//利用AccessInfo中的aid，获取该视频的JSON文件
		public String getVedioJson() throws IOException{
			
			GetJsonUtil vediojson = new GetJsonUtil();
			String aid = repositoryService.pollInfo();
			if(StringUtils.isBlank(aid)){
				return "aid不存在";
			}
			
			HttpGet req = new HttpGet(vedioJsonURL + aid + jsonURL2);
			return vediojson.getJson(jsonURL1, jsonURL2, req);			
		}
		
		
		// 重载   解析主页的JSON文件，并将数据抽取到实体类AccessInfo中进行存储
		public void hadnleJsonTest(String jsonContent, String urlPage){ 
			System.out.println("开始爬取分页URL，当前是第:" + urlPage + "页");
			JSONArray array = JSONArray.fromObject(jsonContent);
			int l = array.size();
			JSONObject object = null;
			int url = Integer.parseInt(urlPage);
			int tmp = url;

			for(int i = 0; i < l; i++){
				object = array.getJSONObject(i);
				JSONObject obj1 = (JSONObject) object.get("data");
				JSONObject obj2 = (JSONObject) obj1.get("list");
				JSONObject obj3 = (JSONObject) obj1.get("page");
				JSONArray vlist = obj2.getJSONArray("vlist");
				for(int j = 0; j < 30; j++){
					JSONObject obj4 = (JSONObject) vlist.get(j);
					
					AccessInfo accessInfo = new AccessInfo();
					accessInfo.setAid(obj4.get("aid").toString());
					accessInfo.setPage(Integer.parseInt(obj3.get("count").toString()));
					
					int count = accessInfo.getPage();
					if(count % 30 != 0){
						MAX_PAGENM = count / 30 + 1;
					}else {
						MAX_PAGENM = count;
					}
					
					//向获取视频总页数的高队列中添加当前页面的url
					
					int flag = -1;  //redis中的高队列如果存在数据，则flag != 0 ,不存在数据，则flag = 0 
					if(repositoryService.getLlen(RedisUtil.highkey) > 0)
						mask = 1;
					System.out.println("-----------------mark = " + mask + "--------------------");
					if(tmp <= 20  && mask == 0){
						flag = 0;
					}
					if(flag == 0){
						while (repositoryService.getLlen(new RedisUtil().highkey) <= MAX_PAGENM - 1 && flag != -1) {
							String urlString = Integer.toString(url + 1);
							if(url + 1 > 1 && url < MAX_PAGENM)
								repositoryService.addHighLevel(urlString);
							mask = 1;
							url++;			
							if(url == MAX_PAGENM + 1){
								flag = -1;
							}
						}
					}			
					try {
						boolean isTrue = repositoryService.addLowLevel(accessInfo.getAid().toString());
						if(isTrue){
							System.out.println("***第" + j + "次，"+ urlPage + "数据插入成功" + accessInfo.getAid());
						}else{
							System.out.println("数据插入失败");
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}		
		}
		
	//处理视频的JSON文件，并存放在Bilibili实体类中
	public void handleVedioJson(String jsonContent){
		
		JSONArray array = JSONArray.fromObject(jsonContent);
		int l = array.size();
		JSONObject object = null;
		
		for(int i = 0; i < l; i++){		
			object = array.getJSONObject(i);
			Bilibili bilibili = this.downloadPage(object);			
			SpiderTest.this.storePageInfo(bilibili);
			System.out.println("-----------------------");
		}
	} 
	
	public boolean judgeIsBlank() {
		
		if(repositoryService.getLlen(new RedisUtil().highkey) == 0)
			return true;
		else {
			return false;
		}
	}
	
	/**
	 * 下载页面
	 * @param url
	 * @return
	 */
	public Bilibili downloadPage(JSONObject object) {
		
		return this.downLoadSerivce.download(object);
	}
		
	/**
	 * 存储页面信息
	 * @return
	 */
	public void storePageInfo(Bilibili bilibili){
		
		this.storeService.store(bilibili);
	}
	
	public IDownLoadService getDownLoadSerivce() {
		return downLoadSerivce;
	}

	public void setDownLoadSerivce(IDownLoadService downLoadSerivce) {
		this.downLoadSerivce = downLoadSerivce;
	}
	
	public void setStoreService(IStoreService storeService) {
		this.storeService = storeService;
	}
	
	public IRepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(IRepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
}
