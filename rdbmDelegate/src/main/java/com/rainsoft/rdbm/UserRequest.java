/**
 * 
 */
package com.rainsoft.rdbm;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Lance
 *
 */

@JsonPropertyOrder({"username","password","sql"})
public class UserRequest {
	private String sql; 
	private String username; 
	private String password;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequest [sql=").append(sql).append(", username=").append(username).append(", password=")
				.append(password).append("]");
		return builder.toString();
	} 
	
	
}
