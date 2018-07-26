package com.gp.sql;

import com.google.common.base.Preconditions;

/**
 * The column builder support assign the columns and values respectively
 * 
 *  @author gdiao 
 *  @version 0.1 2017-12-2
 *  
 **/
public class ColumnBuilder {

	private String[] columns = new String[0];
	
	private Object[] values = new String[0];
	
	/**
	 * Assign columns 
	 **/
	public ColumnBuilder columns(String ...columns) {
		
		this.columns = columns;
		return this;
	}
	
	/**
	 * Assign values 
	 **/
	public ColumnBuilder values(Object ...values) {
		
		Preconditions.checkArgument( values.length > 0 && values.length == columns.length, "values number not map the columns");
		
		this.values = values;
		return this;
	}
	
	/**
	 * Assign the column with value 
	 **/
	public ColumnBuilder set(String column, Object value) {
		columns = new String[] {column};
		values = new Object[] {value};
		return this;
	}
	
	/**
	 * Columns getter 
	 **/
	public String[] getColumns() {
		
		return columns;
	}
	
	/**
	 * Values getter 
	 **/
	public Object[] getValues() {
		
		return values;
	}
}
