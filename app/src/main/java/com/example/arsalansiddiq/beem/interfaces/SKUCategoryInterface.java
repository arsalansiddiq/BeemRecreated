package com.example.arsalansiddiq.beem.interfaces;

import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesObjectResponse;

import retrofit2.Response;

/**
 * Created by jellani on 8/19/2018.
 */

public interface SKUCategoryInterface {

    void success(Response<SalesObjectResponse> response);
    void failed(String error);

}
