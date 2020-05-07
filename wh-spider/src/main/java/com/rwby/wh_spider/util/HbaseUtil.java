package com.rwby.wh_spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import com.rwby.wh_spider.entity.Bilibili;

import net.sf.json.JSONArray;


/**
 * aid,tid,tname,pic,title,mid,mname,danmu,reply,facorite,coin，share，like
 * @author Administrator
 *
 */
/**
 * DDL：
 * 1.判断表是否存在
 * 2.创建表
 * 3.创建命名空间
 * 4.删除表
 * <p>
 * DML:
 * 5.插入数据
 * 6.查数据(get)
 * 7.查数据(scan)
 * 8.删除数据
 *
 */
public class HbaseUtil {
	
	/**
	 * HBASE 表名称
	 */
	public static final String TABLE_NAME = "tvcount";
	/**
	 * 列簇1
	 */
	public static final String COLUMNFAMILY_1 = "tvinfo";
	/**
	 * 列簇1中的列
	 */
	public static final String COLUMNFAMILY_1_AID = "aid";
	public static final String COLUMNFAMILY_1_TID = "tid";
	public static final String COLUMNFAMILY_1_TNAME = "tname";
	public static final String COLUMNFAMILY_1_PIC = "pic";
	public static final String COLUMNFAMILY_1_TITLLE = "title";
	public static final String COLUMNFAMILY_1_VIEW = "view";
	public static final String COLUMNFAMILY_1_MID = "mid";
	public static final String COLUMNFAMILY_1_MNAME = "mname";
	public static final String COLUMNFAMILY_1_DANMAKU = "danmaku";
	public static final String COLUMNFAMILY_1_REPLY = "reply";
	public static final String COLUMNFAMILY_1_FAVORITE = "favorite";
	public static final String COLUMNFAMILY_1_COIN = "coin";
	public static final String COLUMNFAMILY_1_SHARE = "share";
	public static final String COLUMNFAMILY_1_LIKE = "like";
		
	private static Connection connection = null;
	private static Admin admin = null;
	
	static{
				
		try {
			Configuration configuration = HBaseConfiguration.create();
			configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
			// 2.创建连接对象
			connection = ConnectionFactory.createConnection(configuration);
			// 3.创建Admin对象
			admin = connection.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * rowFilter的使用
	 * 
	 * @param tableName
	 * @param reg
	 * @throws Exception
	 */
	public void getRowFilter(String tableName, String reg) throws Exception {
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		// Filter
		RowFilter rowFilter = new RowFilter(CompareOp.NOT_EQUAL,
				new RegexStringComparator(reg));
		scan.setFilter(rowFilter);
		//ResultScanner scanner = hTable.getScanner(scan);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(new String(result.getRow()));
		}
	}
	
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return 
	 */
	public static boolean isTableExist(String tableName){
		
		boolean exists = false;
		try {
			exists = admin.tableExists(TableName.valueOf(tableName));
			admin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exists;
	}
	
	
	/**
	 *  创建表	
	 *  
	 * @param tableName
	 * @param cfs	列族可以是一个也可以是多个，所以定义可变形参
	 * @throws IOException
	 */
	public static void createTable(String tableName, String...  cfs) throws IOException{
		 
		// 1.判断是否存在列族信息
		if (cfs.length <= 0) {
			System.out.println("请设置列族信息!");
			return;
		}
		
		// 2.判断表是否存在
		if (isTableExist(tableName)) {
			System.out.println(tableName + "表已存在!");
			return;
		}
		
		//3.创建表描述器
		HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
		
		//4.循环添加列族信息
		for (String cf : cfs) {
			
			//5.创建列族描述器
			HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
			
			
			//6.添加具体的列族信息
			hTableDescriptor.addFamily(hColumnDescriptor);
		}
		//7.创建表
		admin.createTable(hTableDescriptor);
	}
	
	/**
	 * 删除表
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	public static void dropTable(String tableName) throws IOException {
		
		//1.判断表是否存在
		if(!isTableExist(tableName)){
			System.out.println(tableName + "表不存在");
			return;
		}
		
		//2.使表下线
		admin.disableTable(TableName.valueOf(tableName));
		
		//3.删除表
		admin.deleteTable(TableName.valueOf(tableName));
	}
	
	/**
	 * 创建命名空间
	 * 
	 * @param ns namespace
	 */
	public static void createNameSpace(String ns) {
		
		//1.创建命名空间描述器
		NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(ns).build();
		
		//1.创建命名空间
		try {
			admin.createNamespace(namespaceDescriptor);
		} catch (NamespaceExistException e) {
			System.out.println(ns + "命名空间已存在！"); 
		}catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//向表中插入数据
	/**
	 * 
	 * @param tableName  表名
	 * @param rowKey	
	 * @param cf	列族
	 * @param cn	列名
	 * @param value
	 * @throws IOException
	 */
	public static void putData(String tableName, String rowKey, String cf, String cn,
			String value) throws IOException{
		
		//1.获取表对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		
		//2.创建put对象
		Put put = new Put(Bytes.toBytes(rowKey));
		
		//3.给put对象赋值
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(value));

		
		//4.插入数据
		table.put(put);
		
		//5.关闭表连接
		table.close();
	}
	
	/**
	 * 获取数据（get方法）
	 * @param tableName
	 * @param rowKey
	 * @param cf	Column Family
	 * @param cn	Column Names
	 * @throws IOException
	 */
	public static void getData(String tableName, String rowKey, String cf, String cn) throws IOException{
		
		//1.获取表对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		
		//2.创建get对象
		Get get = new Get(Bytes.toBytes(rowKey));
		
			//2.1指定获取的列族
		//get.addFamily(Bytes.toBytes(cf));
		
			//2.2列族和列（列不能抛开列族单独存在）
		get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));
		
			//2.3获取数据的版本数
		get.setMaxVersions(5);
		//3.获取数据
		Result result = table.get(get);
		
		//4.解析result，并打印
		for (Cell cell : result.rawCells()) {
			
			//5.打印数据
			//byte[] cloneFamily = CellUtil.cloneFamily(cell);
			System.out.println("CF:" + Bytes.toString(CellUtil.cloneFamily(cell)) +
					"，CN:" + Bytes.toString(CellUtil.cloneQualifier(cell)) + 
					"，Value:" + Bytes.toString(CellUtil.cloneValue(cell)));
		}
		//6.关闭表连接
		table.close();
	}
		
	/**
	 * 
	 * @param tableName
	 * @param family	列簇
	 * @param qualifier	列名
	 * @throws IOException
	 */
	public static void scanTable(String tableName) throws IOException{
		
		//1.获取表对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		
		//2.构建Scan对象
		Scan scan = new Scan();
		//scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		
		//3.扫描表
		ResultScanner resultScanner= table.getScanner(scan);
		
		//4.解析resultScannner
		for (Result result : resultScanner) {
		
			//5.解析result并打印
			for(Cell cell :result.rawCells()){
				
				//6.打印数据
				System.out.println("RowKey:" + Bytes.toString(CellUtil.cloneRow(cell)) + 
						",CF:" + Bytes.toString(CellUtil.cloneFamily(cell)) +
						"，CN:" + Bytes.toString(CellUtil.cloneQualifier(cell)) + 
						"，Value:" + Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
		
		//7.关闭表连接
		table.close();
	}
	
	/**
	 * 删除数据
	 * 
	 * @param tableName
	 * @param rowKey	
	 * @param cf	Column Family
	 * @param cn	Column Name
	 * @throws IOException
	 */
	public static void deleteData(String tableName, String rowKey, String cf, String cn) throws IOException{
		
		//1.获取表对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		
		//2.构建delete对象
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		
			//2.1.设置删除的列
		delete.addColumns(Bytes.toBytes(cf), Bytes.toBytes(cn));//删除列中所有的版本 √（建议使用）
		//delete.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));	//删除最新版本，但保留以前的版本×
		
			//2.2删除指定的列族
		//delete.addFamily(Bytes.toBytes(cf));
		
		//3.执行删除操作
		table.delete(delete);  	//如果2.x什么都不加，则执行的是rowkey级别的删除
		
		//4.关闭表连接
		table.close();
		
		System.out.println("删除成功");
	}
	
	/**
	 * 查询所有表名
	 * @return
	 * @throws Exception
	 */
	public List<String> getALLTable() throws Exception {
		ArrayList<String> tables = new ArrayList<String>();
		if (admin != null) {
			HTableDescriptor[] listTables = admin.listTables();
			if (listTables.length > 0) {
				for (HTableDescriptor tableDesc : listTables) {
					tables.add(tableDesc.getNameAsString());
					System.out.println(tableDesc.getNameAsString());
				}
			}
		}
		return tables;
	}
	
	/**
	 * 获取视频详细信息
	 * @param tableName
	 * @param row
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static Bilibili get(String tableName, String row) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(row.getBytes());
		Bilibili bilibili = null;
		try {
			Result result = table.get(get);
			KeyValue[] raw = result.raw();
			System.out.println(raw.length);
			if (raw.length == 13) {
				bilibili = new Bilibili();
				bilibili.setAid(row);
				bilibili.setCoin(new String(raw[0].getValue()));
				bilibili.setDanmaku(new String(raw[1].getValue()));
				bilibili.setFavorite(new String(raw[2].getValue()));
				bilibili.setLike(new String(raw[3].getValue()));
				bilibili.setMid(new String(raw[4].getValue()));
				bilibili.setAuthor(new String(raw[5].getValue()));
				bilibili.setPic(new String(raw[6].getValue()));
				bilibili.setReply(new String(raw[7].getValue()));
				bilibili.setShare(new String(raw[8].getValue()));
				bilibili.setTid(new String(raw[9].getValue()));
				bilibili.setTitle(new String(raw[10].getValue()));
				bilibili.setTname(new String(raw[11].getValue()));
				bilibili.setView(new String(raw[12].getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bilibili;
	}
	 
	/**
	 * 查询某列某个字段的所有版本的数据
	 * get 'student','1001',{COLUMN=>'info:name',VERSIONS=>3}
	 * @param tableName
	 * @param rowkey
	 * @param family	列簇
	 * @param qualifier	列名
	 * @return
	 */
	public static List<Map<String,String>> getCellMoreVersion(String tableName, String family, String qualifier, String rowkey){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		try {
			Table table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowkey));
			get.setMaxVersions();
			Result result = table.get(get);
			List<Cell> columnCells = result.getColumnCells(Bytes.toBytes(family), Bytes.toBytes(qualifier));
			for(int i = 0; i < columnCells.size(); i++)
			{
				Cell cell = columnCells.get(i);
				long timesramp = cell.getTimestamp();
				Map<String, String> map = new HashMap<String, String>();
				map.put("time", DateUtil.formatDate(new Date(timesramp)));
				map.put("value", new String(cell.getValue()));
				list.add(map);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}
	
	/**
	 * 关闭资源
	 */
	public static void close(){
		
		if(admin != null){
			try {
				admin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//	weifuwu,shiwu duoxiancheng 
	
	public static void main(String[] args) throws IOException {
		
		List<Map<String, String>> versionList = getCellMoreVersion(HbaseUtil.TABLE_NAME,HbaseUtil.COLUMNFAMILY_1,"view","4523597");
		//生成时间轴
		List<String> categories = new ArrayList<String>();
		List<Map<String,Object>> outerList = new ArrayList<Map<String,Object>>();
		List<Integer> innerList = new ArrayList<Integer>();
		Map<String, Object> innerMap = new HashMap<String, Object>();
		StringBuffer xdata = new StringBuffer();
		int size = versionList.size();
		for(int i = size - 1; i >= 0; i--){
			Map<String, String> map = versionList.get(i);
			innerList.add(Integer.parseInt(map.get("value").replaceAll(",", "")));
			xdata.append(map.get("time"));
			categories.add(map.get("time"));
			if(i > 0){
				xdata.append(",");
			}
		}
		innerMap.put("name", "view");
		innerMap.put("data", innerList);
		outerList.add(innerMap);
		
		//按照highchart格式拼接数据
		Map<String, List> finalMap = new HashMap<String, List>();
		finalMap.putIfAbsent("categories", categories);
		finalMap.put("series", outerList);
		JSONArray.fromObject(finalMap.toString());	
		
		//Bilibili bilibili = get(HbaseUtil.TABLE_NAME, "9999121");
		//System.out.println(bilibili.toString());
		//System.out.println(isTableExist("stu5"));
		
		//2.创建表测试
		//createTable("stu5", "info1","info2");
		
		//3.删除表测试
	//	dropTable("stu5");
		
		//4. 创建命名空间测试
		//createNameSpace("0408");
		//System.out.println(isTableExist("stu5"));
		
		//5.创建数据测试
		//putData("stu5", "1002", "info2", "name", "sunying");
		
		//6.获取单行数据测试
//		getData("student", "1001", "info", "name");
		
		//7. 测试扫描数据
		//scanTable("student");
		
		//8.测试删除
		//deleteData("stu5", "1002", "info1", "sex");
		//关闭资源
		close();
		
		//两军问题。，拜占庭将军问题
	}
}
