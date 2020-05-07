package com.rwby.wh_spider.entity;

import org.apache.solr.client.solrj.beans.Field;

public class Bilibili {

	/**
	 * 1.视频av号
	 */
	@Field
	private String aid;
	
	/**
	 * 2.视频类别号
	 */
	@Field
	private String tid;
	
	/**
	 * 3.视频标题
	 */
	@Field
	private String title;
	
	/**
	 * 4.视频封面图
	 */
	@Field
	private String pic;
	
	/**
	 * 5.视频类别名称
	 */
	@Field
	private String tname;
	
	/**
	 * 6.收藏数
	 */
	@Field
	private String favorite;
	
	/**
	 * 7.硬币数
	 */
	@Field
	private String coin;
	
	/**
	 * 8.视频作者
	 */
	@Field
	private String author;
	
	/**
	 * 9.视频播放量
	 */
	@Field
	private String view;
	
	/**
	 * 10.分享次数
	 */
	@Field
	private String share;
	
	/**
	 * 11.点赞
	 */
	@Field
	private String like;

	/**
	 * 12.视频所属作者id
	 */
	@Field
	private String mid;
	
	/**
	 * 13.弹幕数
	 */
	@Field
	private String danmaku;
	
	/**
	 * 14.评论数
	 */
	@Field
	private String reply;
		

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getDanmaku() {
		return danmaku;
	}

	public void setDanmaku(String danmaku) {
		this.danmaku = danmaku;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Override
	public String toString() {
		return "Bilibili [aid=" + aid + ", tid=" + tid + ", title=" + title + ", pic=" + pic + ", tname=" + tname
				+ ", favorite=" + favorite + ", coin=" + coin + ", author=" + author + ", view=" + view + ", share="
				+ share + ", like=" + like + ", mid=" + mid + ", danmaku=" + danmaku + ", reply=" + reply + "]";
	}
	
	
}
