/**
 * 
 */
package com.rainsoft.rdbm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Lance
 *
 */
public class DataSourceBuilder {
	
	@Value("${spring.datasource.url}")
	private String url; 
	
	public DataSource getDataSource(String username, String password) {
		DriverManagerDataSource dataSource;
		dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource; 
	}
	
	public JdbcTemplate getJdbcTemplate(String username, String password) {		
		return new JdbcTemplate(this.getDataSource(username, password)); 
	}
}
