package com.gp.sql.select;

import java.util.Collections;
import java.util.function.Consumer;

import com.gp.sql.BaseBuilder;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;

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
public class SelectBuilder extends BaseBuilder implements Cloneable{

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
	 * Assign select columns <br>
	 * !!! Important !!! column decide clear column pair or not.
	 * @param columns if columns is null or length is 0, all the columns be cleared.
	 **/
	public SelectBuilder column(String ...columns) {
		if(columns == null || columns.length == 0) {
			select.columns.clear();
			return this;
		}
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
	 * Assign select from table 
	 **/
	public SelectBuilder resetFrom(String table) {
		this.select.tables.clear();
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
	 * Select where setting, assign the condition directly, default is AND logic operation<br>
	 * !!! IMPORTANT !!! - this method will reset the where condition
	 * 
	 * <pre>
	 * 	builder.where("a = 1");
	 *  builder.where("b = 'c'");
	 *  -- the output is;
	 *     b = 'c'
	 * </pre>
	 **/
	public SelectBuilder where(String condition) {
		
		Condition cond = new Condition(condition);
		select.setWhere(cond);
		return this;
	}
	
	/**
	 * Select where setting with lambda function<br>
	 * !!! IMPORTANT !!! - this method will reset the where condition
	 **/
	public SelectBuilder where(Consumer<ConditionBuilder> condConsumer) {
		
		ConditionBuilder cbuilder = new ConditionBuilder();
		condConsumer.accept(cbuilder);
		Condition cond = cbuilder.getCondition();
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
		
		select.add(condition, Operator.AND);
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
		
		select.add(condConsumer, Operator.AND);
		return this;
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
		
		select.add(condition, Operator.OR);
		return this;
	}
	
	/**
	 * Where OR condition setting with lambda function
	 * <pre>
	 * 	c2 -> {
	 *		c2.or("pp = '1'");
	 *		c2.or("m.f = c");
	 *	}
	 * </pre>
	 **/
	public SelectBuilder or(Consumer<ConditionBuilder> condConsumer) {
		
		select.add(condConsumer, Operator.OR);
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
	 * for update builder 
	 **/
	public SelectBuilder forUpdate() {
		select.forUpdate(false);
		return this;
	}
	
	public SelectBuilder forUpdate(boolean noWait) {
		
		this.forUpdate(noWait);
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
	
	@Override
	public SelectBuilder clone() {
		Select select = new Select(this.select);
		SelectBuilder builder = new SelectBuilder() ;
		builder.select = select;
		
		return builder;
	}
}
