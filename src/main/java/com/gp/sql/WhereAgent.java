package com.gp.sql;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.Operator;

public class WhereAgent{

	private WhereSupport supporter;
	
	public WhereAgent(WhereSupport supporter) {
		this.supporter = supporter;
	}
	
	public void add(String condition, Operator op) {
		
		if(null == supporter.getWhere()) {
			Condition cond = new Condition(condition);
			supporter.setWhere(cond);
		}else {
			supporter.getWhere().add(condition, op);
		}
	}
	
	public void add(Consumer<ConditionBuilder> condConsumer, Operator op) {
		
		ConditionBuilder cbuilder = new ConditionBuilder();
		condConsumer.accept(cbuilder);
		Condition cond = cbuilder.getCondition();
		
		if(null == supporter.getWhere()) {
			
			supporter.setWhere(cond);
		}else {
			if(cond.isSingle()) {
				supporter.getWhere().add(cond.toString(), op);
			}else {
				supporter.getWhere().add( "(" + cond.toString() + ")", op);
			}
		}
	
	}

}
