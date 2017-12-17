// package com.login;
//
// import java.io.IOException;
//
// import javax.faces.application.FacesMessage;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.SessionScoped;
// import javax.faces.context.FacesContext;
//
// @ManagedBean
// @SessionScoped
// public class UserBean {
//
// private String userName;
// private String password;
//
// public String getUserName() {
// return userName;
// }
//
// public void setUserName(String userName) {
// this.userName = userName;
// }
//
// public String getPassword() {
// return password;
// }
//
// public void setPassword(String password) {
// this.password = password;
// }
//
// public String validateUser() {
// boolean valid = LoginDAO.validate(userName, password);
// if (valid == true) {
//
// return "userhome?faces-redirect=true";
// } else {
// FacesContext.getCurrentInstance().addMessage(null, new
// FacesMessage(FacesMessage.SEVERITY_WARN,
// "Incorrect Username and Passowrd. Please enter correct username and
// Password", ""));
// return "login";
// }
// }
//
// // logout event, invalidate session
// public void logout() throws IOException {
// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
// FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
// }
// }
