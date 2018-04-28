package com.gp.sql.insert;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;

/**
 * Insert clause setting builder
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-2
 **/
public class Insert {
	
	private String table;
	
	List<String> columns = Lists.newArrayList();
	
	List<Object> values = Lists.newArrayList();
	
	private String select ;
	
	/**
	 * Set table 
	 **/
	public void setTable(String table) {
		this.table = table;
	}
	
	/**
	 * Set the column and value to be assigned 
	 **/
	public void setColumn(String column, Object value) {
		columns.add(column);
		values.add(value);
	}
	
	/**
	 * Set the select  
	 **/
	public void setSelect(String select) {
		this.select = select;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		builder.append(table);
		builder.append(" ( ");
		builder.append(Joiner.on(", ").join(columns));
		builder.append(" ) ");
		builder.append(BaseBuilder.NEW_LINE);
		
		if(!Strings.isNullOrEmpty(select)) {
			builder.append(select);
		}else {
			builder.append(" VALUES ( ");
			builder.append(Joiner.on(", ").join(values));
			builder.append(" ) ");
		}
		return builder.toString();
	}
}
