package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse{

	@SerializedName("error")
	private String error;

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}
}