package com.gp.sql.select;

/**
 * The abstract limiter
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public abstract class Limiter {

	private int start = -1;
	
	private int size = -1;
	
	public Limiter(int start, int size) {
		this.setStart(start);
		this.setSize(size);
	}
	
	public abstract String limit(String sql);

	/**
	 * Getter of start 
	 **/
	public int getStart() {
		return start;
	}

	/**
	 * Setter of start 
	 **/
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Getter of size 
	 **/
	public int getSize() {
		return size;
	}

	/**
	 * Setter of size 
	 **/
	public void setSize(int size) {
		this.size = size;
	}
}
