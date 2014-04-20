package com.sportscar.database;

public abstract interface ISqlStrings {
	
	// SQL STRINGS
	String ALL = "*";
	
	String COMA = ", ";
	
	String FROM = " FROM ";
	
	String WHERE = " WHERE ";
	
	String DISTINCT = "DISTINCT ";
	
	String SELECT = "SELECT ";
	
	String SELECT_ID = SELECT + "id as _id";
	
	String SELECT_IDASID = SELECT_ID + COMA;
	
	String SELECT_ALL_FROM = SELECT_IDASID + ALL + FROM;
	
	String SELECT_ALL = SELECT + ALL + FROM;
	
	String SELECT_DISTINCT = SELECT + DISTINCT;
	
	String INSERT_INTO = "INSERT INTO ";
	
	String SQLCOUNT = "SELECT COUNT(*)";
	
	String UPDATE = "UPDATE ";
	
	String DELETE = "DELETE FROM ";
	
	String SET_VALUE = " =?";
	
	String E = " && ";
	String O = " || ";
	
	String AND = " AND ";
	
	String AS = " AS ";
	
	String OR = " OR ";
	
	String ORDER_BY = " ORDER BY ";
	
	String GROUP_BY = " GROUP BY ";
	
	String ASC = " ASC";
	
	String DSC = " DESC";
	
	String SUBSTRING_HOUR = "SUBSTR(hour,1,2)";
	
	String AS_INNER_JOIN = " as a INNER JOIN ";
	
	String B_IDAS_ID = "b.id as _id, b.";
	
	String B_DOT = "b.";
	
	String COMA_B_DOT = COMA + B_DOT;
	
	String EQUAL_B = "=" + B_DOT;
	
	String AS_B_ON_A = " AS b ON a.";
	
	String LIMIT = " LIMIT ";
	String OFFSET = " OFFSET ";
	String IS_NULL = " IS NULL";
	String NOT_NULL = " IS NOT NULL";

}
