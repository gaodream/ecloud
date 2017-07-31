package com.ecloud.deploy;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class RxJava2 {
	
	
	public static void main(String[] args) throws Exception {
		
		String content = "data: {id:''}";
		String content1 = "event: status_update_event";
		System.out.println(content.substring(6));
		System.out.println(content1.substring(7));
	}

}
