package com.rwby.wh_spider.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class GetJsonUtil {
	// 发送请求得到json文件
	@Test
	public static String getJson(String jsonURL1, String jsonURL2, HttpGet req) throws IOException{
		HttpClient httpClient = HttpClients.createDefault();
		//HttpGet req = new HttpGet(jsonURL1 + tids[w] + jsonURL2 + pagenum);
		//添加请求头
		req.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		req.addHeader("Accept-Encoding", "gzip, deflate, br");
		req.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		req.addHeader("Content-Type", "	application/json; charset=utf-8");
		req.addHeader("User-Agent", 
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:74.0) Gecko/20100101 Firefox/74.0");
		CloseableHttpResponse resp = (CloseableHttpResponse) httpClient.execute(req);
		HttpEntity repEntity = resp.getEntity();
		String content = "[" + EntityUtils.toString(repEntity) + "]";
		return content;
	}//测试成功
}
