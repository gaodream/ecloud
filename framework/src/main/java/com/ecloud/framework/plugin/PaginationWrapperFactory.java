package com.ecloud.framework.plugin;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

public class PaginationWrapperFactory implements ObjectWrapperFactory {

	@Override
	public ObjectWrapper getWrapperFor(MetaObject arg0, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println("----getWrapperFor------");
		return null;
	}

	@Override
	public boolean hasWrapperFor(Object arg0) {
		System.out.println("-----hasWrapperFor-----");
		return false;
	}

}
