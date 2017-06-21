package com.ecloud.framework.dialects;

public class SqlServerDialect implements Dialect{
	
	
	@Override
	 public String getPaginationSql(String sql, int pageNo, int pageSize) {

		return "select top " + pageSize + " from (" + sql

	              + ") t where t.id not in (select top " + (pageNo-1) * pageSize + " t1.id from ("

	              + sql + ") t1)";

	    }


}
