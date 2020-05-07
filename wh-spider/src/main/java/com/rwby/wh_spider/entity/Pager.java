package com.rwby.wh_spider.entity;

import java.util.List;

import org.fusesource.leveldbjni.All;

public class Pager<T> {

	public int curPageNo; // 当前页
	public int pageSize; //每页显示的记录数
	public int rowsCount; // 记录行数
	public int pageCount; //页数
	public int prePageNO; // 前一页
	public int nextPageNO; // 后一页
	public List<T> list;
 	
	
	public List<T> getList(){
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public Pager(int allCount, int offset, int pageSize, int curPageNO) {
		this.rowsCount = allCount;
		if(offset > allCount){
			offset = allCount;
		}
		if(curPageNO < 1)
			curPageNO = 1;
		if(curPageNO > (int)Math.ceil((double)allCount / pageSize))
			curPageNO = (int)Math.ceil((double)allCount / pageSize);
		this.curPageNo = curPageNO;
		this.pageSize = pageSize;
		
		this.pageCount = (int) Math.ceil((double)allCount / pageSize);
		this.prePageNO = previous();
		this.nextPageNO = next();
	}
	
	public Pager(int allCount, int offset, int pageSize){
		this.rowsCount = allCount;
		if(offset > allCount){
			offset = allCount;
		}
		
	}
			
	public Pager(){
		
	}

	public int previous(){
		return (curPageNo - 1 < 1) ? 1: curPageNo - 1;
	}
	
	public int next() {
		return (curPageNo + 1 < last()) ? curPageNo : last();
	}
	
	public int frist(){
		return 1;
	}
	
	public int last(){
		return list.size() - 1;
	}
	
	public int getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPrePageNO() {
		return prePageNO;
	}

	public void setPrePageNO(int prePageNO) {
		this.prePageNO = prePageNO;
	}

	public int getNextPageNO() {
		return nextPageNO;
	}

	public void setNextPageNO(int nextPageNO) {
		this.nextPageNO = nextPageNO;
	}
	
	
}
