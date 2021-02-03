package pl.nzc;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

public class DBUtils {
	
	public static boolean ifUserExists(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select * from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		boolean ifExists = false;
		if (rs.next()) {
			ifExists = true;
		}
		return ifExists;
	}
	
	public static String findUsersPassword(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select Password from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		String password = null;
		if (rs.next()) {
			password = rs.getString("Password");
		}
		return password;
	}
	
	public static int findUsersAvailability(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select AVAILABILITY from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		int availability = 0;
		if (rs.next()) {
			availability = rs.getInt("AVAILABILITY");
		}
		return availability;
	}
	
	public static int findUsersNoOfIncorrectPasswords(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select NoINCORRECTPASSWORDS from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		int noOfIncorrectPasswords = 0;
		if (rs.next()) {
			noOfIncorrectPasswords = rs.getInt("NoINCORRECTPASSWORDS");
		}
		return noOfIncorrectPasswords;
	}
	
	public static Timestamp findUsersDisableToDate(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select DISABLETO from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		Timestamp disableToDate = null;
		if (rs.next()) {
			disableToDate = rs.getTimestamp("DISABLETO");
		}
		return disableToDate;
	}
	
	public static String[] findUsersData(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select login, Password, AVAILABILITY, NoINCORRECTPASSWORDS, DISABLETO from User where login = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		String[] usersData = {null, null, null, null, null};
		if (rs.next()) {
			usersData[0] = rs.getString("login");
			usersData[1] = rs.getString("Password");
			usersData[2] = rs.getString("AVAILABILITY");
			usersData[3] = rs.getString("NoINCORRECTPASSWORDS");
			usersData[4] = rs.getString("DISABLETO");
		}
		return usersData;
	}
	
	public static void updateNoOfIncorrectPasswords(String userName, int noOfIncorrectPasswords) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Update User set NoINCORRECTPASSWORDS =? where	login=? ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, noOfIncorrectPasswords);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}
	
	public static void updateDisableToDate(String userName, Timestamp date) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Update User set disableTo =DATEADD('DAY',3, ?) where login=? ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setTimestamp(1, date);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}
	
	public static void updateAvailability(String userName, int availability) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Update User set availability =? where	login=? ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, availability);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}
	
	public static boolean ifPasswordWasUsed(String userName, String newPassword) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "select password from (Select * from (select top 2 * from passwordstory order by enddate desc) where users_LOGIN =?) where password =?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, newPassword);
		ResultSet rs = pstm.executeQuery();
		boolean ifExists = false;
		if (rs.next()) {
			ifExists = true;
		}
		return ifExists;
	}

	public static void updatePassword(String userName, String password) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Update User set password =? where	login=? ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, password);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}
	
	public static void updatePasswordStory(String userName, String password) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "INSERT INTO passwordstory(users_LOGIN, PASSWORD, startdate, enddate) VALUES(?,?,?,DATEADD('DAY',3, ?)) ";
		long now = System.currentTimeMillis();
		Timestamp startdate = new Timestamp(now);
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		pstm.setTimestamp(3, startdate);
		pstm.setTimestamp(4, startdate);
		pstm.executeUpdate();
	}
	
	public static Timestamp datePasswordValid(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Select ps.enddate from passwordstory ps join User u on u.login=ps.users_LOGIN where ps.users_LOGIN = ? and ps.password=u.Password";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		Timestamp enddate = null;
		if (rs.next()) {
			enddate = rs.getTimestamp("enddate");
		}
		return enddate;
	}
	
	public static void updateLoginStory(String userName, String description, Timestamp logout) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "INSERT INTO loginstory(users_LOGIN, description, login , logout ) VALUES(?,?,?,?) ";
		long now = System.currentTimeMillis();
		Timestamp startdate = new Timestamp(now);
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, description);
		pstm.setTimestamp(3, startdate);
		pstm.setTimestamp(4, logout);
		pstm.executeUpdate();
	}
	
	public static void updateLoginStorySetLogoutDate(String userName) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		String sql = "Update loginstory set logout=? where	users_LOGIN=? and logout is null";
		long now = System.currentTimeMillis();
		Timestamp logout = new Timestamp(now);
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setTimestamp(1, logout);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}
	
	 public static void storeLoginedUser(HttpSession session, String userName) {
		 session.setAttribute("userName", userName);
	 }
	 
	 public static String getLoginedUser(HttpSession session) {
		 String userName = (String)session.getAttribute("userName");
		 return userName;
	 }
	
}
