package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_comment")
	private int idComment;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	@SerializedName("username")
	private String username;

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
	}

	public void setIdComment(int idComment){
		this.idComment = idComment;
	}

	public int getIdComment(){
		return idComment;
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

	public String getUsername(){
		return username;
	}
}