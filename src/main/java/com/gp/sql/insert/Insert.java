package com.gp.sql.insert;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
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
	
	final List<String> columns = Lists.newArrayList();
	
	List<Object> values = Lists.newArrayList();
	
	private String select ;
	
	/**
	 * default constructor 
	 **/
	public Insert() {}
	
	/**
	 * constructor to replace the clone implementation 
	 **/
	public Insert(Insert insert) {
		this.table = insert.table;
		this.columns.addAll(insert.columns);
		this.select = insert.select;
	}
	
	/**
	 * Set table 
	 **/
	public void setTable(String table) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table mustn't be null");
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
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table is required");
		Preconditions.checkArgument(columns.size() > 0, "columns is required");
		
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ").append(BaseBuilder.NEW_LINE);
		builder.append(BaseBuilder.INDENT).append(table);
		builder.append(" ( ");
		builder.append(Joiner.on(", ").join(columns));
		builder.append(" ) ");
		builder.append(BaseBuilder.NEW_LINE);
		
		if(!Strings.isNullOrEmpty(select)) {
			builder.append(BaseBuilder.INDENT).append(select);
		}else {
			builder.append("VALUES ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT).append("(");
			builder.append(Joiner.on(", ").join(values));
			builder.append(" ) ");
		}
		return builder.toString();
	}
}
