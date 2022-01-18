package com.fahrimz.friendconnect.model;

import com.google.gson.annotations.SerializedName;

public class PostsItem{

	@SerializedName("comments")
	private int comments;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	@SerializedName("username")
	private String username;

	@SerializedName("likes")
	private int likes;

	@SerializedName("created_at")
	private String createdAt;

	public PostsItem(String username, String body, String createdAt, int idPost, int idUser, String avatarUrl, int likes, int comments) {
		this.comments = comments;
		this.avatarUrl = avatarUrl;
		this.idPost = idPost;
		this.idUser = idUser;
		this.body = body;
		this.username = username;
		this.likes = likes;
		this.createdAt = createdAt;
	}

	public void setComments(int comments){
		this.comments = comments;
	}

	public int getComments(){
		return comments;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
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

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setLikes(int likes){
		this.likes = likes;
	}

	public int getLikes(){
		return likes;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
