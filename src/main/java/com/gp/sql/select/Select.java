package com.gp.sql.select;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;
import com.gp.sql.BaseBuilder.OrderType;
import com.gp.sql.WhereSupport;

/**
 * Select SQL clause settings
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class Select extends WhereSupport{
	
	final List<String> columns = Lists.newArrayList();
	
	private final List<String> tables = new ArrayList<>();
	
	private GroupBy groupBy = null;
	
	List<SimpleEntry<String, OrderType>> orderbys = null;
		
	private boolean distinct = false;
	
	private Limiter limiter;
	
	private boolean forUpdate = false;
	
	private boolean noWait = false;
	
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
	 * Add the select column 
	 **/
	public void addColumn(String column) {
		columns.add(column);
	}
	
	/**
	 * Add table 
	 **/
	public void addTable(String table) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(table), "table mustn't be null");
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
	
	public void forUpdate(boolean noWait) {
		this.forUpdate = true;
		this.noWait = noWait;
	}
	
	@Override
	public String toString() {
		Preconditions.checkArgument(tables.size() > 0, "from table is required");
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ").append(BaseBuilder.NEW_LINE);
		
		builder.append(BaseBuilder.INDENT);
		if(distinct) {
			builder.append("DISTINCT ");
		}
		if(columns.size() == 0) {
			builder.append("* ");
		}else {
			builder.append(Joiner.on(", ").join(columns)).append(BaseBuilder.NEW_LINE);
		}
		
		builder.append("FROM ").append(BaseBuilder.NEW_LINE);
		builder.append(BaseBuilder.INDENT);
		builder.append(Joiner.on("," + BaseBuilder.NEW_LINE + BaseBuilder.INDENT).join(tables));
		builder.append(BaseBuilder.NEW_LINE);
		
		if(null != this.getWhere()) {
			builder.append("WHERE ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT);
			builder.append(this.getWhere().toString()).append(BaseBuilder.NEW_LINE);
		}
		
		if(null != groupBy) {
			builder.append("GROUP BY ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT).append(groupBy.toString()).append(BaseBuilder.NEW_LINE);
		}
		
		if(null != orderbys && !orderbys.isEmpty()) {
			builder.append("ORDER BY ").append(BaseBuilder.NEW_LINE);
			builder.append(BaseBuilder.INDENT);
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
		
		if (forUpdate) {
			builder.append("FOR UPDATE");
            if (noWait) {
            	builder.append(" NOWAIT");
            }
		}
		
		if(null != limiter) {
			return limiter.limit(builder.toString());
		}
		
		return builder.toString();
	}
}
