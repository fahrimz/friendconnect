package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class AddCommentRequest{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	public AddCommentRequest(int idPost, int idUser, String body) {
		this.idPost = idPost;
		this.idUser = idUser;
		this.body = body;
	}

	public int getIdPost(){
		return idPost;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getBody(){
		return body;
	}
}