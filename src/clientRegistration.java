
//import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "clientRegistration")
@SessionScoped

public class clientRegistration {

	private String userName;
	private String password;
	private String fullName;
	private String emailID;
	private String mobileNumber;
	// String sqlname;
	String output;
	// String loginUserName;
	// private String login_password;

	public clientRegistration() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String client_registration() {
		Connection con = null;
		String server_server = System.getenv("ICSI518_SERVER");
		String port_port = System.getenv("ICSI518_PORT");
		String db_db = System.getenv("ICSI518_DB");
		String user_user = System.getenv("ICSI518_USER");
		String password_password = System.getenv("ICSI518_PASSWORD");

		try {
			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(server_server);
			ds.setPortNumber(Integer.parseInt(port_port));
			ds.setDatabaseName(db_db);
			ds.setUser(user_user);
			ds.setPassword(password_password);

			// Get a connection object
			con = ds.getConnection();

			// String sql = "select * from usernameandpassword where userName = ?";

			// PreparedStatement st = con.prepareStatement(sql);
			// ResultSet rs = st.executeQuery();
			// rs.next();
			// st.setString(1, this.userName);
			// st.setString(2, this.password);
			// ResultSet rs = st.executeQuery();
			// rs.next();
			// loginUserName = rs.getString("userName");

			// if (loginUserName != userName) {

			// String sql = "select userName from usernameandpassword where userName = ?";
			String sql1 = "insert into clientregister(userName,password,fullName,emailID,mobileNumber) values(?,?,?,?,?)";
			// String sql2 = "insert into usernameandpassword(userName,password)
			// values(?,?)";
			// Get a prepared SQL statement
			// old table
			// String sql = "SELECT registration_name from jeenu where registration_username
			// = ?";
			// PreparedStatement st = con.prepareStatement(sql);
			// st.setString(1, this.userName);
			// ResultSet rs = st.executeQuery();
			// rs.next();
			// loginUserName = rs.getString("userName");

			// if (loginUserName.equals(userName)) {
			// FacesMessage facemessage1 = new FacesMessage("User name already exists.
			// Please try another user name");
			// facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
			// FacesContext.getCurrentInstance().addMessage(null, facemessage1);
			// }
			//
			// else {
			PreparedStatement st1 = con.prepareStatement(sql1);
			st1.setString(1, this.userName);
			st1.setString(2, this.password);
			st1.setString(3, this.fullName);
			st1.setString(4, this.emailID);
			st1.setString(5, this.mobileNumber);
			st1.executeUpdate();
			// PreparedStatement st2 = con.prepareStatement(sql2);
			// st2.setString(1, this.userName);
			// st2.setString(2, this.password);
			// st2.executeUpdate();
			// // }
			// if (loginUserName.equals(userName)) {
			// FacesMessage facemessage1 = new FacesMessage("Duplicate UserName");
			// facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
			// FacesContext.getCurrentInstance().addMessage(null, facemessage1);
			// }

		} catch (

		Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		// FacesMessage facemessage1 = new FacesMessage(
		// "Registration Successful, please enter your Username and Password to
		// continue.");
		// facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
		// FacesContext.getCurrentInstance().addMessage(null, facemessage1);
		// FacesMessage facemessage2 = new FacesMessage(
		// "***Note: ***: If you registered as 'MANAGER' you may not be able to login
		// now because your account first will be verified by 'ADMINISTRATOR', it will
		// be done in next 24hrs. ",
		// "INFO MESSAGE");
		// facemessage2.setSeverity(FacesMessage.SEVERITY_ERROR);
		// FacesContext.getCurrentInstance().addMessage(null, facemessage2);
		output = pageNavigation.loginOutput1();
		return output;

	}

}

//////////////////////////////////////////////////////////////////////////
// public void validateUserName(FacesContext context) {
//
// Connection con = null;
// String server_server = System.getenv("ICSI518_SERVER");
// String port_port = System.getenv("ICSI518_PORT");
// String db_db = System.getenv("ICSI518_DB");
// String user_user = System.getenv("ICSI518_USER");
// String password_password = System.getenv("ICSI518_PASSWORD");
//
// try {
// // Setup the DataSource object
// com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new
////////////////////////////////////////////////////////////////////////// com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
// ds.setServerName(server_server);
// ds.setPortNumber(Integer.parseInt(port_port));
// ds.setDatabaseName(db_db);
// ds.setUser(user_user);
// ds.setPassword(password_password);
//
// // Get a connection object
// con = ds.getConnection();
//
// String sql = "select * from usernameandpassword where userName = ?";
// PreparedStatement st = con.prepareStatement(sql);
// ResultSet rs = st.executeQuery();
// rs.next();
// st.setString(1, this.userName);
// loginUserName = rs.getString("userName");
//
// if (loginUserName.equals(userName)) {
// FacesMessage facemessage = new FacesMessage(
// "Registration Successful, please enter your Username and Password to
////////////////////////////////////////////////////////////////////////// continue.");
// facemessage.setSeverity(FacesMessage.SEVERITY_ERROR);
// FacesContext.getCurrentInstance().addMessage(null, facemessage);
//
// }
//
// } catch (
//
// Exception e) {
// System.err.println("Exception: " + e.getMessage());
// } finally {
// try {
// con.close();
// } catch (SQLException e) {
// }
// }
// }
