package com.example.demo.service;


import retrofit2.Call;
import retrofit2.http.GET;

import java.util.HashMap;

public interface RetroTestService {

    @GET("/api/v2/pokemon/ditto")
    Call<HashMap> testRes();

}
