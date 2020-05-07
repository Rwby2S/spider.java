package com.rwby.wh_spider.util;

public class PagerUtil {
	/**
	 * 
	 * @param allCount 总记录数
	 * @param curPageNo	当前页数
	 * @param pageSize	每页的数据条数
	 * @return offset 下一页的数据在列表中的位置
	 * 第一页  0-9
	 * 第二页 10-19
	 * 第三页 20-29
	 */
	public static int getOffset(int allCount,int curPageNo, int pageSize){
		
		int offset = 0;
		if(curPageNo == 1)
			offset = 0;
		if(curPageNo * pageSize <= allCount){
			offset = (curPageNo - 1) * pageSize;
		}else {
			offset = allCount;
		}
		return offset;
	}
}
