package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;

import com.iLirium.utils.db.DBManager;
import com.iLirium.utils.db.DBUtils;

@SuppressWarnings("unused")
public class UserDAO implements IUserDAO
{
	private static final String	SQL_COUNT		= String.format("SELECT COUNT(*) FROM %s", CLASS_NAME);
	private static final String	SQL_INSERT		= String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", CLASS_NAME, COL_ID, COL_USERNAME, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME);
	private static final String	SQL_FIND_BY_ID	= String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE id = ?", COL_ID, COL_USERNAME, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, CLASS_NAME);
	private static final String	SQL_UPDATE		= String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", CLASS_NAME, COL_USERNAME, COL_PASSWORD, COL_FIRSTNAME, COL_LASTNAME, COL_ID);
	private static final String	SQL_DELETE		= String.format("DELETE FROM %s WHERE %s = ?", CLASS_NAME, COL_ID);

	@Override
	public Long count() throws SQLException
	{
		Long count = null;
		Statement sm = null;
		ResultSet rs = null;

		try
		{
			Connection conn = DBManager.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(SQL_COUNT);

			while (rs.next())
			{
				count = rs.getLong(1);
			}
		}
		finally
		{
			DBUtils.close(rs, sm);
		}

		return count;
	}

	@Override
	public User insert(User user) throws SQLException
	{
		PreparedStatement ps = null;
		// NamedParameterStatement nps = null;
		ResultSet rs = null;

		try
		{
			Connection conn = DBManager.getConnection();

			// Object[] values = { user.getUsername(), user.getPassword(),
			// user.getFirstname(), user.getLastname() };
			// PreparedStatement ps = conn.prepareStatement(SQL_INSERT); //
			// prepareStatement(conn, SQL_INSERT, true, values);

			Long newSequence = 1L;

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			ps.setObject(index++, newSequence);
			ps.setObject(index++, user.getUsername());
			ps.setObject(index++, user.getPassword());
			ps.setObject(index++, user.getFirstname());
			ps.setObject(index++, user.getLastname());

			/*
			 * nps = new NamedParameterStatement(conn, SQL_INSERT,
			 * Statement.RETURN_GENERATED_KEYS); nps.setLong(COL_ID,
			 * newSequence); nps.setString(COL_USERNAME, user.getUsername());
			 * nps.setString(COL_PASSWORD, user.getPassword());
			 * nps.setString(COL_FIRSTNAME, user.getFirstname());
			 * nps.setString(COL_LASTNAME, user.getLastname());
			 */

			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0)
			{
				throw new SQLException("Creating user failed, no rows affected.");
			}

			/*
			 * rs = ps.getGeneratedKeys(); if (rs.next()) {
			 * user.setId(rs.getLong(1)); } else { throw new
			 * SQLException("Creating user failed, no generated key obtained.");
			 * }
			 */
		}
		finally
		{
			// DBUtils.close(rs, nps);
			DBUtils.close(rs, ps);
		}

		return user;
	}

	@Override
	public User findById(Long id) throws SQLException
	{
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			Connection conn = DBManager.getConnection();

			ps = conn.prepareStatement(SQL_FIND_BY_ID);
			ps.setLong(1, id);

			rs = ps.executeQuery();
			if (rs.next())
			{
				user = map(rs);
			}
		}
		finally
		{
			DBUtils.close(rs, ps);
		}

		return user;
	}

	@SuppressWarnings("null")
	@Override
	public void update(User user) throws SQLException
	{
		// NamedParameterStatement nps = null;
		PreparedStatement ps = null;

		try
		{
			Connection conn = DBManager.getConnection();

			/*
			 * nps = new NamedParameterStatement(conn, SQL_UPDATE,
			 * Statement.RETURN_GENERATED_KEYS); nps.setObject(COL_ID,
			 * user.getId()); nps.setString(COL_USERNAME, user.getUsername());
			 * nps.setString(COL_PASSWORD, user.getPassword());
			 * nps.setString(COL_FIRSTNAME, user.getFirstname());
			 * nps.setString(COL_LASTNAME, user.getLastname()); int affectedRows
			 * = nps.executeUpdate();
			 */

			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0)
			{
				throw new SQLException("Updating user failed, no rows affected.");
			}
		}
		finally
		{
			// DBUtils.close(nps);
			DBUtils.close(ps);
		}
	}

	@Override
	public void delete(Long id) throws SQLException
	{
		PreparedStatement ps = null;
		try
		{
			Connection conn = DBManager.getConnection();

			ps = conn.prepareStatement(SQL_DELETE);
			ps.setLong(1, id);

			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0)
			{
				throw new SQLException("Deleting user failed, no rows affected.");
			}
		}
		finally
		{
			DBUtils.close(ps);
		}
	}

	/**
	 * HELPER METHOD
	 */
	private static User map(ResultSet resultSet) throws SQLException
	{
		User user = new User();
		user.setId(resultSet.getLong(COL_ID));
		user.setUsername(resultSet.getString(COL_USERNAME));
		user.setPassword(resultSet.getString(COL_PASSWORD));
		user.setFirstname(resultSet.getString(COL_FIRSTNAME));
		user.setLastname(resultSet.getString(COL_LASTNAME));
		return user;
	}
}
