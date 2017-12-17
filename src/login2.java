import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "login2")
@SessionScoped

public class login2 {
	private String userName;
	private String password;
	String output;
	String loginUserName;
	String loginPassword;

	public login2() {
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

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String validateLogin() {
		// user = userName;
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
			String sql = "SELECT * from administrator where userName = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, this.userName);
			ResultSet rs = st.executeQuery();
			rs.next();

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName",
					rs.getString("userName"));
			// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mid",
			// rs.getString("mid"));
			loginUserName = rs.getString("userName");
			loginPassword = rs.getString("password");

			if (loginUserName.equals(userName) && loginPassword.equals(password)) {
				output = "adminHome";

			} else {

				FacesMessage facemessage = new FacesMessage(
						"***Invalid Login Credentials, Incorrect Username or Password***", "ERROR MESSAGE");
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
	}
}
