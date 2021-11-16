package com.blissstock.nursinghomesupport.utilities;

public class NullStringChecker {
	public static boolean isStringEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
}
