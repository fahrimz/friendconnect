package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("bio")
	private String bio;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("invite_code")
	private String inviteCode;

	@SerializedName("accessToken")
	private String accessToken;

	@SerializedName("username")
	private String username;

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public void setBio(String bio){
		this.bio = bio;
	}

	public String getBio(){
		return bio;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setInviteCode(String inviteCode){
		this.inviteCode = inviteCode;
	}

	public String getInviteCode(){
		return inviteCode;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}