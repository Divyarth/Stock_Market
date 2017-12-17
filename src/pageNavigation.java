import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.login.SessionUtils;

@ManagedBean(name = "pageNavigation")
@SessionScoped
public class pageNavigation {

	// String index;
	// String login;
	// String registration;
	// String managerRegistration;
	// String clientRegistration;

	public pageNavigation() {
	}

	public String indexOutput() {
		return "index";
	}

	// public static String indexOutput1() {
	// return "index";
	// }

	public String loginOutput() {
		return "login";
	}

	public static String loginOutput1() {
		return "login";
	}

	public String registrationOutput() {
		return "registration";
	}

	// public static String registrationOutput1() {
	// return "registration";
	// }

	public String managerRegistrationOutput() {
		return "managerRegistration";
	}

	// public static String managerRegistrationOutput1() {
	// return "managerRegistration";
	// }

	public String clientRegistrationOutput() {
		return "clientRegistration";
	}

	public static String clientRegistrationOutput1() {
		return "clientRegistration";
	}

	public static String clientLoginOutput() {
		return "clientHome";
	}

	public String clientLoginOutput1() {
		return "clientHome";
	}

	public String searchViewBuy() {
		return "searchViewBuy";
	}

	public static String searchViewBuy1() {
		return "searchViewBuy";
	}

	public String clientProfileUpdateOutput() {
		return "clientProfileUpdate";
	}

	public String clientLogin() {
		return "clientLogin";
	}

	public String managerLogin() {
		return "managerLogin";
	}

	public String administratorLogin() {
		return "adminstratorLogin";
	}

	public String accountBalanceHistory() {
		return "accountBalanceHistory";
	}

	public String login() {
		return "login";
	}

	public static String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}

	// public static String clientProfileUpdateOutput1() {
	// return "clientProfileUpdate";
	// }

}
