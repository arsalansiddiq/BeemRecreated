package com.example.arsalansiddiq.beem.interfaces;

import com.example.arsalansiddiq.beem.models.responsemodels.AttandanceResponse;

import retrofit2.Response;

public interface AttandanceInterface {

    void success(Response<AttandanceResponse> attandanceResponseResponse);
    void failed(String error);
}
