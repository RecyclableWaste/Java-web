package cas.database;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBCTemplate<T> {
	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String DBMS_URL = "jdbc:mysql://localhost:3306/";
	public static final String ARG = "?useUnicode=ture&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
	private String dbName;
	private String userName;
	private String passwd;

	public JDBCTemplate(String dbName, String userName, String passwd)
	{
		this.dbName = dbName;
		this.userName = userName;
		this.passwd = passwd;
	}
	public abstract T execute() throws Exception;
	public Connection getConnection() throws Exception
	{
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(DBMS_URL+dbName+ARG,userName,passwd);
	}
}
