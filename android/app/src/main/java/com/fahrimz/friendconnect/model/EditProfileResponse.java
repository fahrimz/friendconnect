package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class EditProfileResponse{

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}