package com.gp.sql.delete;

import java.util.function.Consumer;

import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;

/**
 * The delete builder help to weave the delete SQL
 * 
 * <pre>
 * Delete sql = SqlBuilder.delete( del -> {
 *	del.from("a");
 *			
 *	del.where( cond -> {
 *		cond.and("a.d = 'a'");
 *		cond.and("c.d = 1");
 *			cond.and( c1 -> {
 *				c1.or("c.f = 1");
 *				c1.or("a='f'");
 *				c1.or( c2 -> {
 *					c2.and("pp = '1'");
 *					c2.and("m.f = c");
 *				});
 *			});
 *		});
 *	});
 * 
 * -- the output: 
 *   DELETE FROM a
 *   WHERE a.d = 'a' AND c.d = 1 AND (c.f = 1 OR a='f' OR (pp = '1' AND m.f = c))
 * </pre>
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-2
 * 
 **/
public class DeleteBuilder extends BaseBuilder{

	private Delete delete;
	
	/**
	 * the default constructor 
	 **/
	public DeleteBuilder() {
		delete = new Delete();
	}
	
	/**
	 * Delete from setting 
	 **/
	public DeleteBuilder from (String table) {
		
		delete.setTable(table);
		return this;
	}
	
	/**
	 * Delete where setting, assign the condition directly, default is AND logic operation
	 * 
	 * <pre>
	 * 		builder.where("a = 1");
	 *      builder.where("b = 'c'");
	 *  -- the output is;
	 *     a = 1 AND b = 'c'
	 * </pre>
	 **/
	public DeleteBuilder where(String condition) {
		
		if(null == delete.getWhere()) {
			Condition cond = new Condition(condition);
			delete.setWhere(cond);
		}else {
			delete.getWhere().add(condition);
		}
		return this;
	}
	
	/**
	 * Delete where setting with lambda function
	 * <pre>
	 * 	c2 -> {
	 *		c2.and("pp = '1'");
	 *		c2.and("m.f = c");
	 *	}
	 * </pre>
	 **/
	public DeleteBuilder where(Consumer<ConditionBuilder> condConsumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		delete.setWhere(cond);
		
		return this;
	}

	/**
	 * Get the delete setting 
	 **/
	public Delete getDelete() {
		return this.delete;
	}

	@Override
	public String build() {

		return delete.toString();
	}
}
