package com.gp.sql.select;

import java.util.Collections;
import java.util.function.Consumer;

import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;
import com.gp.sql.BaseBuilder.Operator;
import com.gp.sql.delete.DeleteBuilder;

/**
 * The select builder help to weave the select SQL
 * 
 * <pre>
 *Select sql = SqlBuilder.select( sel -> {
 *	sel.distinct();
 *	sel.column("a","b");
 *	sel.from("tb1");
 *	sel.from(f1 -> {
 *		f1.table("t2")
 *		.join("b", "b.id = tb1.id")
 *		.leftJoin("c", "m.id = c.id")
 *		.rightJoin("c1", c1 -> {
 *			c1.and("a = 1");
 *			c1.and("a = 'c'");
 *		});
 *	});
 *	
 *	sel.where(c1 -> {
 *		c1.or("c.f = 1");
 *		c1.or("a='f'");
 *		c1.or( c2 -> {
 *			c2.and("pp = '1'");
 *			c2.and("m.f = c");
 *		});
 *	});
 *	
 *	sel.groupBy( gb -> {
 *		gb.column("a","c");
 *		gb.having(c1 -> {
 *			c1.and("a='4'");
 *			c1.and("b = 4");
 *		});
 *	});
 *	
 *	sel.orderBy("a","b","c");
 *	sel.orderBy("f", OrderType.DESC);
 *	
 *	sel.limit();
 *});
 *
 * -- output:
 * SELECT DISTINCT a, b
 * FROM 
 * tb1,
 * t2
 * INNER JOIN b ON b.id = tb1.id 
 * LEFT JOIN c ON m.id = c.id 
 * RIGHT JOIN c1 ON a = 1 AND a = 'c' 
 * WHERE 
 * c.f = 1 OR a='f' OR (pp = '1' AND m.f = c)
 * GROUP BY a, c 
 * HAVING a='4' AND b = 4
 * ORDER BY a, b, c, f DESC
 * LIMIT ? OFFSET ?
 * </pre>
 * 
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class SelectBuilder extends BaseBuilder{

	private Select select ;
	
	/**
	 * Default constructor, the default database type is MySql
	 **/
	public SelectBuilder() {
		
		this.select = new Select();
	}

	/**
	 * Constructor with database type 
	 **/
	public SelectBuilder(Database dbtype) {
		
		this.setDatabase(dbtype);
		this.select = new Select();
	}
	
	/**
	 * Get the select 
	 **/
	public Select getSelect() {
		
		return select;
	}
	
	/**
	 * Select all setting, use * as column.
	 **/
	public SelectBuilder all() {
		this.select.addColumn("*");
		return this;
	}
	
	/**
	 * Assign select columns
	 **/
	public SelectBuilder column(String ...columns) {
		
		Collections.addAll(select.columns, columns);
		return this;
	}
	
	/**
	 * Specify the distinct 
	 **/
	public SelectBuilder distinct() {
		
		this.select.distinct();
		return this;
	}
	
	/**
	 * Assign select from table 
	 **/
	public SelectBuilder from(String table) {
		
		this.select.addTable(table);
		return this;
	}
		
	/**
	 * Assign the from with lambda function to help build join clause 
	 **/
	public SelectBuilder from(Consumer<FromBuilder> consumer) {
		
		FromBuilder builder = new FromBuilder();
		consumer.accept(builder);
		
		From from = builder.getFrom();
		
		select.addTable(from.toString());
		return this;
	}
	
	/**
	 * Select where setting, assign the condition directly, default is AND logic operation
	 * 
	 * <pre>
	 * 		builder.where("a = 1");
	 *      builder.where("b = 'c'");
	 *  -- the output is;
	 *     a = 1 AND b = 'c'
	 * </pre>
	 **/
	public SelectBuilder where(String condition) {
		
		if(null == select.getWhere()) {
			Condition cond = new Condition(condition);
			select.setWhere(cond);
		}else {
			select.getWhere().add(condition);
		}
		return this;
	}
	
	/**
	 * Select where setting with lambda function
	 * <pre>
	 * 	c2 -> {
	 *		c2.and("pp = '1'");
	 *		c2.and("m.f = c");
	 *	}
	 * </pre>
	 **/
	public SelectBuilder where(Consumer<ConditionBuilder> condConsumer) {
		
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		select.setWhere(cond);
		
		return this;
	}

	/**
	 * Where AND setting, assign the condition directly
	 * 
	 * <pre>
	 * 		builder.where("a = 1");
	 *      builder.and("b = 'c'");
	 *  -- the output is;
	 *     a = 1 AND b = 'c'
	 * </pre>
	 **/
	public SelectBuilder and(String condition) {
		
		if(null == select.getWhere()) {
			Condition cond = new Condition(condition);
			select.setWhere(cond);
		}else {
			select.getWhere().add(condition, Operator.AND);
		}
		return this;
	}
	
	/**
	 * Where AND condition setting with lambda function
	 * <pre>
	 * 	c2 -> {
	 *		c2.and("pp = '1'");
	 *		c2.and("m.f = c");
	 *	}
	 * </pre>
	 **/
	public SelectBuilder and(Consumer<ConditionBuilder> condConsumer) {
		
		return add(condConsumer, Operator.AND);
	}
	
	/**
	 * Where OR setting, assign the condition directly
	 * 
	 * <pre>
	 * 		builder.where("a = 1");
	 *      builder.or("b = 'c'");
	 *  -- the output is;
	 *     a = 1 OR b = 'c'
	 * </pre>
	 **/
	public SelectBuilder or(String condition) {
		
		if(null == select.getWhere()) {
			Condition cond = new Condition(condition);
			select.setWhere(cond);
		}else {
			select.getWhere().add(condition, Operator.OR);
		}
		return this;
	}
	
	/**
	 * Where AND condition setting with lambda function
	 * <pre>
	 * 	c2 -> {
	 *		c2.and("pp = '1'");
	 *		c2.and("m.f = c");
	 *	}
	 * </pre>
	 **/
	public SelectBuilder or(Consumer<ConditionBuilder> condConsumer) {
		
		return add(condConsumer, Operator.OR);
	}
	
	private SelectBuilder add(Consumer<ConditionBuilder> condConsumer, Operator op) {
		
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		if(null == select.getWhere()) {
			
			select.setWhere(cond);
		}else {
			if(cond.isSingle()) {
				select.getWhere().add(cond.toString(), op);
			}else {
				select.getWhere().add( "(" + cond.toString() + ")", op);
			}
		}
	
		return this;
	}
	
	/**
	 * Assign the group by columns 
	 **/
	public SelectBuilder groupBy(String ...column) {
		GroupBy groupBy  = new GroupBy(column);
		
		select.setGroupBy(groupBy);
		return this;
	}
	
	/**
	 * Assign the group by setting with lambda function, which help build having clause. 
	 **/
	public SelectBuilder groupBy(Consumer<GroupByBuilder> consumer) {
		GroupByBuilder builder = new GroupByBuilder();
		consumer.accept(builder);
		
		select.setGroupBy(builder.groupBy);
		return this;
	}
	
	/**
	 * Assign the order by columns 
	 **/
	public SelectBuilder orderBy(String ... column) {
		
		if(null == column) return this;
		
		for(int i = 0; i < column.length ; i++) {
			select.addOrderBy(column[i], null);
		}
		return this;
	}
	
	/**
	 * Assign the order by and order direction 
	 **/
	public SelectBuilder orderBy(String column, OrderType type) {
		select.addOrderBy(column, type);
		return this;
	}
	
	/**
	 * Assign limiter, the LIMIT and OFFSET take ? as parameter place holder.
	 **/
	public SelectBuilder limit() {
		
		Limiter lim = Limits.get(super.getDatabase());
		select.setLimiter(lim);
		return this;
	}
	
	/**
	 * Assign limiter
	 **/
	public SelectBuilder limit(int start, int size) {
		
		Limiter lim = Limits.get(super.getDatabase(), start, size);
		select.setLimiter(lim);
		return this;
	}
	
	@Override
	public String build() {
		
		return select.toString();
	}
}
