// package stockapi;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.security.SecureRandom;
// import java.security.cert.X509Certificate;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;
//
// import javax.annotation.PostConstruct;
// import javax.faces.application.FacesMessage;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.SessionScoped;
// import javax.faces.context.FacesContext;
// import javax.faces.model.SelectItem;
// import javax.json.Json;
// import javax.json.JsonObject;
// import javax.json.JsonReader;
// import javax.net.ssl.HttpsURLConnection;
// import javax.net.ssl.SSLContext;
// import javax.net.ssl.TrustManager;
// import javax.net.ssl.X509TrustManager;
// import javax.servlet.http.HttpServletRequest;
//
// import com.login.DataConnect;
//
// @ManagedBean(name = "StockApiBeanSell")
// @SessionScoped
// public class StockApiBeanSell {
//
// // private static final long serialVersionUID = 1L;
// // static final String API_KEY = "AF93E6L5I01EA39O";
// private String id;
// String fetchSymbol;
// String fetchQuantity;
// String pid;
// String price1;
// String totalPrice;
//
// public String getId() {
// return id;
// }
//
// public void setId(String id) {
// this.id = id;
// }
//
// public String multiply(String num1, String num2) {
// String n1 = new StringBuilder(num1).reverse().toString();
// String n2 = new StringBuilder(num2).reverse().toString();
// int[] d = new int[num1.length() + num2.length()];
// for (int i = 0; i < n1.length(); i++) {
// for (int j = 0; j < n2.length(); j++) {
// d[i + j] += (n1.charAt(i) - '0') * (n2.charAt(j) - '0');
// }
// }
// StringBuilder sb = new StringBuilder();
// for (int i = 0; i < d.length; i++) {
// int mod = d[i] % 10;
// int carry = d[i] / 10;
// if (i + 1 < d.length) {
// d[i + 1] += carry;
// }
// sb.insert(0, mod);
// }
// while (sb.charAt(0) == '0' && sb.length() > 1) {
// sb.deleteCharAt(0);
// }
// return sb.toString();
// }
//
// public String fetchSymbolQty() {
// pid = this.id;
// Connection con = null;
// String server_server = System.getenv("ICSI518_SERVER");
// String port_port = System.getenv("ICSI518_PORT");
// String db_db = System.getenv("ICSI518_DB");
// String user_user = System.getenv("ICSI518_USER");
// String password_password = System.getenv("ICSI518_PASSWORD");
// // System.out.println("fetch1");
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
// System.out.println("checking1");
// // Get a connection object
// con = ds.getConnection();
// // System.out.println("entering code");
// System.out.println("fetch1");
// String sql = "SELECT * from purchase where id = '" + pid + "' ";
// PreparedStatement st = con.prepareStatement(sql);
// ResultSet rs = st.executeQuery();
// rs.next();
// fetchSymbol = rs.getString("stock_symbol");
// fetchQuantity = rs.getString("qty");
// this.selectedInterval = "1min";
// this.selectedSymbol = fetchSymbol;
// totalPrice = multiply(fetchQuantity, price1);
//
// String sql1 = "insert into purchase
// (`uid`,`stock_symbol`,`qty`,`price`,`amt`,'purchaseOrSell') VALUES "
// + "(1111,'" + fetchSymbol + "','" + fetchQuantity + "','" + price + "','" +
// totalPrice
// + "', 'Sell' )";
// PreparedStatement st1 = con.prepareStatement(sql1);
// st1.executeQuery();
//
// } catch (Exception e) {
// System.err.println("Exception: " + e.getMessage());
// } finally {
// try {
// con.close();
// } catch (SQLException e) {
// }
// }
// return "clientHome";
// // return name;
// }
//
// // public void timeseries1() throws MalformedURLException, IOException {
// //
// // installAllTrustingManager();
// //
// // // System.out.println("selectedItem: " + this.selectedSymbol);
// // // System.out.println("selectedInterval: " + this.selectedInterval);
// // this.selectedInterval = "1min";
// // this.selectedSymbol = "Apple";
// // String symbol = this.selectedSymbol;
// // String interval = this.selectedInterval;
// //
// // System.out.println("selectedItem: " + symbol);
// // System.out.println("selectedInterval: " + interval);
// //
// // String url =
// // "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="
// +
// // symbol + "&interval="
// // + interval + "&apikey=" + API_KEY;
// //
// // this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
// // InputStream inputStream = new URL(url).openStream();
// //
// // // convert the json string back to object
// // System.out.println("check 1");
// // JsonReader jsonReader = Json.createReader(inputStream);
// // System.out.println("check 2");
// // JsonObject mainJsonObj = jsonReader.readObject();
// // System.out.println("check 3");
// // for (String key : mainJsonObj.keySet()) {
// // if (key.equals("Meta Data")) {
// // this.table1Markup = "-->"; // reset table 1 markup before repopulating
// // JsonObject jsob = (JsonObject) mainJsonObj.get(key);
// // this.table1Markup += "<style>#detail >tbody > tr > td{
// // text-align:center;}</style><b>Stock Details</b>:<br>";
// // this.table1Markup += "<table>";
// // this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1.
// // Information") + "</td></tr>";
// // this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2.
// Symbol")
// // + "</td></tr>";
// // this.table1Markup += "<tr><td>Last Refreshed</td><td>" +
// jsob.getString("3.
// // Last Refreshed")
// // + "</td></tr>";
// // this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4.
// // Interval") + "</td></tr>";
// // this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5.
// // Output Size") + "</td></tr>";
// // this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6.
// Time
// // Zone") + "</td></tr>";
// // this.table1Markup += "</table>";
// // } else {
// // this.table2Markup = "<--"; // reset table 2 markup before repopulating
// // JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
// // this.table2Markup += "<table class='table table-hover'>";
// // this.table2Markup +=
// //
// "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
// // this.table2Markup += "<tbody>";
// // int i = 0;
// // for (String subKey : dataJsonObj.keySet()) {
// // JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
// // this.table2Markup += "<tr>" + "<td>" + subKey + "</td>" + "<td>" +
// // subJsonObj.getString("1. open")
// // + "</td>" + "<td>" + subJsonObj.getString("2. high") + "</td>" + "<td>"
// // + subJsonObj.getString("3. low") + "</td>" + "<td>" +
// // subJsonObj.getString("4. close")
// // + "</td>" + "<td>" + subJsonObj.getString("5. volume") + "</td>";
// // if (i == 0) {
// // String path =
// //
// FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
// // this.table2Markup += "<td><a class='btn btn-success' href='" + path
// // + "/faces/purchase.xhtml?symbol=" + symbol + "&price="
// // + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
// // }
// // price1 = subJsonObj.getString("4. close");
// // this.table2Markup += "</tr>";
// // i++;
// // }
// // this.table2Markup += "</tbody></table>";
// // }
// // }
// // // return; }
//
// }
