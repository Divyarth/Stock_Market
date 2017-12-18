import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "login1")
@SessionScoped

public class login1 {
	private String userName;
	private String password;
	static String displayUserName;
	static String fullName;
	static String mid;
	private String emailID;
	private String mobileNumber;
	private String licenseNumber;
	private String companyName;
	private String approval;
	private String fee;
	static String user;
	String output;
	String site;
	String loginUserName;
	String loginPassword;
	String number;

	public login1() {
	}

	public static String getMid() {
		return mid;
	}

	public static void setMid(String mid) {
		login1.mid = mid;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getDisplayUserName() {
		return displayUserName;
	}

	public void setDisplayUserName(String displayUserName) {
		login1.displayUserName = displayUserName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		login1.fullName = fullName;
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

	public String validateLogin() {
		// user = userName;
		number = "1";
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
			// System.out.println("entering code");
			String sql = "SELECT * from managerregister where userName = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, this.userName);
			ResultSet rs = st.executeQuery();
			rs.next();
			mid = rs.getString("mid");
			displayUserName = rs.getString("userName");
			user = displayUserName;
			mobileNumber = rs.getString("mobileNumber");
			emailID = rs.getString("emailID");
			fullName = rs.getString("fullName");
			licenseNumber = rs.getString("licenseNumber");
			companyName = rs.getString("companyName");
			fee = rs.getString("fee");
			approval = rs.getString("approval");
			System.out.println(approval);
			// String sql1 = "SELECT * from clientregister where userName = ?";
			// PreparedStatement st1 = con.prepareStatement(sql1);
			// st1.setString(1, this.userName);
			// // st.setString(2, this.password);
			// ResultSet rs1 = st1.executeQuery();
			// rs1.next();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName",
					rs.getString("userName"));
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mid", rs.getString("mid"));
			loginUserName = rs.getString("userName");
			loginPassword = rs.getString("password");

			if (approval.equals(number)) {
				if (loginUserName.equals(userName) && loginPassword.equals(password)) {

					output = "managerHome";
					// HttpSession session = SessionUtils.getSession();
					// session.setAttribute("username", username);

					// return "output";
				} else {

					FacesMessage facemessage = new FacesMessage(
							"***Invalid Login Credentials, Incorrect Username or Password***", "ERROR MESSAGE");
					facemessage.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, facemessage);
				}
			} else {
				FacesMessage facemessage = new FacesMessage("***Not yet approved by administrator***", "ERROR MESSAGE");
				facemessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, facemessage);
			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return output;
		// return name;
	}
}
//
// public String validateLogin1() {
// user = userName;
// Connection con = null;
// String server_server = System.getenv("ICSI518_SERVER");
// String port_port = System.getenv("ICSI518_PORT");
// String db_db = System.getenv("ICSI518_DB");
// String user_user = System.getenv("ICSI518_USER");
// String password_password = System.getenv("ICSI518_PASSWORD");
//
// try {
//
// // Setup the DataSource object
// com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new
// com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
// ds.setServerName(server_server);
// ds.setPortNumber(Integer.parseInt(port_port));
// ds.setDatabaseName(db_db);
// ds.setUser(user_user);
// ds.setPassword(password_password);
//
// // Get a connection object
// con = ds.getConnection();
// // System.out.println("entering code");
// String sql = "SELECT * from clientregister where userName = ?";
// PreparedStatement st = con.prepareStatement(sql);
// st.setString(1, this.userName);
// ResultSet rs = st.executeQuery();
// rs.next();
// displayUserName = rs.getString("userName");
// mobileNumber = rs.getString("mobileNumber");
// emailID = rs.getString("emailID");
// fullName = rs.getString("fullName");
//
// String sql1 = "SELECT * from clientregister where userName = ?";
// PreparedStatement st1 = con.prepareStatement(sql1);
// st1.setString(1, this.userName);
// // st.setString(2, this.password);
// ResultSet rs1 = st1.executeQuery();
// rs1.next();
// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName",
// rs.getString("userName"));
// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid",
// rs.getString("uid"));
// loginUserName = rs.getString("userName");
// loginPassword = rs.getString("password");
//
// if (loginUserName.equals(userName) && loginPassword.equals(password)) {
//
// output = "clientHome";
// // HttpSession session = SessionUtils.getSession();
// // session.setAttribute("username", username);
//
// // return "output";
// } else {
//
// FacesMessage facemessage = new FacesMessage(
// "***Invalid Login Credentials, Incorrect Username or Password***", "ERROR
// MESSAGE");
// facemessage.setSeverity(FacesMessage.SEVERITY_ERROR);
// FacesContext.getCurrentInstance().addMessage(null, facemessage);
//
// // output = "index";
// // return "output";
// }
//
// } catch (Exception e) {
// System.err.println("Exception: " + e.getMessage());
// } finally {
// try {
// con.close();
// } catch (SQLException e) {
// }
// }
// return output;
// // return name;
// }
// }
//// public String logout() {
//// HttpSession session = SessionUtils.getSession();
//// session.invalidate();
//// output = "login";
//// return output;
//// }
