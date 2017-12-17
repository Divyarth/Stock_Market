import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "adminManager")
@SessionScoped
public class adminManager {
	private String mid;
	private String fullName;
	private String emailID;
	private String mobileNumber;
	private String companyName;
	private String fee;
	private String managerID;
	String approve;
	String MID;
	String output;
	// String forUserName = login.user;

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public adminManager() {
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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

	public List<clientManager> getUserList() {
		List<clientManager> list = new ArrayList<clientManager>();
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
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

			String sql = "select * from managerRegister WHERE approval = 0";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				clientManager usr = new clientManager();
				usr.setMid(rs.getString("mid"));
				usr.setFullName(rs.getString("fullName"));
				usr.setEmailID(rs.getString("emailID"));
				usr.setMobileNumber(rs.getString("mobileNumber"));
				usr.setCompanyName(rs.getString("companyName"));
				usr.setFee(rs.getString("fee"));
				list.add(usr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String approveManager() {
		MID = this.managerID;
		approve = "1";
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
			String sql1 = "UPDATE managerregister SET approval = ? WHERE mid = '" + MID + "'  ";
			System.out.println("Checking1");
			PreparedStatement st1 = con.prepareStatement(sql1);
			System.out.println("Checking2");
			st1.setString(1, approve);
			System.out.println("Checking3");
			st1.executeUpdate();
			System.out.println("Checking4");
			// PreparedStatement st2 = con.prepareStatement(sql2);
			// st2.setString(1, this.userName);
			// st2.setString(2, this.password);
			// st2.executeUpdate();
			// }
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
		FacesMessage facemessage1 = new FacesMessage("Manager Approved");
		facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, facemessage1);
		output = "adminHome";
		return output;
	}

	public String disapproveManager() {
		MID = this.managerID;
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
			String sql1 = "DELETE FROM managerregister WHERE mid = '" + MID + "' ";
			PreparedStatement st1 = con.prepareStatement(sql1);
			// st1.setString(1, this.managerID);
			st1.executeUpdate();
			// PreparedStatement st2 = con.prepareStatement(sql2);
			// st2.setString(1, this.userName);
			// st2.setString(2, this.password);
			// st2.executeUpdate();
			// }
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
		FacesMessage facemessage1 = new FacesMessage("Manager Disapproved");
		facemessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, facemessage1);
		output = "adminHome";
		return output;

	}

}