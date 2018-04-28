package gp.sql;

import com.gp.sql.SqlBuilder;
import com.gp.sql.delete.Delete;

public class DeleteBuilderTest {

	public static void main(String[] args) {
		
		Delete sql = SqlBuilder.delete( del -> {
			del.from("a");
			
			del.where( cond -> {
				cond.and("a.d = 'a'");
				cond.and("c.d = 1");
				cond.and( c1 -> {
					c1.or("c.f = 1");
					c1.or("a='f'");
					c1.or( c2 -> {
						c2.and("pp = '1'");
						c2.and("m.f = c");
					});
				});
			});
		});
		
		System.out.println(sql.toString());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sql = SqlBuilder.delete( del -> {
			del.from("a");
			
			del.where("a='c'");
			del.and("a='b'");
			del.and(c1 -> {
				c1.or("c='2c'");
				c1.or("d=2");
			});
		});
		
		System.out.println(sql.toString());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
	}
	
}
