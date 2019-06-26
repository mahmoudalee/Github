package com.example.github.network;

import com.example.github.Models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPoint {

    @GET("users")
    Call<Users> Users(@Query("q")String q1 , @Query("page")int q2);
}
