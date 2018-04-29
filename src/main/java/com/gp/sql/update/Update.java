package com.gp.sql.update;

import java.util.List;
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
		
		if(this.getWhere() != null && !this.getWhere().isEmpty()) {
			builder.append(BaseBuilder.NEW_LINE);
			builder.append("WHERE ");
			builder.append(this.getWhere().toString());
		}
		return builder.toString();
	}
}
