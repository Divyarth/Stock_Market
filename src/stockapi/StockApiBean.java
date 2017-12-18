package stockapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.login.DataConnect;

@ManagedBean
@SessionScoped
public class StockApiBean {

	private static final long serialVersionUID = 1L;
	static final String API_KEY = "AF93E6L5I01EA39O";

	private String symbol;
	private double price;
	private int qty;
	private double amt;
	private int uidd;
	private int rid;
	private String table1Markup;
	private String table2Markup;
	private String id;
	private String sellID;
	String managerName;
	String fetchSymbol;
	String fetchQuantity;
	String pid;
	String price1;
	String totalPrice;
	String purchase;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getUidd() {
		return uidd;
	}

	public void setUidd(int uidd) {
		this.uidd = uidd;
	}

	public String getSellID() {
		return sellID;
	}

	public void setSellID(String sellID) {
		this.sellID = sellID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String selectedSymbol;
	private List<SelectItem> availableSymbols;

	public String getPurchaseSymbol() {
		if (getRequestParameter("symbol") != null) {
			symbol = getRequestParameter("symbol");
		}
		return symbol;
	}

	public void setPurchaseSymbol(String purchaseSymbol) {
		System.out.println("func setPurchaseSymbol()"); // check
	}

	public double getPurchasePrice() {
		if (getRequestParameter("price") != null) {
			price = Double.parseDouble(getRequestParameter("price"));
			System.out.println("price: " + price);
		}
		return price;
	}

	public void setPurchasePrice(double purchasePrice) {
		System.out.println("func setPurchasePrice()"); // check
	}

	private String getRequestParameter(String name) {
		return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter(name);
	}

	@PostConstruct
	public void init() {
		// initially populate stock list
		availableSymbols = new ArrayList<SelectItem>();
		availableSymbols.add(new SelectItem("AAPL", "Apple"));
		availableSymbols.add(new SelectItem("AMZN", "Amazon LLC"));
		availableSymbols.add(new SelectItem("AR", "Antero Resources"));
		availableSymbols.add(new SelectItem("EBAY", "Ebay"));
		availableSymbols.add(new SelectItem("FB", "Facebook, Inc."));
		availableSymbols.add(new SelectItem("GOLD", "Gold"));
		availableSymbols.add(new SelectItem("GOOGL", "Google"));
		availableSymbols.add(new SelectItem("MSFT", "Microsoft"));
		availableSymbols.add(new SelectItem("SLV", "Silver"));
		availableSymbols.add(new SelectItem("TWTR", "Twitter, Inc."));

		// initially populate intervals for stock api
		availableIntervals = new ArrayList<SelectItem>();
		availableIntervals.add(new SelectItem("1min", "1min"));
		availableIntervals.add(new SelectItem("5min", "5min"));
		availableIntervals.add(new SelectItem("15min", "15min"));
		availableIntervals.add(new SelectItem("30min", "30min"));
		availableIntervals.add(new SelectItem("60min", "60min"));
	}

	private String selectedInterval;
	private List<SelectItem> availableIntervals;

	public String getSelectedInterval() {
		return selectedInterval;
	}

	public void setSelectedInterval(String selectedInterval) {
		this.selectedInterval = selectedInterval;
	}

	public List<SelectItem> getAvailableIntervals() {
		return availableIntervals;
	}

	public void setAvailableIntervals(List<SelectItem> availableIntervals) {
		this.availableIntervals = availableIntervals;
	}

	public String getSelectedSymbol() {
		return selectedSymbol;
	}

	public void setSelectedSymbol(String selectedSymbol) {
		this.selectedSymbol = selectedSymbol;
	}

	public List<SelectItem> getAvailableSymbols() {
		return availableSymbols;
	}

	public void setAvailableSymbols(List<SelectItem> availableSymbols) {
		this.availableSymbols = availableSymbols;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public String getTable1Markup() {
		return table1Markup;
	}

	public void setTable1Markup(String table1Markup) {
		this.table1Markup = table1Markup;
	}

	public String getTable2Markup() {
		return table2Markup;
	}

	public void setTable2Markup(String table2Markup) {
		this.table2Markup = table2Markup;
	}

	public String createDbRecord(String symbol, double price, int qty, double amt) {
		Double amount = null;
		String pORs = "PURCHASED";
		try {
			// System.out.println("symbol: " + this.symbol + ", price: " + this.price +
			// "\n");
			// System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();

			// get userid
			Integer uid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid"));

			System.out.println(uid);
			System.out.println("symbol:" + symbol);
			System.out.println("price:" + price);
			System.out.println("qty:" + qty);
			System.out.println("amt:" + amt);
			statement.executeUpdate(
					"INSERT INTO `purchase` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchaseOrSell`) "
							+ "VALUES (NULL,'" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt
							+ "', '" + pORs + "')");
			statement.executeUpdate(
					"INSERT INTO `history` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchaseOrSell`) "
							+ "VALUES (NULL,'" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt
							+ "', '" + pORs + "')");

			String sql1 = "SELECT * from clientregister WHERE uid = '" + uid + "' ";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			ResultSet rs = st1.executeQuery();
			rs.next();
			amount = rs.getDouble("accountBalance");
			// System.out.println("Checking2");
			amount = amount - amt;
			// System.out.println("Checking3");

			String sql2 = "UPDATE clientregister SET accountbalance = '" + amount + "' WHERE uid = '" + uid + "' ";
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.executeUpdate();

			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "purchase";
	}

	public String createDbRecordd(String symbol, double price, int qty, double amt) {
		purchase = "Purchase";
		try {
			// System.out.println("symbol: " + this.symbol + ", price: " + this.price +
			// "\n");
			// System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();

			// get userid
			Integer uid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid"));

			System.out.println(uid);
			System.out.println("symbol:" + symbol);
			System.out.println("price:" + price);
			System.out.println("qty:" + qty);
			System.out.println("amt:" + amt);
			String sql = "SELECT * from clientregister where uid = '" + uid + "'";
			PreparedStatement st = conn.prepareStatement(sql);
			// st.setString(1, this.userName);
			ResultSet rs = st.executeQuery();
			rs.next();
			managerName = rs.getString("manager");
			statement.executeUpdate(
					"INSERT INTO `clientrequest` (`uid`, `stock_symbol`, `qty`, `purchaseOrSell` , `managerName` ) "
							+ "VALUES ('" + uid + "','" + symbol + "','" + qty + "', '" + purchase + "' , '"
							+ managerName + "')");

			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Requested Manger", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "purchase";
	}

	public String createDbRecorddd(String symbol, double price, int qty, double amt) {
		String id;
		String ss;
		String q;
		String pos;
		String mn;
		Double amount = null;
		String pORs = "Sell";

		// String pORs = "PURCHASED";
		try {
			// System.out.println("symbol: " + this.symbol + ", price: " + this.price +
			// "\n");
			// System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();

			// get userid
			Integer uid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid"));

			System.out.println(uid);
			// System.out.println("symbol:" + symbol);
			// System.out.println("price:" + price);
			// System.out.println("qty:" + qty);
			// System.out.println("amt:" + amt);

			String sql0 = "SELECT * from clientregister WHERE uid = '" + uid + "' ";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			ResultSet rs0 = st0.executeQuery();
			rs0.next();
			mn = rs0.getString("manager");

			String sql = "SELECT * from purchase WHERE id = '" + qty + "' ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			rs.next();
			ss = rs.getString("stock_symbol");
			q = rs.getString("qty");
			id = rs.getString("id");
			amount = price * qty;
			amt = amount;

			statement.executeUpdate(
					"INSERT INTO `clientrequest` (`id`, `uid`, `stock_symbol`, `qty`, `purchaseOrSell` , `managername`, `pid`) "
							+ "VALUES (NULL,'" + uid + "','" + ss + "','" + q + "','" + pORs + "', '" + mn + "', '" + id
							+ "')");

			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Requested Manger", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "sell";
	}

	public String createDbRecord1(String symbol, double price, int qty, double amt) {
		String quantity;
		Double amount = null;
		String pORs = "SOLD";

		// String pORs = "PURCHASED";
		try {
			// System.out.println("symbol: " + this.symbol + ", price: " + this.price +
			// "\n");
			// System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();

			// get userid
			Integer uid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid"));

			System.out.println(uid);
			System.out.println("symbol:" + symbol);
			System.out.println("price:" + price);
			System.out.println("qty:" + qty);
			// System.out.println("amt:" + amt);

			String sql = "SELECT * from purchase WHERE id = '" + qty + "' ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			rs.next();
			quantity = rs.getString("qty");
			amount = price * qty;
			amt = amount;

			statement.executeUpdate("UPDATE purchase SET amt = '" + amt + "', purchaseOrSell = '" + pORs
					+ "' WHERE id = '" + qty + "'");

			// statement.executeUpdate("UPDATE `purchase` SET (`amt`, `purchaseOrSell`) " +
			// "VALUES ('" + amt + "', '"
			// + pORs + "') WHERE id = '" + qty + "' ");

			statement.executeUpdate(
					"INSERT INTO `history` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchaseOrSell`) "
							+ "VALUES (NULL,'" + uid + "','" + symbol + "','" + quantity + "','" + price + "','" + amt
							+ "', '" + pORs + "')");

			String sql1 = "SELECT * from clientregister WHERE uid = '" + uid + "' ";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			ResultSet rs1 = st1.executeQuery();
			rs1.next();
			amount = rs1.getDouble("accountBalance");
			System.out.println(amount);
			amount = amount + amt;
			// System.out.println("Checking3");

			String sql2 = "UPDATE clientregister SET accountbalance = '" + amount + "' WHERE uid = '" + uid + "' ";
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.executeUpdate();
			// System.out.println("Checking2");
			// amount = amount - amt;
			// System.out.println("Checking3");

			//// String sql2 = "UPDATE clientregister SET accountbalance = '" + amount + "'
			//// WHERE uid = '" + uid + "' ";
			// PreparedStatement st2 = conn.prepareStatement(sql2);
			// st2.executeUpdate();

			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Sold Stock", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "sell";
	}

	public String createDbRecordPurchase(int rid, int uidd, String symbol, double price, int qty, double amt) {
		Double amount = null;
		String pORs = "PURCHASED";
		String pORsBY = "MANAGER";
		int accountBalance;
		int fee;

		try {
			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();
			Integer mid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mid"));
			System.out.println("mid:" + mid);

			System.out.println("uidd:" + uidd);
			System.out.println("rid:" + rid);
			System.out.println("symbol:" + symbol);
			System.out.println("price:" + price);
			System.out.println("qty:" + qty);
			System.out.println("amt:" + amt);
			statement.executeUpdate(
					"INSERT INTO `history` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchasedBy`, `purchaseOrSell`, `mid`) "
							+ "VALUES (NULL,'" + uidd + "','" + symbol + "','" + qty + "','" + price + "','" + amt
							+ "', '" + pORsBY + "', '" + pORs + "', '" + mid + "')");
			System.out.println("history update done");
			//
			// String sql1 = "UPDATE purchase SET purchaseOrSell = '" + pORs + "' WHERE uid
			// = '" + uidd + "' AND qty = '"
			// + qty + "' AND ";
			// PreparedStatement st1 = conn.prepareStatement(sql1);
			// st1.executeUpdate();

			statement.executeUpdate(
					"INSERT INTO `purchase` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchasedBy`, `purchaseOrSell`) "
							+ "VALUES (NULL,'" + uidd + "','" + symbol + "','" + qty + "','" + price + "','" + amt
							+ "', '" + pORsBY + "', '" + pORs + "')");
			System.out.println("purchase update done");

			String sql = "SELECT * from clientregister WHERE uid = '" + uidd + "' ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			rs.next();
			amount = rs.getDouble("accountBalance");
			System.out.println("Client Account Balance:" + amount);

			String sql0 = "SELECT * from managerregister WHERE mid = '" + mid + "' ";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			ResultSet rs0 = st0.executeQuery();
			rs0.next();
			fee = rs0.getInt("fee");
			accountBalance = rs0.getInt("accountBalance");
			System.out.println("Manager fee" + fee);
			System.out.println("Manager Account Balance" + accountBalance);
			accountBalance = accountBalance + fee;
			System.out.println(" Manager Updated Account Balance" + accountBalance);
			amount = amount - fee;
			System.out.println("Updated Client Account Balance after subtracting fee" + amount);

			amount = amount - amt;
			System.out.println("Updated Client Account Balance after subtracting fee" + amount);

			String sql3 = "UPDATE clientregister SET accountbalance = '" + amount + "' WHERE uid = '" + uidd + "' ";
			PreparedStatement st3 = conn.prepareStatement(sql3);
			st3.executeUpdate();
			System.out.println("clientRegister update done");
			String sql2 = "DELETE FROM clientRequest WHERE id ='" + rid + "' ";
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.executeUpdate();
			System.out.println("clientRequest update done");
			String sql4 = "UPDATE managerregister SET accountbalance = '" + accountBalance + "' WHERE mid = '" + mid
					+ "' ";
			PreparedStatement st4 = conn.prepareStatement(sql4);
			st4.executeUpdate();
			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Purchased Stock For Manager", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "purchase2";
	}

	public String createDbRecordSell(int rid, int uidd, String symbol, double price, int qty, double amt) {
		Double amount = null;
		String pORs = "SOLD";
		String pORsBY = "MANAGER";
		String id;
		Double amtt;
		int accountBalance;
		int fee;
		try {
			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();
			Integer mid = Integer.parseInt(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mid"));

			System.out.println("mid:" + mid);
			System.out.println("uidd:" + uidd);
			System.out.println("rid:" + rid);
			System.out.println("symbol:" + symbol);
			System.out.println("price:" + price);
			System.out.println("qty:" + qty);
			System.out.println("amt:" + amt);

			String sql5 = "SELECT * from clientrequest WHERE id = '" + rid + "' ";
			PreparedStatement st5 = conn.prepareStatement(sql5);
			ResultSet rs5 = st5.executeQuery();
			rs5.next();
			id = rs5.getString("pid");
			System.out.println("pid:" + id);

			String sql1 = "UPDATE purchase SET purchaseOrSell = '" + pORs + "', purchasedBy = '" + pORsBY
					+ "'  WHERE id = '" + id + "' ";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			st1.executeUpdate();

			statement.executeUpdate(
					"INSERT INTO `history` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`, `purchasedBy`, `purchaseOrSell`, `mid`) "
							+ "VALUES (NULL,'" + uidd + "','" + symbol + "','" + qty + "','" + price + "','" + amt
							+ "', '" + pORsBY + "', '" + pORs + "', '" + mid + "')");
			System.out.println("history update done");

			String sql = "SELECT * from clientregister WHERE uid = '" + uidd + "' ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			rs.next();
			amtt = rs.getDouble("accountBalance");

			// amtt = amount;
			System.out.println("Client Account Balance:" + amtt);

			String sql0 = "SELECT * from managerregister WHERE mid = '" + mid + "' ";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			ResultSet rs0 = st0.executeQuery();
			rs0.next();
			fee = rs0.getInt("fee");
			accountBalance = rs0.getInt("accountBalance");

			System.out.println("Manager fee" + fee);
			System.out.println("Manager Account Balance" + accountBalance);
			accountBalance = accountBalance + fee;
			System.out.println(" Manager Updated Account Balance" + accountBalance);

			amount = amtt - fee;
			System.out.println("Updated Client Account Balance after subtracting fee" + amount);

			amount = amount + amt;
			// System.out.println("Updated Client Account Balance after subtracting fee" +
			// amount);

			System.out.println(" uid " + uidd);
			String sql6 = "UPDATE clientregister SET accountbalance = '" + amount + "' WHERE uid = '" + uidd + "' ";
			PreparedStatement st6 = conn.prepareStatement(sql6);
			st6.executeUpdate();
			System.out.println("Updated Client Account Balance after adding selling stock" + amount);

			System.out.println("clientRegister update done");

			String sql2 = "DELETE FROM clientRequest WHERE id ='" + rid + "' ";
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.executeUpdate();
			System.out.println("clientRequest update done");

			String sql3 = "UPDATE managerregister SET accountbalance = '" + accountBalance + "' WHERE mid = '" + mid
					+ "' ";
			PreparedStatement st3 = conn.prepareStatement(sql3);
			st3.executeUpdate();
			statement.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Sold Stock Via Manager", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "purchase2";
	}

	public void installAllTrustingManager() {
		TrustManager[] trustAllCerts;
		trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return;
	}

	public void timeseries() throws MalformedURLException, IOException {

		installAllTrustingManager();

		// System.out.println("selectedItem: " + this.selectedSymbol);
		// System.out.println("selectedInterval: " + this.selectedInterval);
		String symbol = this.selectedSymbol;
		String interval = this.selectedInterval;
		String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval="
				+ interval + "&apikey=" + API_KEY;

		this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
		InputStream inputStream = new URL(url).openStream();

		// convert the json string back to object
		JsonReader jsonReader = Json.createReader(inputStream);
		JsonObject mainJsonObj = jsonReader.readObject();
		for (String key : mainJsonObj.keySet()) {
			if (key.equals("Meta Data")) {
				this.table1Markup = null; // reset table 1 markup before repopulating
				JsonObject jsob = (JsonObject) mainJsonObj.get(key);
				this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
				this.table1Markup += "<table>";
				this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
				this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
				this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed")
						+ "</td></tr>";
				this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
				this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
				this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
				this.table1Markup += "</table>";
			} else {
				this.table2Markup = null; // reset table 2 markup before repopulating
				JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
				this.table2Markup += "<table class='table table-hover'>";
				this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
				this.table2Markup += "<tbody>";
				int i = 0;
				for (String subKey : dataJsonObj.keySet()) {
					JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
					this.table2Markup += "<tr>" + "<td>" + subKey + "</td>" + "<td>" + subJsonObj.getString("1. open")
							+ "</td>" + "<td>" + subJsonObj.getString("2. high") + "</td>" + "<td>"
							+ subJsonObj.getString("3. low") + "</td>" + "<td>" + subJsonObj.getString("4. close")
							+ "</td>" + "<td>" + subJsonObj.getString("5. volume") + "</td>";
					if (i == 0) {
						String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
						this.table2Markup += "<td><a class='btn btn-success' href='" + path
								+ "/faces/purchase.xhtml?symbol=" + symbol + "&price="
								+ subJsonObj.getString("4. close") + "'>Buy Stock Or Request Manger</a></td>";
					}

					this.table2Markup += "</tr>";
					i++;
					price1 = subJsonObj.getString("4. close");
				}
				this.table2Markup += "</tbody></table>";
			}
		}
		return;
	}

	public void timeseries1() throws MalformedURLException, IOException {

		installAllTrustingManager();

		// System.out.println("selectedItem: " + this.selectedSymbol);
		// System.out.println("selectedInterval: " + this.selectedInterval);
		String symbol = this.selectedSymbol;
		String interval = this.selectedInterval;
		String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval="
				+ interval + "&apikey=" + API_KEY;

		this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
		InputStream inputStream = new URL(url).openStream();

		// convert the json string back to object
		JsonReader jsonReader = Json.createReader(inputStream);
		JsonObject mainJsonObj = jsonReader.readObject();
		for (String key : mainJsonObj.keySet()) {
			if (key.equals("Meta Data")) {
				this.table1Markup = null; // reset table 1 markup before repopulating
				JsonObject jsob = (JsonObject) mainJsonObj.get(key);
				this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
				this.table1Markup += "<table>";
				this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
				this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
				this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed")
						+ "</td></tr>";
				this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
				this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
				this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
				this.table1Markup += "</table>";
			} else {
				this.table2Markup = null; // reset table 2 markup before repopulating
				JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
				this.table2Markup += "<table class='table table-hover'>";
				this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
				this.table2Markup += "<tbody>";
				int i = 0;
				for (String subKey : dataJsonObj.keySet()) {
					JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
					this.table2Markup += "<tr>" + "<td>" + subKey + "</td>" + "<td>" + subJsonObj.getString("1. open")
							+ "</td>" + "<td>" + subJsonObj.getString("2. high") + "</td>" + "<td>"
							+ subJsonObj.getString("3. low") + "</td>" + "<td>" + subJsonObj.getString("4. close")
							+ "</td>" + "<td>" + subJsonObj.getString("5. volume") + "</td>";
					if (i == 0) {
						String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
						this.table2Markup += "<td><a class='btn btn-success' href='" + path
								+ "/faces/sell.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close")
								+ "'>Sell Stock</a></td>";
					}

					this.table2Markup += "</tr>";
					i++;
					price1 = subJsonObj.getString("4. close");
				}
				this.table2Markup += "</tbody></table>";
			}
		}
		return;
	}

	public void timeseries2() throws MalformedURLException, IOException {

		installAllTrustingManager();

		// System.out.println("selectedItem: " + this.selectedSymbol);
		// System.out.println("selectedInterval: " + this.selectedInterval);
		String symbol = this.selectedSymbol;
		String interval = this.selectedInterval;
		String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval="
				+ interval + "&apikey=" + API_KEY;

		this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
		InputStream inputStream = new URL(url).openStream();

		// convert the json string back to object
		JsonReader jsonReader = Json.createReader(inputStream);
		JsonObject mainJsonObj = jsonReader.readObject();
		for (String key : mainJsonObj.keySet()) {
			if (key.equals("Meta Data")) {
				this.table1Markup = null; // reset table 1 markup before repopulating
				JsonObject jsob = (JsonObject) mainJsonObj.get(key);
				this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
				this.table1Markup += "<table>";
				this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
				this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
				this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed")
						+ "</td></tr>";
				this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
				this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
				this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
				this.table1Markup += "</table>";
			} else {
				this.table2Markup = null; // reset table 2 markup before repopulating
				JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
				this.table2Markup += "<table class='table table-hover'>";
				this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
				this.table2Markup += "<tbody>";
				int i = 0;
				for (String subKey : dataJsonObj.keySet()) {
					JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
					this.table2Markup += "<tr>" + "<td>" + subKey + "</td>" + "<td>" + subJsonObj.getString("1. open")
							+ "</td>" + "<td>" + subJsonObj.getString("2. high") + "</td>" + "<td>"
							+ subJsonObj.getString("3. low") + "</td>" + "<td>" + subJsonObj.getString("4. close")
							+ "</td>" + "<td>" + subJsonObj.getString("5. volume") + "</td>";
					if (i == 0) {
						String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
						this.table2Markup += "<td><a class='btn btn-success' href='" + path
								+ "/faces/purchase2.xhtml?symbol=" + symbol + "&price="
								+ subJsonObj.getString("4. close") + "'>Buy Or Sell Stock</a></td>";
					}

					this.table2Markup += "</tr>";
					i++;
					price1 = subJsonObj.getString("4. close");
				}
				this.table2Markup += "</tbody></table>";
			}
		}
		return;
	}

	public void purchaseStock() {
		System.out.println("Calling function purchaseStock()");
		System.out.println("stockSymbol: "
				+ FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockSymbol"));
		System.out.println("stockPrice"
				+ FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockPrice"));
		return;
	}

}
