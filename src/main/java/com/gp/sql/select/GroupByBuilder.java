package com.gp.sql.select;

import java.util.Collections;
import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Operator;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;

/**
 * the group by builder 
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 *  
 **/
public class GroupByBuilder {

	GroupBy groupBy;
	
	/**
	 * Default constructor 
	 **/
	public GroupByBuilder() {
		this.groupBy = new GroupBy();
	}
	
	/**
	 * Get the group by 
	 **/
	public GroupBy getGroupBy() {
		
		return groupBy;
	}
	
	/**
	 * Add group by columns 
	 **/
	public GroupByBuilder column(String ...column) {
		
		Collections.addAll(groupBy.columns, column);
		return this;
	}
	
	/**
	 * Assign the having condition 
	 **/
	public GroupByBuilder having(String condition) {
		
		Condition cond = new Condition(condition);
		groupBy.having = cond;
		return this;
	}
	
	/**
	 * Assign the having condition with lambda function 
	 **/
	public GroupByBuilder having(Consumer<ConditionBuilder> condConsumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		groupBy.having = cond;
		return this;
	}
	
	/**
	 * Assign the having condition 
	 **/
	public GroupByBuilder and(String condition) {
		
		if(groupBy.having == null) {
			Condition cond = new Condition(condition);
			groupBy.having = cond;
		}else {
			groupBy.having.add(condition, Operator.AND);
		}
		return this;
	}
	
	/**
	 * Assign the having condition 
	 **/
	public GroupByBuilder or(String condition) {
		
		if(groupBy.having == null) {
			Condition cond = new Condition(condition);
			groupBy.having = cond;
		}else {
			groupBy.having.add(condition, Operator.OR);
		}
		return this;
	}

}
