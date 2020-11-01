package cas.database;

import java.sql.Connection;

public abstract class Transaction<T> extends JDBCTemplate<T> {

	public Transaction(String dbName, String userName, String passwd) {
		super(dbName, userName, passwd);
	}
	@Override
	public T execute() throws Exception
	{
		Connection con = this.getConnection();
		con.setAutoCommit(false);
		try
		{
			T result = this.doTranscation(con);
			con.commit();
			return result;
		}
		catch(Exception e)
		{
			con.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			con.close();
		}
	}
	public abstract T doTranscation(Connection con) throws Exception;

}
