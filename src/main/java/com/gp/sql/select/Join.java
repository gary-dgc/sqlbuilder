package com.gp.sql.select;

import com.gp.sql.BaseBuilder.JoinType;
import com.gp.sql.Condition;

/**
 * the join setting 
 **/
class Join {
	
	String table;
	
	Condition condition;
	
	JoinType type = JoinType.INNER;
	
	public void setTable(String table) {
		this.table = table;
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		if(type == JoinType.INNER) {
			buffer.append("INNER JOIN ");
		}
		if(type == JoinType.LEFT) {
			buffer.append("LEFT JOIN ");
		}
		if(type == JoinType.RIGHT) {
			buffer.append("RIGHT JOIN ");
		}
		buffer.append(table);
		buffer.append(" ON ");
		buffer.append(condition);
		buffer.append(" ");
		
		return buffer.toString();
	}
}
