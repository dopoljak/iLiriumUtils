package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

import com.iLirium.utils.db.DBManager;

@SuppressWarnings("unused")
public class MainTest
{
	public static void prepareTestDatabase() throws ClassNotFoundException, SQLException
	{
		DeleteDbFiles.execute("~", "test", true);

		Connection connection = DBManager.startTransaction();

		Statement statement = connection.createStatement();
		statement.execute("create table CONFIG_PROPERTIES (key varchar(255) primary key, value varchar(255))");

		String createTableSlot = "CREATE TABLE SLOT (AUTHENTICATIONTYPEID NUMBER(10,0), LASTUSEDDATE TIMESTAMP (6), LASTUSEDBY VARCHAR2(32 CHAR), BINARYSLOT BLOB, TOKENID NUMBER(10,0), ID NUMBER);";
		statement.execute(createTableSlot);

		String createUserTable = String.format("CREATE TABLE %s (%s NUMBER(19,0), %s VARCHAR2(100 CHAR), %s VARCHAR2(100 CHAR), %s VARCHAR2(100 CHAR), %s VARCHAR2(100 CHAR));", IUserDAO.CLASS_NAME, IUserDAO.COL_ID, IUserDAO.COL_USERNAME, IUserDAO.COL_PASSWORD, IUserDAO.COL_FIRSTNAME, IUserDAO.COL_LASTNAME);

		statement.execute(createUserTable);

		statement.close();

		DBManager.commit_close();
	}

	private static void TRANSACTION_LESS_EXAMPLE()
	{
		DAOFactory daoFactory = DAOFactory.getInstance();
		IUserDAO userDAO = daoFactory.getUserDAO();

		try
		{
			// START transaction
			DBManager.startConnection();

			// get #1
			// userDAO.findAll();

			// get #2
			// userDAO.find(null);

		}
		catch (Exception e)
		{
			DBManager.rollback_close();
		}

	}

	private static void TRANSACTION_EXAMPLE()
	{
		DAOFactory daoFactory = DAOFactory.getInstance();
		IUserDAO userDAO = daoFactory.getUserDAO();

		try
		{
			// START transaction
			DBManager.startTransaction();

			// insert #1
			userDAO.insert(null);

			// update #1
			userDAO.update(null);

			// insett #2
			userDAO.insert(null);

			// COMMIT
			DBManager.commit();
		}
		catch (Exception e)
		{
			DBManager.rollback_close();
		}
	}

	public static void main(String[] args)
	{
		System.out.println("Started .....");

		try
		{
			DBManager.initializeDB("org.h2.Driver", "jdbc:h2:~/test", "test", "test");

			prepareTestDatabase();
			
			DAOFactory daoFactory = DAOFactory.getInstance();
			IUserDAO userDAO = daoFactory.getUserDAO();

			// start transaction
			DBManager.startTransaction();

			
			System.out.println("Count = " + userDAO.count());
			
			User user = new User();
			user.setFirstname("aaa");
			user.setLastname("aasdada");
			user.setPassword("gfdfgfd");
			user.setUsername("gfdgdfg");
			
			userDAO.insert(user);
			
			
			System.out.println("Count = " + userDAO.count());
			
			
			User getUser = userDAO.findById(1L);
			System.out.println("User = " + getUser.toString());
			
						
			// commit transactions
			DBManager.commit();
		}
		catch (Exception e)
		{
			System.out.println("MAIN EXCEPT : ");

			e.printStackTrace();
			
			// rollback transactions
			DBManager.rollback_close();
		}
		
		System.out.println("Ended  .....");
	}

}
