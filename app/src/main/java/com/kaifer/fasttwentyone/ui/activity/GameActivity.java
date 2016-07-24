package com.kaifer.fasttwentyone.ui.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaifer.fasttwentyone.BuildConfig;
import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.API;
import com.kaifer.fasttwentyone.api.models.req.EndGameForm;
import com.kaifer.fasttwentyone.api.models.res.CreatedIdModelRes;
import com.kaifer.fasttwentyone.api.services.GameService;
import com.kaifer.fasttwentyone.game.Dealer;
import com.kaifer.fasttwentyone.game.GameStep;
import com.kaifer.fasttwentyone.utils.TokenManager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class GameActivity extends AppCompatActivity {

    private final Context context = this;

    private final Handler handler = new Handler();

    private final int timeAdjust = 100;

    @BindView(R.id.goalTextView) TextView goalTextView;

    @BindView(R.id.firstTextView) TextView firstTextView;
    @BindView(R.id.secondTextView) TextView secondTextView;

    @BindView(R.id.firstBtn) Button firstBtn;
    @BindView(R.id.secondBtn) Button secondBtn;
    @BindView(R.id.thirdBtn) Button thirdBtn;

    @BindView(R.id.stepTextView) TextView stepTextView;
    @BindView(R.id.scoreTextView) TextView scoreTextView;
    @BindView(R.id.lifeTextView) TextView lifeTextView;
    @BindView(R.id.timeTextView) TextView timeTextView;

    @BindView(R.id.countTextView) TextView countTextView;

    @BindView(R.id.buttonSet) LinearLayout buttonSet;
    @BindView(R.id.choiceSet) LinearLayout choiceSet;
    @BindView(R.id.countReadySet) LinearLayout countReadySet;

    private Dealer dealer;
    private Long gameId;
    private boolean isOver;

    private CountDownTimer timer;

    private int readyChecker;

    @OnClick(R.id.firstBtn)
    public void onClickFirstBtn() {
        onClickChoice(1);
    }

    @OnClick(R.id.secondBtn)
    public void onClickSecondBtn() {
        onClickChoice(2);
    }

    @OnClick(R.id.thirdBtn)
    public void onClickThirdBtn() {
        onClickChoice(3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        isOver = false;
        readyChecker = 2;

        choiceSet.setVisibility(LinearLayout.INVISIBLE);
        buttonSet.setVisibility(LinearLayout.INVISIBLE);

        dealer = new Dealer();
        dealer.generateGameStep();

        lifeTextView.setText(String.valueOf(dealer.getLife()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        GameService gameService = API.createService(GameService.class, TokenManager.getToken(context));

        Call<CreatedIdModelRes> call = gameService.start(new HashMap<String, String>());
        call.enqueue(new Callback<CreatedIdModelRes>() {
            @Override
            public void onResponse(Call<CreatedIdModelRes> call, Response<CreatedIdModelRes> response) {
                gameId = response.body().getId();
                Timber.d("Start game. GameId:%d", gameId);

                startGame();
            }

            @Override
            public void onFailure(Call<CreatedIdModelRes> call, Throwable t) {
                Toast.makeText(context, R.string.toast_fail_to_game_start, Toast.LENGTH_SHORT).show();

                Timber.d("Fail to start game. Cause:%s, Message:%s", t.getCause(), t.getMessage());
                t.printStackTrace();
            }
        });

        new CountDownTimer(4000 + timeAdjust, 1000) {
            public void onTick(long millisUntilFinished) {
                countTextView.setText(String.valueOf(millisUntilFinished / 1000 - 1));
            }

            public void onFinish() {
                startGame();
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (timer != null) {
            timer.cancel();
        }
    }

    private void startGame(){
        readyChecker -= 1;
        if(readyChecker <= 0){
            choiceSet.setVisibility(LinearLayout.VISIBLE);
            buttonSet.setVisibility(LinearLayout.VISIBLE);
            countReadySet.setVisibility(LinearLayout.INVISIBLE);
            setGameStep();
        }
    }

    private void onClickChoice(int inp) {
        firstBtn.setEnabled(false);
        secondBtn.setEnabled(false);
        thirdBtn.setEnabled(false);

        timer.cancel();

        int inpAnswer = dealer.getGameStep().getChoices()[inp - 1];

        if (dealer.checkAnswer(inpAnswer)) {
            nextStep();
        } else {
            onFail();
        }
    }

    private void onGameOver() {
        Timber.d("End game. GameId:%d", gameId);

        GameService gameService = API.createService(GameService.class, TokenManager.getToken(context));

        EndGameForm endGameForm = new EndGameForm(dealer.getScore(), dealer.getStep());
        Call<Void> call = gameService.end(gameId, endGameForm);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                });
                alert.setMessage("Total Score : " + dealer.getScore());

                AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.d("Fail to end game. Cause:" + t.getCause());
            }
        });
    }

    private void onFail() {
        int answer = dealer.getGameStep().getAnswer();
        int[] choices = dealer.getGameStep().getChoices();

        if (choices[0] == answer) firstBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else if (choices[1] == answer) secondBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else if (choices[2] == answer) thirdBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        if (!isOver) {
            boolean isNotOver = dealer.useLife();

            lifeTextView.setText(String.valueOf(dealer.getLife()));

            if (isNotOver) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextStep();
                    }
                }, 1000);
            } else {
                isOver = true;
                onGameOver();
            }
        }
    }

    private void nextStep() {
        dealer.nextStep();
        dealer.generateGameStep();

        setGameStep();
    }

    private void setGameStep(){
        GameStep gs = dealer.getGameStep();

        int[] opens = gs.getOpens();
        int[] choices = gs.getChoices();

        stepTextView.setText(dealer.getStep() + " " + getString(R.string.text_step));
        scoreTextView.setText(dealer.getScore() + " " + getString(R.string.text_unit_score));

        goalTextView.setText(String.valueOf(gs.getGoal()));

        firstTextView.setText(String.valueOf(opens[0]));
        secondTextView.setText(String.valueOf(opens[1]));

        firstBtn.setText(String.valueOf(choices[0]));
        secondBtn.setText(String.valueOf(choices[1]));
        thirdBtn.setText(String.valueOf(choices[2]));

        firstBtn.setEnabled(true);
        secondBtn.setEnabled(true);
        thirdBtn.setEnabled(true);
        firstBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        secondBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        thirdBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        Timber.d("Game timer created");
        timer = new CountDownTimer(1000 * (dealer.getTime() + 1) + timeAdjust, 1000) {
            public void onTick(long millisUntilFinished) {
                timeTextView.setText((millisUntilFinished / 1000) - 1+ " " + getString(R.string.text_unit_time));
            }

            public void onFinish() {
                onFail();
            }
        }.start();
    }
}

