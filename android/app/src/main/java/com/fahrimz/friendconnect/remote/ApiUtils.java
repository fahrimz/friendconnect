package com.fahrimz.friendconnect.remote;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.1.4:5000/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static PostService getPostService() {
        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }
}
