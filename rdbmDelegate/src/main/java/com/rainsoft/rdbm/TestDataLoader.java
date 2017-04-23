/**
 * 
 */
package com.rainsoft.rdbm;

import java.security.SecureRandom;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Lance
 *
 */
@Component
public class TestDataLoader implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(TestDataLoader.class);
	
	@Autowired
	private JdbcTemplate jt;

	private final static String sql = " INSERT INTO PERF_TEST (ID, USER_NAME, USER_MEMO, CREATE_DT) "
			+ " VALUES (PERF_TEST_SEQ.NEXTVAL, :USERNAME, :USER_MEMO, SYSDATE)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg0) throws Exception {		
		if ( arg0[0].equals("InitPerfTest") ) {
			logger.info("start build test data set on Table PERF_TEST, Please make sure the table is created");
			StopWatch sw = new StopWatch(); 
			sw.start("generate test data");
			ArrayList<Object[]> paramList = new ArrayList<>(500000); 
			for (int i = 0; i < 500000 ; i ++) {
				String userName = TestDataLoader.randomStr(512);
				String userMemo = TestDataLoader.randomStr(1024);
				paramList.add(new Object[] {userName, userMemo});
			}
			sw.stop();			
			sw.start("insert into database");
			jt.batchUpdate(sql,paramList);	
			sw.stop();
			logger.info("End create performance Table Performance info \n" + sw.prettyPrint());
		}
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	private static String randomStr(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i= 0 ; i <len ; i ++) {
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		}
		return sb.toString();
	}

}
