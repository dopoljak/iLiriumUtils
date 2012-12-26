package com.iLirium.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class NamedPreparedStatement
{
	private static final Map<String, Map<String, Integer>>	sql_index_columns	= new HashMap<String, Map<String, Integer>>();

	public static String formatSQL(String format, Object... args)
	{
		final Map<String, Integer> index_column = new HashMap<String, Integer>();
		
		int index = 1;
		for (int i = 0; i < args.length; i++)
		{
			Object object = args[i];
			if (object instanceof String)
			{
				String column = (String) object;
				index_column.put(column, index++);
			}
			else if (object instanceof Class<?>)
			{
				args[i] = ((Class<?>) object).getSimpleName();
			}
		}

		@SuppressWarnings("resource")
		String formated = new Formatter().format(format, args).toString();

		if(sql_index_columns.containsKey(formated)) {
			System.err.println("Allready contaions formated SQL = " + formated);
		}
		
		sql_index_columns.put(formated, index_column);
		return formated;
	}
	
	private PreparedStatement ps;
	private Map<String, Integer> columnIndexes;
	
	public NamedPreparedStatement(Connection conn, String sql) throws SQLException
	{
		if(!sql_index_columns.containsKey(sql)) {
			throw new SQLException("SQL isnt initialised with column indexes, use formatSQL");
		}

		columnIndexes = sql_index_columns.get(sql);
		ps = conn.prepareStatement(sql);		
	}
	
	public void	setObject(String column, Object value) throws SQLException
	{
		int index = columnIndexes.get(column);
		ps.setObject(index, value);
	}

	public void close() throws SQLException
	{
		ps.close();
	}
	
	public ResultSet executeQuery() throws SQLException
	{
		return ps.executeQuery();
	}
}
