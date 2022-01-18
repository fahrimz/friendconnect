package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class AddFriendRequest{

	@SerializedName("invite_code")
	private String inviteCode;

	public AddFriendRequest(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getInviteCode(){
		return inviteCode;
	}
}