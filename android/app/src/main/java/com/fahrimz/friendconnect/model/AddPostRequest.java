package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class AddPostRequest{

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	public AddPostRequest(int idUser, String body) {
		this.idUser = idUser;
		this.body = body;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}
}