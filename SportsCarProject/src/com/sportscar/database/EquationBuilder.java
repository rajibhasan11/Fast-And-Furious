package com.sportscar.database;

public class EquationBuilder {

	// Method to build value in sql equations
	public static String SQLEqual(Object arg){
		return new String("='" + arg + "'");
	}
	
	public static String SQLGreaterEqual(Object arg){
		return new String(">='" + arg + "'");
	}
	
	public static String SQLGreater(Object arg){
		return new String(">'" + arg + "'");
	}
	
	public static String SQLLessEqual(Object arg){
		return new String("<='" + arg + "'");
	}
	
	public static String SQLLess(Object arg){
		return new String("<'" + arg + "'");
	}
}
