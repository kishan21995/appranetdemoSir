package com.e.testdemo.retrofit;


import com.e.testdemo.models.PostResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/typicode/demo/db")
    Call<PostResponse>getTest();


}
