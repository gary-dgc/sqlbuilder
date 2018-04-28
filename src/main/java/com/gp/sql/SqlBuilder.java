package com.gp.sql;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Database;
import com.gp.sql.delete.Delete;
import com.gp.sql.delete.DeleteBuilder;
import com.gp.sql.insert.Insert;
import com.gp.sql.insert.InsertBuilder;
import com.gp.sql.select.Select;
import com.gp.sql.select.SelectBuilder;
import com.gp.sql.update.Update;
import com.gp.sql.update.UpdateBuilder;

/**
 * SQL Builder provides different ways to get builder for CRUD SQL scripts
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-2
 * 
 **/
public class SqlBuilder {
	/**
	 * Get the delete builder 
	 **/
	public static DeleteBuilder delete () {
		DeleteBuilder builder = new DeleteBuilder();
		return builder;
	}
	
	/**
	 * Get the delete builder on specified table
	 **/
	public static DeleteBuilder delete (String table) {
		DeleteBuilder builder = new DeleteBuilder();
		return builder.from(table);
	}
	
	/**
	 * Get the delete support lambda function 
	 **/
	public static Delete delete (Consumer<DeleteBuilder> delConsumer) {
		DeleteBuilder builder = new DeleteBuilder();
		delConsumer.accept(builder);
		return builder.getDelete();
	}
	
	/**
	 * Get the insert builder 
	 **/
	public static InsertBuilder insert () {
		InsertBuilder builder = new InsertBuilder();
		return builder;
	}
	
	/**
	 * Get the insert builder into specified table
	 **/
	public static InsertBuilder insert (String table) {
		InsertBuilder builder = new InsertBuilder();
		return builder.into(table);
	}
	
	/**
	 * Get the insert support lambda function
	 **/
	public static Insert insert (Consumer<InsertBuilder> insConsumer) {
		InsertBuilder builder = new InsertBuilder();
		insConsumer.accept(builder);
		return builder.getInsert();
	}
	
	/**
	 * Get the update builder 
	 **/
	public static UpdateBuilder update () {
		UpdateBuilder builder = new UpdateBuilder();
		return builder;
	}
	
	/**
	 * Get the update builder on specified table
	 **/
	public static UpdateBuilder update (String table) {
		UpdateBuilder builder = new UpdateBuilder();
		return builder.table(table);
	}
	
	/**
	 * Get the update support lambda function 
	 * 
	 **/
	public static Update update (Consumer<UpdateBuilder> insConsumer) {
		UpdateBuilder builder = new UpdateBuilder();
		insConsumer.accept(builder);
		return builder.getUpdate();
	}
	
	/**
	 * Get the select support lambda function 
	 **/
	public static Select select (Consumer<SelectBuilder> selConsumer) {
		SelectBuilder builder = new SelectBuilder();
		selConsumer.accept(builder);
		return builder.getSelect();
	}
	
	/**
	 * Get the select builder 
	 **/
	public static SelectBuilder select() {
		SelectBuilder builder = new SelectBuilder();
		
		return builder;
	}
	
	/**
	 * Get the select builder on specified table
	 **/
	public static SelectBuilder select(String table) {
		SelectBuilder builder = new SelectBuilder();
		return builder.from(table);
	}
	
	/**
	 * Get the select builder with specified database, database affects the limit clause
	 **/
	public static SelectBuilder select(Database database) {
		SelectBuilder builder = new SelectBuilder(database);
		
		return builder;
	}
	
	/**
	 * Get the select with specified database and lambda function, database affects the limit clause
	 **/
	public static Select select (Database database, Consumer<SelectBuilder> selConsumer) {
		SelectBuilder builder = new SelectBuilder(database);
		selConsumer.accept(builder);
		return builder.getSelect();
	}
	
}
