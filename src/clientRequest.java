import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "clientRequest")
@SessionScoped

public class clientRequest {
	String name;
	private String id;
	private String pid;
	private String uid;
	private String stock_symbol;
	private String qty;
	private String purchaseOrSell;

	public clientRequest() {
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getPurchaseOrSell() {
		return purchaseOrSell;
	}

	public void setPurchaseOrSell(String purchaseOrSell) {
		this.purchaseOrSell = purchaseOrSell;
	}

	public List<clientRequest> getUserList() {
		System.out.println("checking1");
		name = login1.fullName;
		List<clientRequest> list = new ArrayList<clientRequest>();
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

			String sql = "select * from clientrequest WHERE managerName = '" + name + "' ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				clientRequest usr = new clientRequest();
				usr.setPid(rs.getString("pid"));
				usr.setId(rs.getString("id"));
				usr.setUid(rs.getString("uid"));
				usr.setStock_symbol(rs.getString("stock_symbol"));
				usr.setQty(rs.getString("qty"));
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
