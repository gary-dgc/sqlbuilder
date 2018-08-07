package gp.sql;

import com.gp.sql.SqlBuilder;
import com.gp.sql.select.SelectBuilder;

public class SqlTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SelectBuilder builder = SqlBuilder.select("tbl1");
		builder.column("q1","q2","q3");
		builder.where((cond) -> {
			cond.or("q1=?");
			cond.or("q2=?");
		});
		builder.and("q3=?");
		
		System.out.println(builder.toString());
	}

}
