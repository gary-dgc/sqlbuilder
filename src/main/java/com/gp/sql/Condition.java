package com.gp.sql;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gp.sql.BaseBuilder.Operator;

public class Condition {

	Operator op = Operator.AND;
	
	private final List<String> conditions = Lists.newArrayList();
	
	public Condition() {}
	
	public Condition(String condition) {
		this.conditions.add(condition);
	}
	
	public void add(String condition) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(condition), "the logic expr condition cannot be empty");
		conditions.add(condition);
	}
	
	public void add(String condition, Operator operator) {
		
		op = operator;
		add(condition);
	}
	
	public boolean isSingle() {
		
		return conditions.size() == 1;
	}
	
	public boolean isEmpty() {
		
		return conditions.size() == 0;
	}

	@Override
	public String toString() {
		
		if(conditions.size() == 0) {
			return null;
		}
		else if(conditions.size() == 1) {
			return conditions.get(0);
		}
		else {
			
			return Joiner.on(" " + op.name() + " ").join(conditions);
			
		}
	}
}
