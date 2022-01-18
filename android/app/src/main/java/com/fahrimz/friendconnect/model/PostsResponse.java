package com.fahrimz.friendconnect.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostsResponse{

	@SerializedName("posts")
	private List<PostsItem> posts;

	public void setPosts(List<PostsItem> posts){
		this.posts = posts;
	}

	public List<PostsItem> getPosts(){
		return posts;
	}
}