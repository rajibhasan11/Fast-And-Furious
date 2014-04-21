package com.sportscar.utils;

public class StringsUtils {

	public static String capitalizeFirstLetter(String word){
		if(word != null){
			word = word.trim();
			if(word.length() > 0){
				if(word.length() == 1){
					return word.toUpperCase();
				}
				if(word.length() > 1){
					return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
				}
			}
			if(word.length() == 0){
				return word;
			}
		}
		return "";
	}
}
