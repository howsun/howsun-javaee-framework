package org.howsun.test;

public abstract class RunTimeConsuming {

	public abstract void run();
	
	public long consume(){
		long start = System.currentTimeMillis();
		run();
		return System.currentTimeMillis() - start;
	}
}
