package com.gp.sql.update;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;
import com.gp.sql.WhereSupport;

/**
 * The update setting
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class Update extends WhereSupport{
	
	private String table;

	final List<String> columns = Lists.newArrayList();
	
	final List<Object> values = Lists.newArrayList();
	
	/**
	 * default constructor 
	 **/
	public Update() {}
	
	/**
	 * constructor to replace the clone implementation 
	 **/
	public Update(Update update) {
		this.table = update.table;
		this.columns.addAll(update.columns);
		this.values.addAll(update.values);
		this.setWhere(update.getWhere());
	}
	
	/**
	 * Setter of table 
	 **/
	public void setTable(String table) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table mustn't be null");
		this.table = table;
	}
	
	/**
	 * Set the column with value 
	 **/
	public void setColumn(String column, Object value) {
		columns.add(column);
		values.add(value);
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table is required");
		builder.append("UPDATE ").append(BaseBuilder.NEW_LINE);
		builder.append(BaseBuilder.INDENT).append(table).append(BaseBuilder.NEW_LINE);
		
		builder.append("SET ").append(BaseBuilder.NEW_LINE);
		Preconditions.checkArgument(columns.size() > 0, "columns is required");
		builder.append(BaseBuilder.INDENT);
		for (int i = 0; i < columns.size(); i++) {
			
			builder.append(columns.get(i))
					.append(" = ")
					.append(values.get(i));

			if (i != columns.size() - 1) {
				builder.append(", ");
			}
		}
		
		if(this.getWhere() != null && !this.getWhere().isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT).append(this.getWhere().toString());
		}
		return builder.toString();
	}
}
