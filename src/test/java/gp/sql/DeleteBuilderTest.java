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
		
		sql.toString();
		
		System.out.println(sql.toString());
	}
	
}
