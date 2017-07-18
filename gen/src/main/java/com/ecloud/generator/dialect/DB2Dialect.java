package com.ecloud.generator.dialect;

public class DB2Dialect implements Dialect{
	
	@Override
	public String getPaginationSql(String sql, int pageNo, int pageSize) {


	       return "select * from (select rownum rn, t.* from (" + sql

	              + ") t where rownum <= " + (pageNo * pageSize)

	              + ") t1 where t1.rn > " + ((pageNo- 1) * pageSize);

	    }


}
