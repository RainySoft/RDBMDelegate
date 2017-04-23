/**
 * Copyright 2017 RainySoft.com
 */
package com.rainsoft.rdbm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlService {
	
	private final static Logger logger = LoggerFactory.getLogger(SqlService.class);
	
	@Autowired
	private JdbcTemplate jt;
	
	@Autowired
	private DataSourceBuilder dataSourceBuilder; 
	
	@RequestMapping(path="/connectivity")
	@ResponseBody
	public List<Map<String,Object>> getData() {
		return jt.queryForList("select 'connected' as status, sys_context('USERENV','INSTANCE_NAME') as instance from dual");
	}
	
	@RequestMapping(path="/vbaSQL2",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public SqlResult getSqlData() {				
		SqlResult result = jt.query("select * from perf_test", new ResultSetExtractor<SqlResult>() {
			@Override
			public SqlResult extractData(ResultSet rs) throws SQLException, DataAccessException {
				ResultSetMetaData rsMeta = rs.getMetaData();
				SqlResult sqlResult = new SqlResult();
				for (int i=1; i <= rsMeta.getColumnCount(); i ++) {
					logger.info(rsMeta.getColumnName(i) + ":" + rsMeta.getColumnTypeName(i) + ":" + rsMeta.getColumnDisplaySize(i) + rsMeta.getColumnClassName(i));					
					sqlResult.addColType(rsMeta.getColumnName(i), rsMeta.getColumnTypeName(i));
				}
				ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
				RowMapperResultSetExtractor<Map<String, Object>> rse = new RowMapperResultSetExtractor<>(rowMapper);
				sqlResult.getColValues().addAll(rse.extractData(rs));
				return sqlResult; 
			}			
		});
		return result; 
	}
	
	
	/**
	 * Send mediaType Application_JSON via Post protocol
	 * format 
	 * {
	 * 	"username":"hr",
	 *  "password":"hr",
	 *  "sql":"select * from countries"
	 * } 
	 * @param userReq
	 * @return
	 */
	@RequestMapping(path="/vbaSQL3",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public SqlResult getSqlData(@RequestBody UserRequest userReq) {
		logger.info("user input :" + userReq);
		JdbcTemplate jt = dataSourceBuilder.getJdbcTemplate(userReq.getUsername(), userReq.getPassword());
		SqlResult result = jt.query(userReq.getSql(), new ResultSetExtractor<SqlResult>() {
			@Override
			public SqlResult extractData(ResultSet rs) throws SQLException, DataAccessException {
				ResultSetMetaData rsMeta = rs.getMetaData();
				SqlResult sqlResult = new SqlResult();
				for (int i=1; i <= rsMeta.getColumnCount(); i ++) {					
					sqlResult.addColType(rsMeta.getColumnName(i), rsMeta.getColumnTypeName(i));
				}
				ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
				RowMapperResultSetExtractor<Map<String, Object>> rse = new RowMapperResultSetExtractor<>(rowMapper);
				sqlResult.getColValues().addAll(rse.extractData(rs));
				return sqlResult; 
			}			
		});
		logger.info("complete query, return to user, record count:{}", result.getColValues().size());
		return result; 		
	}
	
}
