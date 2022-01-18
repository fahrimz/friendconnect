package com.fahrimz.friendconnect.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailPostResponse{

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("id_post")
	private int idPost;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("body")
	private String body;

	@SerializedName("likes")
	private List<LikesItem> likes;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("username")
	private String username;

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
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

	public void setLikes(List<LikesItem> likes){
		this.likes = likes;
	}

	public List<LikesItem> getLikes(){
		return likes;
	}

	public String getUsername(){
		return username;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}
}