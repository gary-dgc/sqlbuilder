package com.gp.sql;

/**
 * The base builder, here define all the enums 
 * 
 * @author gdiao 
 * 
 * @version v 0.1 2017-12-2
 * 
 **/
public abstract class BaseBuilder {

	/**
	 * The where clause logic expression operator 
	 **/
	public static enum Operator{
		AND, OR
	}
	
	/**
	 * The database type
	 **/
	public static enum Database {
		ORACLE, MYSQL
	}
	
	/**
	 * The join type 
	 **/
	public static enum JoinType{
		LEFT, RIGHT, INNER
	}
	
	/**
	 * The order by direction asc / desc 
	 **/
	public static enum OrderType{
		ASC, DESC
	}
	
	/**
	 * The database mysql 
	 **/
	private Database database = Database.MYSQL;
	
	public static String NEW_LINE = System.getProperty("line.separator");
	public static String INDENT = "  ";
	/**
	 * The build method  
	 **/
	public abstract String build();
	
	/**
	 * Getter of database type 
	 **/
	public Database getDatabase() {
		return this.database;
	}
	
	/**
	 * Setter of database type 
	 **/
	public void setDatabase(Database dbtype) {
		this.database = dbtype;
	}
	
	@Override
	public String toString() {
		
		return build();
	}
}
