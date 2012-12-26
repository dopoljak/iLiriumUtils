package com.iLirium.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils
{
	public static void close(ResultSet rs, Statement s)
	{
		close(rs);
		close(s);
	}

	public static void close(ResultSet rs, PreparedStatement ps)
	{
		close(rs);
		close(ps);
	}

	public static void close(ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	public static void close(Statement sm)
	{
		if (sm != null)
		{
			try
			{
				sm.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	public static void close(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	public static void close(PreparedStatement ps)
	{
		if (ps != null)
		{
			try
			{
				ps.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... values) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		setValues(preparedStatement, values);
		return preparedStatement;
	}

	public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException
	{
		for (int i = 0; i < values.length; i++)
		{
			preparedStatement.setObject(i + 1, values[i]);
		}
	}

	public static java.sql.Date toDate(java.util.Date date)
	{
		return (date != null) ? new java.sql.Date(date.getTime()) : null;
	}

	public static java.util.Date toDate(java.sql.Date date)
	{
		return (date != null) ? new java.util.Date(date.getTime()) : null;
	}

}
