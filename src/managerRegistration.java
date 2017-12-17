
//import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
//import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;

@ManagedBean(name = "managerRegistration")
@SessionScoped

public class managerRegistration {

	private String userName;
	private String password;
	private String fullName;
	private String emailID;
	private String mobileNumber;
	private String licenseNumber;
	private String companyName;
	private String fee;
	// String sqlname;
	String output;
	// private String login_username;
	// private String login_password;

	public managerRegistration() {

	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
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

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String manager_registration() {
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

			String sql = "insert into managerregister(userName,password,fullName,emailID,mobileNumber,licenseNumber,companyName,fee) values(?,?,?,?,?,?,?,?)";
			// String sql1 = "insert into usernameandpassword(userName,password)
			// values(?,?)";

			// Get a prepared SQL statement
			// old table
			// String sql = "SELECT registration_name from jeenu where registration_username
			// = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, this.userName);
			st.setString(2, this.password);
			st.setString(3, this.fullName);
			st.setString(4, this.emailID);
			st.setString(5, this.mobileNumber);
			st.setString(6, this.licenseNumber);
			st.setString(7, this.companyName);
			st.setString(8, this.fee);
			st.executeUpdate();
			// PreparedStatement st1 = con.prepareStatement(sql1);
			// st1.setString(1, this.userName);
			// st1.setString(2, this.password);
			// st1.executeUpdate();

			// Execute the statement
			// ResultSet rs = st.executeQuery();

			// Iterate through results
			// if (rs.next()) {
			// System.out.println("First Name is: " + rs.getString("name"));
			// this.name = rs.getString("name");
			// }
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		FacesMessage facemessage1 = new FacesMessage(
				"Registration Successful, please enter your Username and Password to continue.");
		facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, facemessage1);
		FacesMessage facemessage2 = new FacesMessage(
				"***Note: ***: If you registered as 'MANAGER' you may not be able to login now because your account first will be verified by 'ADMINISTRATOR', it will be done in next 24hrs. ",
				"INFO MESSAGE");
		facemessage2.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, facemessage2);
		output = pageNavigation.loginOutput1();
		return output;

		// return "result?faces-redirect=true";
	}

}