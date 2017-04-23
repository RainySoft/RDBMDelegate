/**
 * Copyright 2017 RainySoft.com
 */
package com.rainsoft.rdbm;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map; 

public class SqlResult {
	private Map<String,String>  colTypes; 
	private List<Map<String,Object>>  colValues; 
	
	public SqlResult() {
		colTypes = new LinkedHashMap<>(); 
		colValues = new LinkedList<>();
	}
	
	public Map<String,String> getColTypes() {
		return this.colTypes;
	}
	
	
	public List<Map<String,Object>> getColValues() {
		return this.colValues; 
	}
	
	public void addColType(String colName, String typeName) {
		this.getColTypes().put(colName, typeName);
	}
	
	public void addrow(Map<String,Object> row) {
		this.getColValues().add(row);
	}
	
}
