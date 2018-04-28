package com.gp.sql;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Operator;

/**
 * The condition builder help to prepare the where clause
 * 
 * @author gdiao
 * @version 0.1 
 * @date 2017-12-2
 **/
public class ConditionBuilder {

	private Condition condition = new Condition();
	
	/**
	 * And logic expression, eg. a = 'v' 
	 **/
	public ConditionBuilder and(String expression) {
		
		condition.add(expression, Operator.AND);
		return this;
	}
	
	/**
	 * Or logic expression, eg. a = 'v' 
	 **/
	public ConditionBuilder or(String expression) {
		
		condition.add(expression, Operator.OR);
		return this;
	}
	
	/**
	 * And logic expression, support lambda function
	 **/
	public ConditionBuilder and(Consumer<ConditionBuilder> condConsumer) {
		
		add(condConsumer, Operator.AND);
		return this;
	}
	
	/**
	 * Or logic expression, support lambda function
	 **/
	public ConditionBuilder or(Consumer<ConditionBuilder> condConsumer) {
		
		add(condConsumer, Operator.OR);
		return this;
	}

	/**
	 * Get the condition 
	 **/
	public Condition getCondition() {
		return condition;
	}
	
	/**
	 * Add the lambda function to existed function 
	 **/
	private ConditionBuilder add(Consumer<ConditionBuilder> condConsumer, Operator op) {
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		
		Condition cond = builder.condition;
		if(cond.isEmpty()) return this;
		
		if(cond.isSingle()) {
			condition.add(cond.toString(), op);
		}else {
			condition.add( "(" + cond.toString() + ")", op);
		}
		
		return this;
	}
}
