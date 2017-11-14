package com.tecsup.gestion.model;

import javax.validation.constraints.NotEmpty;

public class Credential {

	@NotEmpty
	String login;

	@NotEmpty
	String password;

	public Credential() {

	}

	public Credential(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
