package com.gp.sql.delete;

import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;

/**
 * Delete clause setting holder
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-3
 * 
 **/
public class Delete {
	
	private String table;
	
	private Condition where;

	/**
	 * Set the table 
	 **/
	public void setTable(String table) {
		this.table = table;
	}

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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM ");
		builder.append(table);
		
		if(null != where && !where.isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ");
			builder.append(where.toString());
		}
		return builder.toString();
	}
}
