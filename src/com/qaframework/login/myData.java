 
/**
     * *************************************
     *
     * @myData- Defines the datasource and connection variables required for database connection.
     *
     * @Author:Manoranjan
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
package com.qaframework.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mss.utils.ConnectionProvider;


public class myData{
	 
		public DataSource dataSource;
		public Connection connection;

//	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//	   static final String DB_URL = "jdbc:mysql://localhost:3306/qaf";
//
//	   //  Database credentials
//	   static final String USER = "root";
//	   static final String PASS = "root";
//	
//	public static Connection getMySQLDataSource() {
//		 Connection dbconnection = null;
//	
//		try {
//		
//			 Class.forName("com.mysql.jdbc.Driver");
//			 dbconnection = DriverManager.getConnection(DB_URL,USER,PASS);
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return dbconnection;
//	}
	public Connection getDBConnection() {

		try {
			dataSource = ConnectionProvider.getInstance().getConnection(
					"jndi/qafdb");
			connection = dataSource.getConnection();

		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return connection;

	}

}
