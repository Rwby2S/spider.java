package com.rwby.wh_spider.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rwby.wh_spider.entity.Bilibili;
import com.rwby.wh_spider.entity.Pager;
import com.rwby.wh_spider.service.IPageService;
import com.rwby.wh_spider.util.IDGenerator;
import com.rwby.wh_spider.util.PagerUtil;
import com.rwby.wh_spider.util.SolrUtil;

@Service("pageService")
public class PageService implements IPageService {

	
	@Override
	public Pager<Bilibili> getPageByCondition(String pageIndex, int pageSize, Map<String, Object> map) {
		String search = (String)map.get("search");
		//当前页校验
		int curPageNo = IDGenerator.getInt(pageIndex);
		//总记录数
		int allCount = SolrUtil.getCount(search);
		//偏移量
		int offset = PagerUtil.getOffset(allCount,curPageNo,pageSize);
		
		map.put("offset", offset);
		map.put("pageSize", pageSize);
		//视频列表信息
		List<Bilibili> list = null;
		
		try {
			list = SolrUtil.search(search, offset, pageSize, "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//封装分页对象
		Pager<Bilibili> pager = new Pager<Bilibili>(allCount, offset, pageSize, curPageNo);
		pager.setList(list);
		
		return pager;
	}
	

}
