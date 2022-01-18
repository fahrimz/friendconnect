package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class AddPostResponse{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
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