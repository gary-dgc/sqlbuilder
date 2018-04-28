package com.gp.sql.select;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder.JoinType;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;

/**
 * the form builder help to build the from and join clause
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class FromBuilder {

	From from ;
	
	/**
	 * The default constructor 
	 **/
	public FromBuilder() {
		this.from = new From();
	}
	
	/**
	 * Get the form 
	 **/
	public From getFrom() {
		
		return this.from;
	}
	
	/**
	 * Setter of from table 
	 **/
	public FromBuilder table(String table) {
		this.from.setTable(table);
		return this;
	}
	
	/**
	 * Set the left join with ON condition 
	 **/
	public FromBuilder leftJoin(String table, String onCondition) {
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.LEFT;
		join.condition = new Condition(onCondition);
		
		from.addJoin(join);
		return this;
	}
	
	/**
	 * Set the left join with ON condition lambda function 
	 **/
	public FromBuilder leftJoin(String table, Consumer<ConditionBuilder> consumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		consumer.accept(builder);
		Condition cond = builder.getCondition();
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.LEFT;
		join.condition = cond;
		
		from.addJoin(join);
		return this;
	}

	/**
	 * Set the right join with ON condition 
	 **/
	public FromBuilder rightJoin(String table, String onCondition) {
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.RIGHT;
		join.condition = new Condition(onCondition);
		
		from.addJoin(join);
		return this;
	}
	
	/**
	 * Set the right join with ON condition lambda function 
	 **/
	public FromBuilder rightJoin(String table, Consumer<ConditionBuilder> consumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		consumer.accept(builder);
		Condition cond = builder.getCondition();
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.RIGHT;
		join.condition = cond;
		
		from.addJoin(join);
		return this;
	}
	
	/**
	 * Set the inner join with ON condition 
	 **/
	public FromBuilder join(String table, String onCondition) {
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.INNER;
		join.condition = new Condition(onCondition);
		
		from.addJoin(join);
		return this;
	}
	
	/**
	 * Set the inner join with ON condition lambda function 
	 **/
	public FromBuilder join(String table, Consumer<ConditionBuilder> consumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		consumer.accept(builder);
		Condition cond = builder.getCondition();
		
		Join join = new Join();
		join.table = table;
		join.type = JoinType.INNER;
		join.condition = cond;
		
		from.addJoin(join);
		return this;
	}
}
