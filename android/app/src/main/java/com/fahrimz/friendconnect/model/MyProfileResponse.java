package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class MyProfileResponse{

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("bio")
	private String bio;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("invite_code")
	private String inviteCode;

	@SerializedName("posts")
	private String posts;

	@SerializedName("friends")
	private String friends;

	@SerializedName("username")
	private String username;

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getBio(){
		return bio;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getInviteCode(){
		return inviteCode;
	}

	public String getPosts(){
		return posts;
	}

	public String getFriends(){
		return friends;
	}

	public String getUsername(){
		return username;
	}
}