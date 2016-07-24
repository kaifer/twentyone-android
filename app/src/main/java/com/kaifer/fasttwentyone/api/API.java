package com.kaifer.fasttwentyone.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class API {
    public static final String API_URL = "http://52.78.99.188:9000/v/";

    private static Retrofit.Builder builder() {
        return new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create());
    }

    public static <S> S createService(Class<S> serviceClass){
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String token){
        Interceptor interceptor;

        if(token != null){
            interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                }
            };
        }
        else {
            interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .build();
                    return chain.proceed(request);
                }
            };
        }

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return builder().client(httpClient).build().create(serviceClass);
    }
}
