package com.ecloud.framework.plugin;

import java.util.List;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class PaginationObjectFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = 383815269487616198L;

	@Override
	public <T> T create(Class<T> arg0, List<Class<?>> arg1, List<Object> arg2) {
		// TODO Auto-generated method stub
		System.out.println("------PaginationObjectFactory-----");
		return null;
	}

}
