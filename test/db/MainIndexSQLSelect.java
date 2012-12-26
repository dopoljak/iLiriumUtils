package db;

import java.sql.SQLException;

import com.iLirium.utils.db.NamedPreparedStatement;

public class MainIndexSQLSelect implements IUserDAO
{
	private static final String	SQL_INSERT	= NamedPreparedStatement.formatSQL("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", CLASS_NAME, COL_ID, COL_USERNAME, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME);

	
	
	public static void main(String[] args) throws SQLException
	{
		System.out.println("sql = " + SQL_INSERT);

		NamedPreparedStatement nps = new NamedPreparedStatement(null, SQL_INSERT);
		nps.setObject(COL_ID, 111222L);
		nps.setObject(COL_FIRSTNAME, "firstname00");
		
		nps.executeQuery();
		
		
		/*
		Map<String, Integer> columnIndexes = sql_index_columns.get(SQL_INSERT);		
		System.out.println("COL_ID = " + columnIndexes.get(COL_ID));
		System.out.println("COL_USERNAME = " + columnIndexes.get(COL_USERNAME));
		System.out.println("COL_PASSWORD = " + columnIndexes.get(COL_PASSWORD));
		System.out.println("COL_FIRSTNAME = " + columnIndexes.get(COL_FIRSTNAME));
		System.out.println("COL_LASTNAME = " + columnIndexes.get(COL_LASTNAME));
		*/
		
	}

	
	
	
	
	
	
	
	
	
	@Override
	public Long count() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User insert(User user) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Long id) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) throws SQLException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) throws SQLException
	{
		// TODO Auto-generated method stub

	}
}
