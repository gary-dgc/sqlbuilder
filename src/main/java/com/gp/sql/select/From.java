package com.gp.sql.select;

import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder;

/**
 * the From setting 
 **/
class From {

	String table ;
	
	List<Join> joins = null;
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void addJoin(Join join) {
		
		if(null == joins) joins = Lists.newArrayList();
		
		joins.add(join);
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		if(!Strings.isNullOrEmpty(table)) {
			
			builder.append(table);
			builder.append(BaseBuilder.NEW_LINE);
		}
		
		if(null != joins) {
			
			for(int i = 0; i < joins.size(); i++ ) {
				Join join = joins.get(i);
				builder.append(join.toString());
				
				if(i != joins.size() - 1)
					builder.append(BaseBuilder.NEW_LINE);
			}
		}
		
		return builder.toString();
	}
}
