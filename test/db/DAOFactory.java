package db;

public class DAOFactory
{
	private static DAOFactory instance = new DAOFactory();
	
	private DAOFactory() {}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	public UserDAO getUserDAO() 
	{
		return new UserDAO();
	}
}
