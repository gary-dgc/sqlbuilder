package com.gp.sql.update;

import java.util.List;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;

/**
 * The update setting
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class Update {
	
	private String table;

	final List<String> columns = Lists.newArrayList();
	
	final List<Object> values = Lists.newArrayList();
	
	private Condition condition;
	
	/**
	 * Setter of table 
	 **/
	public void setTable(String table) {
		this.table = table;
	}
	
	/**
	 * Set the column with value 
	 **/
	public void setColumn(String column, Object value) {
		columns.add(column);
		values.add(value);
	}
	
	/**
	 * Set the where condition 
	 **/
	public void setWhere(Condition condition) {
		
		this.condition = condition;
	}
	
	/**
	 * Get the where condition 
	 **/
	public Condition getWhere() {
		
		return this.condition;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE ").append(table).append(" SET ");
		
		for (int i = 0; i < columns.size(); i++) {
			
			builder.append(columns.get(i))
					.append(" = ")
					.append(values.get(i));

			if (i != columns.size() - 1) {
				builder.append(", ");
			}
		}
		
		if(condition != null && !condition.isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ");
			builder.append(condition.toString());
		}
		return builder.toString();
	}
}
