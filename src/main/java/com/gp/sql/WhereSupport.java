package com.gp.sql;

public class WhereSupport {

	private Condition where;

	/**
	 * Set the where condition 
	 **/
	public void setWhere(Condition condition) {
		this.where = condition;
	}
	
	/**
	 * Get the condition 
	 **/
	public Condition getWhere() {
		return this.where;
	}

}
