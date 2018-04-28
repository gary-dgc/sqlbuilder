package com.gp.sql.update;

import java.util.Collections;
import java.util.function.Consumer;

import com.gp.sql.BaseBuilder;
import com.gp.sql.ColumnBuilder;
import com.gp.sql.Condition;
import com.gp.sql.ConditionBuilder;

/**
 * The update builder help to weave the update SQL
 * 
 * <pre>
 * Update sql = SqlBuilder.update( upd -> {
 *	upd.table("tb1 z");
 *	
 *	upd.column("a");
 *	upd.column("c", "v");
 *	
 *	upd.where( cond -> {
 *		cond.and("a.d = 'a'");
 *		cond.and("c.d = 1");
 *		cond.and( c1 -> {
 *			c1.or("c.f = 1");
 *			c1.or("a='f'");
 *			c1.or( c2 -> {
 *				c2.and("pp = '1'");
 *				c2.and("m.f = c");
 *			});
 *		});
 *	});
 * });
 * -- output:
 * UPDATE tb1 z SET a = ?, c = v
 * WHERE a.d = 'a' AND c.d = 1 AND (c.f = 1 OR a='f' OR (pp = '1' AND m.f = c))
 * 
 * sql = SqlBuilder.update( upd -> {
 *	upd.table("tb1 z");
 *	upd.column("a");
 *	upd.column("c", "v");
 *
 *	upd.where("a = ?");
 *	upd.where("b = ?");
 * });
 * -- output:
 * UPDATE tb1 z SET a = ?, c = v
 * WHERE a = ? AND b = ?
 * </pre>
 * @author gdiao
 * @version 0.1
 * @date 2017-12-1
 * 
 **/
public class UpdateBuilder extends BaseBuilder{

	Update update;
	
	/**
	 * The default constructor 
	 **/
	public UpdateBuilder() {
		
		update = new Update();
	}
	
	/**
	 * Get the update setting 
	 **/
	public Update getUpdate() {
		
		return update;
	}
	
	/**
	 * Assign the update table 
	 **/
	public UpdateBuilder table(String table) {
		update.setTable(table);
		return this;
	}
	
	/**
	 * Assign column with lambda function
	 * <pre>
	 * builder.column(cb -> {
	 *			cb.columns("ca","cb","cc");
	 *			cb.values("va","vb","vc");
	 *		});
	 * </pre> 
	 **/
	public UpdateBuilder column(Consumer<ColumnBuilder> consumer) {
		
		ColumnBuilder builder = new ColumnBuilder();
		consumer.accept(builder);
		Collections.addAll(update.columns, builder.getColumns());
		Collections.addAll(update.values, builder.getValues());
		
		return this;
	}
	
	/**
	 * Assign the columns with default question mark
	 **/
	public UpdateBuilder column(String ... column) {
		
		if(column == null || column.length == 0) return this;
		for(String col: column) {
			update.setColumn(col, "?");
		}
		return this;
	}
	
	/**
	 * Assign the column and value 
	 **/
	public UpdateBuilder column(String column, Object value) {
		
		update.setColumn(column, value);
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
	public UpdateBuilder where(String condition) {
		
		if(null == update.getWhere()) {
			Condition cond = new Condition(condition);
			update.setWhere(cond);
		}else {
			update.getWhere().add(condition);
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
	public UpdateBuilder where(Consumer<ConditionBuilder> condConsumer) {
		ConditionBuilder builder = new ConditionBuilder();
		condConsumer.accept(builder);
		Condition cond = builder.getCondition();
		update.setWhere(cond);
		
		return this;
	}

	@Override
	public String build() {
		
		return update.toString();
	}

}
