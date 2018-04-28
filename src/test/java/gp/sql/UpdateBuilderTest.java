package gp.sql;

import com.gp.sql.SqlBuilder;
import com.gp.sql.update.Update;

public class UpdateBuilderTest {

	public static void main(String[] args) {

Update sql = SqlBuilder.update( upd -> {
	upd.table("tb1 z");
	
	upd.column("a");
	upd.column("c", "v");
	
	upd.where( cond -> {
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
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
sql = SqlBuilder.update( upd -> {
	upd.table("tb1 z");
	
	upd.column("a");
	upd.column("c", "v");
	upd.where("a=?");
	upd.where("b=?");
});
sql.toString();
		
		System.out.println(sql.toString());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sql = SqlBuilder.update( upd -> {
			upd.table("tb1 z");
			
			upd.column(cb -> {
				cb.columns("ca","cb","cc");
				cb.values("va","vb","vc");
			});
			upd.where("a = ?");
		});
		sql.toString();
				
				System.out.println(sql.toString());
				
	}
	
}
