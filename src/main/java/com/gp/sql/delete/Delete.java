package com.gp.sql.delete;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.gp.sql.BaseBuilder;
import com.gp.sql.WhereSupport;

/**
 * Delete clause setting holder
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-3
 * 
 **/
public class Delete extends WhereSupport{
	
	private String table;
	
	/**
	 * default constructor 
	 **/
	public Delete() {}
	
	/**
	 * constructor to replace the clone implementation 
	 **/
	public Delete(Delete delete) {
		this.table = delete.table;
		this.setWhere(delete.getWhere());
	}
	
	/**
	 * Set the table 
	 **/
	public void setTable(String table) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table mustn't be null");
		this.table = table;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("DELETE FROM ").append(BaseBuilder.NEW_LINE);
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table is required");
		
		builder.append(BaseBuilder.INDENT).append(table);
		
		if(null != this.getWhere() && !this.getWhere().isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT).append(this.getWhere().toString());
		}
		return builder.toString();
	}
}
