package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class AddCommentResponse{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_comment")
	private int idComment;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	public int getIdPost(){
		return idPost;
	}

	public int getIdComment(){
		return idComment;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getBody(){
		return body;
	}
}