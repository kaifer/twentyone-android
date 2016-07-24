package com.kaifer.fasttwentyone.api.services;

import com.kaifer.fasttwentyone.api.models.req.AuthenticateForm;
import com.kaifer.fasttwentyone.api.models.res.ScoreRes;
import com.kaifer.fasttwentyone.api.models.res.TokenRes;
import com.kaifer.fasttwentyone.api.models.res.UserRes;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public interface FriendService {
    @GET("users/{id}/friends")
    Call<List<UserRes>> list(@Path("id") Long id);

    @POST("friends/{id}/add")
    Call<Void> add(@Path("id") Long id);

    @DELETE("friends/{id}/remove")
    Call<Void> remove(@Path("id") Long id);

    @GET("users/{id}/friends_ranking")
    Call<List<ScoreRes>> ranking(@Path("id") Long id);
}
