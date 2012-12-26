package db;

public class User
{
	private Long	id;
	private String	username;
	private String	password;
	private String	firstname;
	private String	lastname;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("id = ").append(id).append(", ");
		sb.append("username = ").append(username).append(", ");
		sb.append("password = ").append(password).append(", ");
		sb.append("firstname = ").append(firstname).append(", ");
		sb.append("lastname = ").append(lastname);		
		return sb.toString();
	}
}
