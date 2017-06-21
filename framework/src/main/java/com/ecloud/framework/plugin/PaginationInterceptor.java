package com.ecloud.framework.plugin;


import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.ecloud.framework.dialects.DB2Dialect;
import com.ecloud.framework.dialects.Dialect;
import com.ecloud.framework.dialects.MySQL5Dialect;
import com.ecloud.framework.dialects.OrcaleDialect;
import com.ecloud.framework.dialects.SqlServerDialect;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {java.sql.Connection.class}) })
public class PaginationInterceptor implements Interceptor {
	

	    @Override
	    public Object intercept(Invocation invocation) throws Throwable {
	    	
	    	
	       StatementHandler statmentHandler = (StatementHandler) invocation.getTarget();
	       
	       MetaObject metaStatementHandler = MetaObject.forObject(statmentHandler, new PaginationObjectFactory(), new PaginationWrapperFactory());
	       
	       RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
	       
	       if(rowBounds == null || rowBounds == RowBounds.DEFAULT) {
	    	  
	    	   return invocation.proceed();

	       }
	       String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");   
	       
	       Dialect dialect = this.getDialect(metaStatementHandler);
	       
	       String newSQL = dialect.getPaginationSql(originalSql,rowBounds.getOffset(), rowBounds.getLimit());
	       
	       ///System.out.println("生成分页SQL : "+newSQL);  
	       

	       
	       /*
	       metaStatementHandler.setValue("delegate.boundSql.sql",newSQL); 
	       
	       metaStatementHandler.setValue("delegate.rowBounds.offset", rowBounds.getOffset());       

	       metaStatementHandler.setValue("delegate.rowBounds.limit",rowBounds.getLimit());*/
	       
	       
	       
	       
	       metaStatementHandler.setValue("delegate.boundSql.sql", newSQL );

	       metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );

	       metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );
	       
	       return invocation.proceed();

	    }

	 

	    @Override
	    public Object plugin(Object target) {
	    	   if (target instanceof StatementHandler) {
	               return Plugin.wrap(target, this);
	           } else {
	               return target;
	           }

	    }


	    public Dialect getDialect(MetaObject metaStatementHandler ){

	  	  Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");

	        Dialect.Type databaseType = null;

	        try{           
	     	   databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());       
	        } catch(Exception e){         
	     	     e.printStackTrace() ;
	     	   }       
	        Dialect dialect =null;

	        switch(databaseType){          

	            case MYSQL: {
	         	   dialect =new MySQL5Dialect();
	         	   break; 
	            }

	            case SQLSERVER : {
	         	   dialect = new SqlServerDialect();
	         	   break; 
	            }
	            case DB2 : {
	         	   dialect = new DB2Dialect();
	         	   break; 
	            }
	            case ORACLE : {
	         	   dialect = new OrcaleDialect();
	            }
	            default :dialect =new OrcaleDialect();
	        }

	  	  return dialect;
	  	  }



		@Override
		public void setProperties(Properties properties) {
			// TODO Auto-generated method stub
			
		}
}