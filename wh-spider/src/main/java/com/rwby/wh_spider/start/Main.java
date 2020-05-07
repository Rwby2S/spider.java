package com.rwby.wh_spider.start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.print.DocFlavor.STRING;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.service.impl.ConsoleStoreService;
import com.rwby.wh_spider.service.impl.HBaseStoreService;
import com.rwby.wh_spider.service.impl.HttpClientDownLoadService;
import com.rwby.wh_spider.service.impl.RedisRepositoryService;
import com.rwby.wh_spider.service.impl.SpiderTest;
import com.rwby.wh_spider.util.LoadPropertyUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main {

	public static void main(String[] args) throws ParseException, IOException {
		
		SpiderTest test = new SpiderTest();
		test.setDownLoadSerivce(new HttpClientDownLoadService());
		test.setStoreService(new HBaseStoreService());
		test.setRepositoryService(new RedisRepositoryService());
		
		test.startSpider();
		System.out.println();
	}
}
