package com.kaifer.fasttwentyone.gcm;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.API;
import com.kaifer.fasttwentyone.api.models.req.RegisterForm;
import com.kaifer.fasttwentyone.api.services.AccountService;
import com.kaifer.fasttwentyone.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 21..
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Timber.i("Register token: %s\n\t App token: %s", token, TokenManager.getToken(this));

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.sp_21), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.sp_reg_token), token);
        editor.commit();

        if (!TokenManager.getToken(this).equals(getString(R.string.sp_empty))) {
            RegisterForm form = new RegisterForm(token);
            AccountService accountService = API.createService(AccountService.class, TokenManager.getToken(this));
            Call<Void> call = accountService.register(form);
            call.enqueue(new Callback<Void>() {
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
    }
}
