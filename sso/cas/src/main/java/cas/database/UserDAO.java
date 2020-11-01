package cas.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cas.domain.User;

public class UserDAO {
	private static final String DB_NAME = "sso";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASSWD = "123456";
	private UserDAO() {}

	public static boolean add(User user) throws Exception
	{
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(DB_NAME,DB_USER_NAME,DB_PASSWD) {

			@Override
			public Boolean doTranscation(Connection con) throws Exception {
				PreparedStatement ps = con.prepareStatement("insert into user(name, passwd) values(?,?)");
				ps.setString(1,user.getName());
				ps.setString(2,user.getPasswd());
				return ps.execute();
			}
		};
		return t.execute();
	}
	public static boolean findUser(User user)
	{
		try {
			User foundUser = UserDAO.get(user.getName());
			if(foundUser!=null && foundUser.getPasswd().equals(user.getPasswd()))
			{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;


	}
	public static User get(String name) throws Exception
	{
		JDBCTemplate<User> t= new Query<User>(DB_NAME,DB_USER_NAME,DB_PASSWD) {

			@Override
			public User doQuery(Connection con) throws Exception {
				PreparedStatement ps = con.prepareStatement("select * from user where name=?");
				ps.setString(1,name);
				ResultSet rs = ps.executeQuery();
				User user = null;
				if(rs.next())
				{
					user = new User(rs.getString("name"),rs.getString("passwd"));
				}
				return user;
			}
		};
		return t.execute();
	}
	public static List<User> getAll() throws Exception
	{
		JDBCTemplate<List<User>> t= new Query<List<User>>(DB_NAME,DB_USER_NAME,DB_PASSWD) {

			@Override
			public List<User> doQuery(Connection con) throws Exception {
				PreparedStatement ps = con.prepareStatement("select * from user");
				ResultSet rs = ps.executeQuery();
				List<User> list = new ArrayList<User>();
				while(rs.next())
				{
					list.add(new User(rs.getString("name"),rs.getString("passwd")));
				}
				return list;
			}
		};
		return t.execute();
	}
	public static boolean update(User newUser, String oldName) throws Exception
	{
	
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(DB_NAME,DB_USER_NAME,DB_PASSWD) {

			@Override
			public Boolean doTranscation(Connection con) throws Exception {
				PreparedStatement ps = con.prepareStatement("update user set name=?,passwd=? where name=?");
				ps.setString(1, newUser.getName());
				ps.setString(2,newUser.getPasswd());
				ps.setString(3,oldName);
				return ps.execute();
			}
			
		};
		return t.execute();
	}
	public static boolean delete(String name) throws Exception
	{
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(DB_NAME,DB_USER_NAME,DB_PASSWD) {

			@Override
			public Boolean doTranscation(Connection con) throws Exception {
				PreparedStatement ps = con.prepareStatement("delete from user where name=?");
				ps.setString(1, name);
				return ps.execute();
			}
			
		};
		return t.execute();
	}
	public static void main(String[] args)
	{
//		User user = new User("ma","2018ma");
//		User user1 = new User("ma","2018");
//		User user2 = new User("root","root");
		try {
//			UserDAO.add(user);
//			UserDAO.add(user1);
//			add(user2);
//			User t = get("root");
//			System.out.println(t);
//			update(user1,"ma");
			delete("root");
			List<User> list = getAll();
			for(User u: list)
			{
				System.out.println(u);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}










 