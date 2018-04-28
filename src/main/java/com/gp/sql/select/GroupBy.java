package com.gp.sql.select;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;

/**
 * The group by setting 
 **/
class GroupBy {

	final List<String> columns = Lists.newArrayList();
	
	public GroupBy(String ...column) {
		Collections.addAll(columns, column);
	}
	
	// the having condition
	Condition having;
	
	public void setHaving(Condition having) {
		this.having = having;
	}
	
	/**
	 * Add columns  
	 **/
	public void addColumn(String ... column) {
		Collections.addAll(columns, column);
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(Joiner.on(", ").join(columns)).append(" ");
		
		
		if(null != having && !having.isEmpty()) {
			buffer.append(BaseBuilder.NEW_LINE);
			buffer.append("HAVING ");
			buffer.append(having.toString());
		}
		
		return buffer.toString();
	}
}
