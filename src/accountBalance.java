import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "accountBalance")
@SessionScoped

public class accountBalance {
	private String userName;
	private String fullName;
	private String accountBalance;
	String user = login.user;
	String uid;
	private String output;
	private String id;
	private String stock_symbol;
	private String qty;
	private String price;
	private String amount;
	private String datePurchase;
	private String purchaseOrSell;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public accountBalance() {
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String accountBalanceHistory() {

		Connection con = null;
		String server_server = System.getenv("ICSI518_SERVER");
		String port_port = System.getenv("ICSI518_PORT");
		String db_db = System.getenv("ICSI518_DB");
		String user_user = System.getenv("ICSI518_USER");
		String password_password = System.getenv("ICSI518_PASSWORD");
		// DB Connection
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

			String sql = "SELECT * from clientregister where userName = '" + user + "' ";
			// System.out.println("checking1");
			PreparedStatement st = con.prepareStatement(sql);
			// st.setString(1, this.userName);
			ResultSet rs = st.executeQuery();
			// System.out.println("checking2");
			rs.next();

			fullName = rs.getString("fullName");
			accountBalance = rs.getString("accountBalance");
			output = "accountBalanceHistory";

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

	public List<accountBalance> getUserList() {
		System.out.println("checking1");
		uid = login.uid;
		List<accountBalance> list = new ArrayList<accountBalance>();
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

			String sql = "select * from purchase WHERE uid = '" + uid + "' ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				accountBalance usr = new accountBalance();
				usr.setId(rs.getString("id"));
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

	public List<accountBalance> GETUSERLIST() {
		System.out.println("checking1");
		uid = login.uid;
		List<accountBalance> list = new ArrayList<accountBalance>();
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
			System.out.println("cehck1");
			String sql = "select * from purchase WHERE uid = '" + uid + "' AND purchaseOrSell = 'PURCHASED' ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				accountBalance usr = new accountBalance();
				usr.setId(rs.getString("id"));
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
