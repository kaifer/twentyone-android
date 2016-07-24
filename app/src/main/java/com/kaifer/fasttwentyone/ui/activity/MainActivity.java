package com.kaifer.fasttwentyone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.API;
import com.kaifer.fasttwentyone.api.models.res.ScoreRes;
import com.kaifer.fasttwentyone.api.services.UserService;
import com.kaifer.fasttwentyone.utils.TokenManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private final Context context = this;

    @BindView(R.id.topScoreTextView) TextView topScoreTextView;
    @BindView(R.id.topStepTextView) TextView topStepTextView;

    @OnClick(R.id.game_start_btn)
    public void startGame() {
        Intent intentSubActivity = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intentSubActivity);
    }

    @OnClick(R.id.ranking_btn)
    public void showRanking() {
        Intent intentSubActivity = new Intent(MainActivity.this, RankingActivity.class);
        startActivity(intentSubActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UserService userService = API.createService(UserService.class, TokenManager.getToken(context));
        Call<ScoreRes> call = userService.getScore(TokenManager.getUserId(context));

        call.enqueue(new Callback<ScoreRes>() {
            @Override
            public void onResponse(Call<ScoreRes> call, Response<ScoreRes> response) {
                Timber.d("Success call getScore api.");
                Long score = response.body().getScore();
                Long step = response.body().getStep();

                topScoreTextView.setText(String.valueOf(score));
                topStepTextView.setText(String.valueOf(step));
            }

            @Override
            public void onFailure(Call<ScoreRes> call, Throwable t) {
                Timber.d("Fail to call getScore api.");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Timber.d("Reg Token %s", TokenManager.getRegToken(context));
    }
}
