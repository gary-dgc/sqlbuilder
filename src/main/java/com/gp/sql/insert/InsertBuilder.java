package com.gp.sql.insert;

import java.util.Collections;
import java.util.function.Consumer;

import com.gp.sql.BaseBuilder;
import com.gp.sql.ColumnBuilder;

/**
 * Insert builder help to weave the SQL script.
 *  
 * <pre>
 * Insert sql = SqlBuilder.insert( ins -> {
 *	ins.into("tbl1");
 *	ins.column("a", "va");
 *	ins.column("b", "va1");
 *	ins.column( cb -> {
 *		cb.columns("c", "b");
 *		cb.values("mc", 12);
 *	});
 *	
 * });
 * -- the output: 
 *  INSERT INTO tbl1 ( a, b, c, b ) 
 *  VALUES ( va, va1, mc, 12 ) 
 *  
 *  sql = SqlBuilder.insert( ins -> {
 *		ins.into("tbl1");
 *		ins.column("a");
 *		ins.column("b");
 *		
 *		ins.select("SELECT a, b FROM tb2 WHERE a = 0");
 *	});
 * -- the output:
 * INSERT INTO tbl1 ( a, b ) 
 * SELECT a, b FROM tb2 WHERE a = 0
 * </pre>
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 **/
public class InsertBuilder extends BaseBuilder{

	private Insert insert;
	
	/**
	 * Default constructor 
	 **/
	public InsertBuilder() {
		insert = new Insert();
	}
	
	/**
	 * Get the insert with hold setting
	 **/
	public Insert getInsert() {
		
		return this.insert;
	}
	
	/**
	 * Set the into table setting
	 **/
	public InsertBuilder into(String table) {
		
		insert.setTable(table);
		return this;
	}
	
	/**
	 * Assign the column setting with lambda function
	 * <pre>
     *	ins.column( cb -> {
     *		cb.columns("c", "b");
     *		cb.values("mc", 12);
     *	});
     *  -- output: 
     *  INSERT INTO tbl1 ( c, b ) 
     *  VALUES ( mc, 12 ) 
	 * </pre>
	 **/
	public InsertBuilder column(Consumer<ColumnBuilder> consumer) {
		
		ColumnBuilder builder = new ColumnBuilder();
		consumer.accept(builder);
		Collections.addAll(insert.columns, builder.getColumns());
		Collections.addAll(insert.values, builder.getValues());
		return this;
	}
	
	/**
	 * Assign the columns with default question mark
	 **/
	public InsertBuilder column(String ...column) {
		if(column == null || column.length == 0) return this;
		for(String col: column) {
			insert.setColumn(col, "?");
		}
		return this;
	}
	
	/**
	 * Assign the column with question mark as value place holder
	 * 
	 **/
	public InsertBuilder column(String column) {
		
		insert.setColumn(column, "?");
		return this;
	}
	
	/**
	 * Assign the column with value
	 * 
	 **/
	public InsertBuilder column(String column, Object value) {
		
		insert.setColumn(column, value);
		return this;
	}
	
	/**
	 * Assign the rows value via select clause
	 * 
	 **/
	public InsertBuilder select(String select) {
		insert.setSelect(select);
		return this;
	}
	
	@Override
	public String build() {
		
		return insert.toString();
	}
}
