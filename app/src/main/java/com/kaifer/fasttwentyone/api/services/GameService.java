package com.kaifer.fasttwentyone.api.services;

import com.kaifer.fasttwentyone.api.models.req.AuthenticateForm;
import com.kaifer.fasttwentyone.api.models.req.EndGameForm;
import com.kaifer.fasttwentyone.api.models.res.CreatedIdModelRes;
import com.kaifer.fasttwentyone.api.models.res.RankingRes;
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
public interface GameService {
    @POST("games")
    Call<CreatedIdModelRes> start(@Body HashMap<String, String> params);

    @PUT("games/{id}/end")
    Call<Void> end(@Path("id") Long id, @Body EndGameForm endGameForm);

    @GET("games/rankings")
    Call<RankingRes> ranking();
}
