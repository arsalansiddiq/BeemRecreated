package com.example.arsalansiddiq.beem.interfaces;

import com.example.arsalansiddiq.beem.models.responsemodels.LoginResponse;

import retrofit2.Response;

public interface LoginInterface {

    void success(Response<LoginResponse> loginResponse);
    void failed(String error);

}
