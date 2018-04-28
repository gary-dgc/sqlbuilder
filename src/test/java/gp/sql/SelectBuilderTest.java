package gp.sql;

import com.gp.sql.SqlBuilder;
import com.gp.sql.BaseBuilder.OrderType;
import com.gp.sql.select.Select;
import com.gp.sql.select.SelectBuilder;

public class SelectBuilderTest {

	public static void main(String[] args) {
		SelectBuilder sql = SqlBuilder.select();
		sql.column("a","b");
		sql.from("tbx");
		sql.where("a='c'");
		sql.groupBy("a", "b");
		sql.orderBy("a", "b");
		sql.limit(1, 15);
		System.out.println(sql.build());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~`");
		main1(args);
	}
	
	public static void main1(String[] args) {
		
Select sql = SqlBuilder.select( sel -> {
	sel.distinct();
	sel.column("a","b");
	sel.from("tb1");
	sel.from(f1 -> {
		f1.table("t2")
		.join("b", "b.id = tb1.id")
		.leftJoin("c", "m.id = c.id")
		.rightJoin("c1", c1 -> {
			c1.and("a = 1");
			c1.and("a = 'c'");
		});
	});
	
	sel.where(c1 -> {
		c1.or("c.f = 1");
		c1.or("a='f'");
		c1.or( c2 -> {
			c2.and("pp = '1'");
			c2.and("m.f = c");
		});
	});
	
	sel.groupBy( gb -> {
		gb.column("a","c");
		gb.having(c1 -> {
			c1.and("a='4'");
			c1.and("b = 4");
		});
	});
	
	sel.orderBy("a","b","c");
	sel.orderBy("f", OrderType.DESC);
	
	sel.limit();
});
		
		sql.toString();
		
		System.out.println(sql.toString());
	}
	
}
