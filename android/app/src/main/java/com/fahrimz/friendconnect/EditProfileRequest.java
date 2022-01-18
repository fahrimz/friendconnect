package com.fahrimz.friendconnect;

import com.google.gson.annotations.SerializedName;

public class EditProfileRequest{

	@SerializedName("bio")
	private String bio;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("invite_code")
	private String inviteCode;

	public EditProfileRequest(int idUser, String bio, String inviteCode) {
		this.bio = bio;
		this.idUser = idUser;
		this.inviteCode = inviteCode;
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
}