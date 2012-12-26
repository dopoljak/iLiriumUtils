package com.iLirium.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager
{
	private static final ThreadLocal<Connection>	connections	= new ThreadLocal<Connection>();

	private static String							url;
	private static String							username;
	private static String							password;
	private static String							driver;

	public static Connection startTransaction() throws SQLException
	{
		Connection conn = connections.get();

		if (conn == null)
		{
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
			connections.set(conn);
		}

		return conn;
	}

	public static Connection startConnection() throws SQLException
	{
		Connection conn = connections.get();

		if (conn == null)
		{
			conn = DriverManager.getConnection(null);
			conn.setAutoCommit(true);
			connections.set(conn);
		}

		return conn;
	}

	public static Connection getConnection()
	{
		Connection conn = connections.get();
		return conn;
	}

	public static void commit() throws SQLException
	{
		Connection conn = connections.get();
		conn.commit();
	}

	public static void commit_close() throws SQLException
	{
		Connection conn = connections.get();

		if (conn != null)
		{
			connections.remove();
			conn.commit();
			DBUtils.close(conn);
		}
	}

	public static void rollback_close()
	{
		Connection conn = connections.get();

		if (conn != null)
		{
			connections.remove();
			try
			{
				conn.rollback();
			}
			catch (Exception e)
			{
			}
			DBUtils.close(conn);
		}
	}

	public static String getDriver()
	{
		return driver;
	}

	public static synchronized void initializeDB(String driver, String url, String username, String password) throws ClassNotFoundException
	{
		DBManager.url = url;
		DBManager.username = username;
		DBManager.password = password;
		DBManager.driver = driver;
		Class.forName(driver);
	}
}
