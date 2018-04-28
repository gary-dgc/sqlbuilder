SQL Builder
===========

A dynamic SQL builder for Java language. inspired by 
Examples
=======
Some usage examples:
Select
-------
```java
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
```

The output is:

```sql
SELECT DISTINCT a, b
FROM 
tb1,
t2
INNER JOIN b ON b.id = tb1.id 
LEFT JOIN c ON m.id = c.id 
RIGHT JOIN c1 ON a = 1 AND a = 'c' 
WHERE 
c.f = 1 OR a='f' OR (pp = '1' AND m.f = c)
GROUP BY a, c 
HAVING a='4' AND b = 4
ORDER BY a, b, c, f DESC
LIMIT ? OFFSET ?
```
Delete
-------
```java
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
```

The output is:

```sql
DELETE FROM a
WHERE a.d = 'a' AND c.d = 1 AND (c.f = 1 OR a='f' OR (pp = '1' AND m.f = c))
```
Update
---------
```java
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
```

The output is:

```sql
UPDATE tb1 z SET a = ?, c = v
WHERE a.d = 'a' AND c.d = 1 AND (c.f = 1 OR a='f' OR (pp = '1' AND m.f = c))
```

Insert
-------
```java
Insert sql = SqlBuilder.insert( ins -> {
			ins.into("tbl1");
			ins.column("a", "va");
			ins.column("b", "va1");
			ins.column( cb -> {
				cb.columns("c", "b");
				cb.values("mc", 12);
			});
			
		});
```

The output is:

```sql
INSERT INTO tbl1 ( a, b, c, b ) 
 VALUES ( va, va1, mc, 12 ) 
```

