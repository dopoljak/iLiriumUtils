package db;

import java.sql.SQLException;

public interface IUserDAO
{
	// CLASS NAME
	public static final Class<User>	CLASS_NAME		= User.class;

	// CLASS FIELDS
	public static final String		COL_ID			= "id";
	public static final String		COL_USERNAME	= "username";
	public static final String		COL_PASSWORD	= "password";
	public static final String		COL_FIRSTNAME	= "firstname";
	public static final String		COL_LASTNAME	= "lastname";

	public Long count() throws SQLException;

	public User insert(User user) throws SQLException;

	public User findById(Long id) throws SQLException;

	public void update(User user) throws SQLException;

	public void delete(Long id) throws SQLException;
}
