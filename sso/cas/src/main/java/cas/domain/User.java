package cas.domain;

public class User {
	private String name;
	private String passwd;
	public User(String name, String passwd)
	{
		this.name = name;
		this.passwd = passwd;
	}
	public String getName()
	{
		return name;
	}
	public String getPasswd()
	{
		return passwd;
	}
	
	public boolean isSame(User user)
	{
		return this.name.equals(user.name) && this.passwd.equals(user.passwd);
	}
	public String toString()
	{
		return name+" "+passwd;
	}
}
