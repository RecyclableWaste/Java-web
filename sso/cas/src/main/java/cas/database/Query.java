package cas.database;

import java.sql.Connection;

public abstract class Query<T> extends JDBCTemplate<T>{
	public Query(String dbName, String userName, String passwd) {
		super(dbName, userName, passwd);
	}

	@Override
	public T execute() throws Exception
	{
		Connection con = this.getConnection();
		try
		{
			T result = doQuery(con);
			return result;
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			con.close();
		}
	}
	public abstract T doQuery(Connection con) throws Exception;
}
