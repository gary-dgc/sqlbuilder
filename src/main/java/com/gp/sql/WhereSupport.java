package com.gp.sql;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Operator;

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
			if(where.isSingle()) {
				where.add(condition, op);
			}else if(where.op == op){
				where.add(condition, op);
			}else {
				
				Condition cond = new Condition("(" + where.toString() + ")");
				cond.add(condition, op);
				where = cond;
			}
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
			if(cond.isSingle()) {
				where = cond;
			}else {
				where = new Condition();
				where.add( "(" + cond.toString() + ")", op);
			}
		}else {
			if(cond.isSingle()) {
				where.add(cond.toString(), op);
			}else {
				where.add( "(" + cond.toString() + ")", op);
			}
		}
	
	}
}
