package com.kaifer.fasttwentyone.api.services;

import com.kaifer.fasttwentyone.api.models.req.AuthenticateForm;
import com.kaifer.fasttwentyone.api.models.res.CreatedIdModelRes;
import com.kaifer.fasttwentyone.api.models.res.ScoreRes;
import com.kaifer.fasttwentyone.api.models.res.TokenRes;
import com.kaifer.fasttwentyone.api.models.res.UserRes;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public interface UserService {
    @PUT("users/{id}")
    Call<Void> connect(@Path("id") Long id);

    @PUT("users/{id}")
    Call<Void> exit(@Path("id") Long id);

    @GET("users/{id}/score")
    Call<ScoreRes> getScore(@Path("id") Long id);

    @GET("users/{keyword}/search")
    Call<List<UserRes>> search(@Path("id") Long id);
}
