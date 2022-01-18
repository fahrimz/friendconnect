package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class LikesItem{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_like")
	private int idLike;

	@SerializedName("id_user")
	private int idUser;

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
	}

	public void setIdLike(int idLike){
		this.idLike = idLike;
	}

	public int getIdLike(){
		return idLike;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}
}