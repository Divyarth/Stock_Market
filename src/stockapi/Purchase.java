package stockapi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.DataConnect;

/**
 * Servlet implementation class Purchase
 */
@WebServlet("/purchase")
public class Purchase extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Purchase() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPost(req, resp); //To change body of generated methods, choose Tools
		// | Templates.
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		try {
			String pORs = "PURCHASED";
			Connection conn = DataConnect.getConnection();
			Statement statement = conn.createStatement();
			String symbol = request.getParameter("symbol");
			String price = request.getParameter("price");
			String qty = request.getParameter("qty");
			String amt = request.getParameter("amt");
			System.out.println("pORs");
			statement.executeUpdate(
					"insert into purchase (`uid`,`stock_symbol`,`qty`,`price`,`amt`, purchaseOrSell)	VALUES "
							+ "(1111,'" + symbol + "','" + qty + "','" + price + "','" + (amt) + "' , '" + pORs + "')");
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}