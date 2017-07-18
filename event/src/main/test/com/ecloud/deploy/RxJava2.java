package com.ecloud.deploy;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class RxJava2 {
	
	
	public static void main(String[] args) throws Exception {
		//File file = new File("F://event.txt");
		File file1 = new File("F://event1.txt");
		File file2 = new File("F://event2.txt");
		String content = FileUtils.readFileToString(file1,"UTF-8").replace("data: {", "\ndata: {");
		FileUtils.write(file2, content, "UTF-8",true);
	}

}
