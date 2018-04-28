package com.gp.sql.select;

import java.util.Collections;
import java.util.function.Consumer;

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
	public void column(String ...column) {
		
		Collections.addAll(groupBy.columns, column);
	}
	
	/**
	 * Assign the having condition 
	 **/
	public void having(String condition) {
		
		Condition cond = new Condition(condition);
		groupBy.having = cond;
	}
	
	/**
	 * Assign the having condition with lambda function 
	 **/
	public void having(Consumer<ConditionBuilder> condConsumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		groupBy.having = cond;
	}
}
