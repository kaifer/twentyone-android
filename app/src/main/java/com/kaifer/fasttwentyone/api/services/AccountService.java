package com.kaifer.fasttwentyone.api.services;

import com.kaifer.fasttwentyone.api.models.req.AuthenticateForm;
import com.kaifer.fasttwentyone.api.models.req.InstallForm;
import com.kaifer.fasttwentyone.api.models.req.RegisterForm;
import com.kaifer.fasttwentyone.api.models.res.CreatedIdModelRes;
import com.kaifer.fasttwentyone.api.models.res.TokenRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public interface AccountService {
    @POST("authenticate/{provider}")
    Call<TokenRes> authenticate(@Path("provider") String provider, @Body AuthenticateForm authenticateForm);

    @POST("install")
    Call<CreatedIdModelRes> install(@Body InstallForm installForm);

    @PUT("register")
    Call<Void> register(@Body RegisterForm registerForm);
}
