package com.example.arsalansiddiq.beem.interfaces;

import com.example.arsalansiddiq.beem.models.responsemodels.LoginResponse;

import retrofit2.Response;

public interface SampleInterface {

    void success(LoginResponse loginResponse);
    void failed(String error);

}
