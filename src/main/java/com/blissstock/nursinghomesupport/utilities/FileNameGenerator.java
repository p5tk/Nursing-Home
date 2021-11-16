package com.blissstock.nursinghomesupport.utilities;

public class FileNameGenerator {
	private static String getFileExtension(String fileName) {
		// TODO Auto-generated method stub
		try {
			int index = fileName.lastIndexOf(".");
			return index== -1?"":fileName.substring(index);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return "";
		}		
		

	}
	
	public static String getRandomFileName(String name) {
		return System.currentTimeMillis()+getFileExtension(name);
	}
}
