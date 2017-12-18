import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "accountBalanceManager")
@SessionScoped

public class accountBalanceManager {
	private String userName;
	private String fullName;
	private String accountBalance;
	String user = login1.user;
	private String output;
	private String uid;
	private String mid;
	private String stock_symbol;
	private String qty;
	private String price;
	private String amount;
	private String datePurchase;
	private String purchaseOrSell;

	public accountBalanceManager() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getStock_symbol() {
		return stock_symbol;
	}

	public void setStock_symbol(String stock_symbol) {
		this.stock_symbol = stock_symbol;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getPurchaseOrSell() {
		return purchaseOrSell;
	}

	public void setPurchaseOrSell(String purchaseOrSell) {
		this.purchaseOrSell = purchaseOrSell;
	}

	public String accountBalanceHistory() {

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
			System.out.println("checking1");
			// Get a connection object
			con = ds.getConnection();
			// System.out.println("entering code");

			String sql1 = "SELECT * from managerRegister where username = '" + user + "' ";
			PreparedStatement st1 = con.prepareStatement(sql1);
			ResultSet rs1 = st1.executeQuery();
			rs1.next();
			mid = rs1.getString("mid");
			accountBalance = rs1.getString("accountbalance");
			fullName = rs1.getString("fullName");
			output = "accountBalanceManager";
			System.out.println(fullName);
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

	public List<accountBalanceManager> getUserList() {
		System.out.println("checking1");
		// mid = login1.mid;
		List<accountBalanceManager> list = new ArrayList<accountBalanceManager>();
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String server_server = System.getenv("ICSI518_SERVER");
		String port_port = System.getenv("ICSI518_PORT");
		String db_db = System.getenv("ICSI518_DB");
		String user_user = System.getenv("ICSI518_USER");
		String password_password = System.getenv("ICSI518_PASSWORD");
		System.out.println("checking2");
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

			String sql = "select * from history WHERE mid = '" + mid + "' ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				accountBalanceManager usr = new accountBalanceManager();
				usr.setUid(rs.getString("uid"));
				usr.setStock_symbol(rs.getString("stock_symbol"));
				usr.setQty(rs.getString("qty"));
				usr.setPrice(rs.getString("price"));
				usr.setAmount(rs.getString("amt"));
				usr.setDatePurchase(rs.getString("datePurchase"));
				usr.setPurchaseOrSell(rs.getString("purchaseOrSell"));
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

}
