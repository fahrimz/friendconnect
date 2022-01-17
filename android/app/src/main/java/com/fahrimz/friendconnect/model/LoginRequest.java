package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest{
	@SerializedName("password")
	private String password;

	@SerializedName("username")
	private String username;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}