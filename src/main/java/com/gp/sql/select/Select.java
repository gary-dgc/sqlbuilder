package com.gp.sql.select;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;
import com.gp.sql.BaseBuilder.OrderType;
import com.gp.sql.Condition;

/**
 * Select SQL clause settings
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class Select {
	
	final List<String> columns = Lists.newArrayList();
	
	private final List<String> tables = new ArrayList<>();
	
	private GroupBy groupBy = null;
	
	List<SimpleEntry<String, OrderType>> orderbys = null;
	
	private Condition where;
		
	private boolean distinct = false;
	
	private Limiter limiter;
	
	/**
	 * Set the group by 
	 **/
	public void setGroupBy(GroupBy groupBy) {
		this.groupBy = groupBy;
	}
	
	/**
	 * Set the limiter 
	 **/
	public void setLimiter(Limiter limiter) {
		this.limiter = limiter;
	}
	
	/**
	 * Set the where condition 
	 **/
	public void setWhere(Condition condition) {
		this.where = condition;
	}
	
	/**
	 * Get the where condition 
	 **/
	public Condition getWhere() {
		return this.where;
	}
	
	/**
	 * Add the select column 
	 **/
	public void addColumn(String column) {
		columns.add(column);
	}
	
	/**
	 * Add table 
	 **/
	public void addTable(String table) {
		tables.add(table);
	}
	
	/**
	 * set distinct setting 
	 **/
	public void distinct() {
		this.distinct = true;
	}
	
	/**
	 * Add order by and order type, order type is nullable 
	 **/
	public void addOrderBy(String column, OrderType order) {
		
		if(orderbys  == null) orderbys = Lists.newArrayList();
		SimpleEntry<String, OrderType> entry = new SimpleEntry<String, OrderType>(column, order);
		orderbys.add(entry);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		if(distinct) {
			builder.append("DISTINCT ");
		}
		if(columns.size() == 0) {
			builder.append("* ");
		}else {
			builder.append(Joiner.on(", ").join(columns)).append(BaseBuilder.NEW_LINE);
		}
		
		builder.append("FROM ").append(BaseBuilder.NEW_LINE);
		builder.append(Joiner.on("," + BaseBuilder.NEW_LINE).join(tables));
		builder.append(BaseBuilder.NEW_LINE);
		
		if(null != where) {
			builder.append("WHERE ").append(BaseBuilder.NEW_LINE);
			builder.append(where.toString()).append(BaseBuilder.NEW_LINE);
		}
		
		if(null != groupBy) {
			builder.append("GROUP BY ").append(groupBy.toString()).append(BaseBuilder.NEW_LINE);
		}
		
		if(null != orderbys && !orderbys.isEmpty()) {
			builder.append("ORDER BY ");
			for(int i = 0; i < orderbys.size(); i++) {
				SimpleEntry<String, OrderType> entry = orderbys.get(i);
				
				builder.append(entry.getKey());
				if(null != entry.getValue()) {
					builder.append(" ").append(entry.getValue().name());
				}
				
				if(i != orderbys.size() - 1) {
					builder.append(", ");
				}
			}
			builder.append(BaseBuilder.NEW_LINE);
		}
		
		if(null != limiter) {
			return limiter.limit(builder.toString());
		}
		
		return builder.toString();
	}
}
