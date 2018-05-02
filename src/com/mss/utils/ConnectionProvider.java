package com.mss.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
	private static ConnectionProvider _instance;

	private DataSource dataSource;
	private Connection connection;

	ResultSet resultSet = null;
	String val = null;
	// Get DataSource
	Context initContext;

	private ConnectionProvider() {

	}

	public static ConnectionProvider getInstance() {

		if (_instance == null) {
			_instance = new ConnectionProvider();
		}
		return _instance;
	}

	public DataSource getConnection(String dataSourceName) {
		try {

			if (CacheManager.getCache().containsKey(dataSourceName)) {
				System.out.println("before getting ds iff!!!");
				dataSource = (DataSource) CacheManager.getCache().get(
						dataSourceName);
				// dataSource = (DB2DataSource)
				// CacheManager.getCache().get(dataSourceName);
				System.out.println("after getting ds iff!!!");

			} else {
				System.out.println("In else!! hi" + dataSourceName);
				Context ctx = new InitialContext();
				dataSource = (DataSource) ctx.lookup("java:comp/env/"
						+ dataSourceName);
				// dataSource = (DataSource) ctx.lookup(dataSourceName);

			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}

}
