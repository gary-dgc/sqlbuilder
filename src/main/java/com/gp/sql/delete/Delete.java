package com.gp.sql.delete;

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
	 * Set the table 
	 **/
	public void setTable(String table) {
		this.table = table;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM ");
		builder.append(table);
		
		if(null != this.getWhere() && !this.getWhere().isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ");
			builder.append(this.getWhere().toString());
		}
		return builder.toString();
	}
}
