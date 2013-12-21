
package org.howsun.dao;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.howsun.core.exception.AssertException;
import org.howsun.util.Randoms;

/**
 * 功能描述：自定义实体ID，
 * 目前暂定格式为：              SSSSSSSS MM HASH
 * Long.mv:92233 72036854 77 5807
 * 目标：
 * 1.隐含时间信息，省去创建时间字段
 * 2.具有顺序
 * 3.前端不易测
 * 
 * 
 * 参考Bson的 @see org.bson.types.ObjectId
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class EntityId {

	private static AtomicInteger nextInc = new AtomicInteger(10);
	
	/**从2010_10_10_10_10_10开始*/
	public static final long START_SECOEND = 1286676610;
	
	private long id = -1;
	
	private int hash;

	private Calendar time;

	private int module;
	
	private boolean lastNot4;

	//private int machine;

	//反系列化
	public EntityId(){
	}
	
	//系列化
	public EntityId(int module){
		this(module, false);
	}
	public EntityId(int module, boolean latsNot4){
		if(module < 0 || module >  99){
			throw new AssertException("模块编号必须是1-99之间的数字");
		}
		this.hash = nextInc.getAndIncrement() & 0xFFF;
		this.module = module;
		this.lastNot4 = latsNot4;
		generatedId();
	}

	

	/**
	 * 创建主键
	 */
	private void generatedId(){
		hash += Randoms.nextInt(5900);
		
		id += (System.currentTimeMillis()/1000 - START_SECOEND) * 1000000;
		id += module *  10000;
		id += hash;
		
		if(lastNot4 && id % 10 == 4){
			id += 4;
		}
		
	}

	/**
	 * 解析主键
	 */
	public EntityId parse(long id){
		this.id = id;
		
		long TEMP = id / 1000000;
		
		this.time = Calendar.getInstance();
		time.setTimeInMillis((TEMP + START_SECOEND) * 1000L);
		
		TEMP = id % (TEMP * 1000000);
		this.module = (int) (TEMP  / 10000);
		
		this.hash = (int) ( (TEMP - this.module * 10000 ) );
		
		return this;
	}


	/**
	 *  int z = flip((int)(System.currentTimeMillis()/1000));
		long zDate = flip(z);
		zDate *= 1000;
		System.out.println(new Date(zDate));
		
	 * @param x
	 * @return
	 */
	public static int flip( int x ){
		int z = 0;
		z |= ( ( x << 24 ) & 0xFF000000 );
		z |= ( ( x << 8 )  & 0x00FF0000 );
		z |= ( ( x >> 8 )  & 0x0000FF00 );
		z |= ( ( x >> 24 ) & 0x000000FF );
		return z;
	}
	
	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public Calendar getTime() {
		return time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastNot4(boolean lastNo4) {
		this.lastNot4 = lastNo4;
	}

	public long getId(){
		return id;
	}
	
	public int getModule() {
		return module;
	}

	public static void main(String[] args) throws Exception {
		long j = 1;
		for (int i = 0; i < 10; i++) {
			Thread.sleep(1001);
			EntityId id = new EntityId(11,true);
			j = id.getId();
			System.out.println(j);
		}
		
		EntityId o = new EntityId().parse(j);
		System.out.println(DateFormat.getDateTimeInstance().format(o.getTime().getTime()));
		System.out.println(o.getModule());
		System.out.println(o.getHash());
	}

}
