package gp.sql;

import com.gp.sql.SqlBuilder;
import com.gp.sql.insert.Insert;

public class InsertBuilderTest {

	
	public static void main(String[] args) {
		
		Insert sql = SqlBuilder.insert( ins -> {
			ins.into("tbl1");
			ins.column("a", "va");
			ins.column("b", "va1");
			ins.column( cb -> {
				cb.columns("c", "b");
				cb.values("mc", 12);
			});
			
		});
		
		System.out.println(sql.toString());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sql = SqlBuilder.insert( ins -> {
			ins.into("tbl1");
			ins.column("a");
			ins.column("b");
			
			ins.select("SELECT a, b FROM tb2 WHERE a = 0");
		});
		
		System.out.println(sql.toString());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		sql = SqlBuilder.insert( ins -> {
			ins.into("tbl1");
			ins.column("a", "va");
			ins.column("b", "va1");
			ins.column( cb -> {
				cb.columns("c", "b");
				cb.values("mc");
			});
			
		});
		
		System.out.println(sql.toString());
	}
	
	
}
