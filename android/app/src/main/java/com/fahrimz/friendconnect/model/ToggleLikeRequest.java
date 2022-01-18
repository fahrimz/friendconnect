package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class ToggleLikeRequest{

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_user")
	private int idUser;

	public ToggleLikeRequest(int idPost, int idUser) {
		this.idPost = idPost;
		this.idUser = idUser;
	}

	public int getIdPost(){
		return idPost;
	}

	public int getIdUser(){
		return idUser;
	}
}