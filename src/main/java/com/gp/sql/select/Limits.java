package com.gp.sql.select;

import com.gp.sql.BaseBuilder;
import com.gp.sql.BaseBuilder.Database;

/**
 * The Limit feature support classes holder
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-2
 * 
 **/
public class Limits {

	/**
	 * Get the limiter by database 
	 **/
	public static Limiter get(Database db, int start, int size) {
		
		if(db == Database.ORACLE) {
			return new OracleLimiter(start, size);
		}
		
		return new MySQLLimiter(start, size);
	}
	
	/**
	 * Get the limiter the start and size reserve as question mark
	 **/
	public static Limiter get(Database db) {
		
		if(db == Database.ORACLE) {
			return new OracleLimiter(-1 , -1);
		}
		
		return new MySQLLimiter(-1 , -1);
	}

	/**
	 * The mysql limiter 
	 **/
	public static class MySQLLimiter extends Limiter {
		
		public MySQLLimiter(int start, int size) {
			super(start, size);
		}

		@Override
		public String limit(String sql) {
			StringBuilder builder = new StringBuilder();
			builder.append(sql);
			builder.append("LIMIT ").append(getSize() == -1 ? "?": getSize());
			builder.append(" OFFSET ").append(getStart() == -1 ? "?" : getStart());
			return builder.toString();
		}

	}

	/**
	 * the oracle limiter 
	 **/
	public static class OracleLimiter extends Limiter {

		public OracleLimiter(int start, int size) {
			super(start, size);
		}
				
		@Override
		public String limit(String sql) {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT data.* FROM (").append(BaseBuilder.NEW_LINE);
			builder.append("SELECT ord_data.*, rownum AS rnum FROM (").append(BaseBuilder.NEW_LINE);
			builder.append(sql);
			builder.append(")").append(BaseBuilder.NEW_LINE);
			builder.append("ord_data ) data").append(BaseBuilder.NEW_LINE);
			builder.append("WHERE").append(BaseBuilder.NEW_LINE);
			if(getStart() == -1 || getSize() == -1) {
				builder.append("rnum BETWEEN ? AND ?");
			}else {
				builder.append("rnum BETWEEN "+ getStart() +" AND "+ (getStart() + getSize()));
			}
			
			return builder.toString();
		}

	}
}
