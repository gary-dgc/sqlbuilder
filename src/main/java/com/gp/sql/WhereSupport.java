package com.gp.sql;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Operator;

/**
 * Where support class help to collect the condition setting
 * 
 * @author gdiao 
 * @version 0.1
 * 
 * @date 2017-12-2
 **/
public class WhereSupport {

	private Condition where;

	/**
	 * Set the where condition 
	 **/
	public void setWhere(Condition condition) {
		this.where = condition;
	}
	
	/**
	 * Get the condition 
	 **/
	public Condition getWhere() {
		return this.where;
	}

	/**
	 * Add condition with logic operator 
	 **/
	public void add(String condition, Operator op) {
		
		if(null == where) {
			Condition cond = new Condition(condition);
			where = cond;
		}else {
			where.add(condition, op);
		}
	}
	
	/**
	 * Add condition with logic operator 
	 **/
	public void add(Consumer<ConditionBuilder> condConsumer, Operator op) {
		
		ConditionBuilder cbuilder = new ConditionBuilder();
		condConsumer.accept(cbuilder);
		Condition cond = cbuilder.getCondition();
		
		if(null == where) {
			
			where = cond;
		}else {
			if(cond.isSingle()) {
				where.add(cond.toString(), op);
			}else {
				where.add( "(" + cond.toString() + ")", op);
			}
		}
	
	}
}