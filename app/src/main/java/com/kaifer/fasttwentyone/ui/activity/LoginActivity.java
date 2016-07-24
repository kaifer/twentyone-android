package com.kaifer.fasttwentyone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kaifer.fasttwentyone.BuildConfig;
import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.API;
import com.kaifer.fasttwentyone.api.models.req.AuthenticateForm;
import com.kaifer.fasttwentyone.api.models.req.InstallForm;
import com.kaifer.fasttwentyone.api.models.req.RegisterForm;
import com.kaifer.fasttwentyone.api.models.res.CreatedIdModelRes;
import com.kaifer.fasttwentyone.api.models.res.TokenRes;
import com.kaifer.fasttwentyone.api.services.AccountService;
import com.kaifer.fasttwentyone.utils.TokenManager;

import java.sql.Timestamp;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class LoginActivity extends AppCompatActivity {

    final private Context context = this;

    CallbackManager callbackManager;

    @BindView(R.id.login_button) LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.sp_21), Context.MODE_PRIVATE);
        boolean isFirst = sharedPref.getBoolean(getString(R.string.sp_is_first), true);

        if(isFirst) {
            Timber.d("First Run!! Call Install api.");

            String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            InstallForm installForm = new InstallForm(androidId);

            AccountService accountService = API.createService(AccountService.class);
            Call<CreatedIdModelRes> call = accountService.install(installForm);

            call.enqueue(new Callback<CreatedIdModelRes>() {
                @Override
                public void onResponse(Call<CreatedIdModelRes> call, Response<CreatedIdModelRes> response) {
                    Timber.d("Success call install api and call authenticate.");

                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.sp_21), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putBoolean(getString(R.string.sp_is_first), false);
                    editor.commit();

                    authenticate();
                }

                @Override
                public void onFailure(Call<CreatedIdModelRes> call, Throwable t) {
                    Timber.d("Fail to call install api.");
                }
            });

        } else {
            authenticate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        finish();
    }

    private void authenticate() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.sp_21), Context.MODE_PRIVATE);
        Timestamp expiredAt = new Timestamp(sharedPref.getLong(getString(R.string.sp_expired_at), 0));
        String token = sharedPref.getString(getString(R.string.sp_token), getString(R.string.sp_empty));

        Timber.d("Token Information. Token:%s, expiredAt:%d", token, expiredAt.getTime());

        if(!token.equals(getString(R.string.sp_empty)) && expiredAt.after(new Timestamp((new Date()).getTime()))) {
            Timber.d("TokenRes valid.");
            goToMain();
        } else {
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    AccessToken accessToken = loginResult.getAccessToken();

                    String token = accessToken.getToken();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    AuthenticateForm authenticateForm = new AuthenticateForm(token, androidId);

                    AccountService accountService = API.createService(AccountService.class);
                    Call<TokenRes> call = accountService.authenticate(getString(R.string.authenticate_facebook), authenticateForm);
                    call.enqueue(new Callback<TokenRes>() {
                        @Override
                        public void onResponse(Call<TokenRes> call, Response<TokenRes> response) {
                            TokenRes tokenRes = response.body();

                            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.sp_21), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            Timber.d("New token Information. Token:%s, expiredAt:%d", tokenRes.getToken(), tokenRes.getExpiredAt().getTime());

                            editor.putString(getString(R.string.sp_token), tokenRes.getToken());
                            editor.putLong(getString(R.string.sp_user_id), tokenRes.getUserId());
                            editor.putLong(getString(R.string.sp_expired_at), tokenRes.getExpiredAt().getTime());
                            editor.commit();

                            String regToken = sharedPref.getString(getString(R.string.sp_reg_token), "");
                            if (!regToken.equals("")) {
                                RegisterForm form = new RegisterForm(regToken);
                                AccountService accountService = API.createService(AccountService.class, TokenManager.getToken(context));
                                Call<Void> call2 = accountService.register(form);
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Timber.i("Success to call RegisterToken API.");
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Timber.i("Fail to call RegisterToken API.");
                                    }
                                });
                            }

                            goToMain();
                        }

                        @Override
                        public void onFailure(Call<TokenRes> call, Throwable t) {
                            Timber.d("accountService:onFailure " + t.getCause());
                        }
                    });
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        }
    }
}

