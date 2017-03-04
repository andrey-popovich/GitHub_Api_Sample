package com.andrey.github_api_sample.endpoint;

import com.andrey.github_api_sample.data.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {

    @GET("users")
    Observable<List<User>> getUsersList();

    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String userName);
}
