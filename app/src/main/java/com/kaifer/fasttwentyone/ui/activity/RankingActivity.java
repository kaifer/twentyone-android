package com.kaifer.fasttwentyone.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.API;
import com.kaifer.fasttwentyone.api.models.res.RankingRes;
import com.kaifer.fasttwentyone.api.models.res.ScoreRes;
import com.kaifer.fasttwentyone.api.services.GameService;
import com.kaifer.fasttwentyone.ui.adapter.RankingAdapter;
import com.kaifer.fasttwentyone.utils.TokenManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class RankingActivity extends AppCompatActivity {

    private final Context context = this;

    List<ScoreRes> ranking;

    @BindView(R.id.rankingListView) ListView rankingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_ranking);

        GameService gameService = API.createService(GameService.class, TokenManager.getToken(context));
        Call<RankingRes> call = gameService.ranking();
        call.enqueue(new Callback<RankingRes>() {
            @Override
            public void onResponse(Call<RankingRes> call, Response<RankingRes> response) {
                Timber.d("Success to get game ranking");

                ranking = response.body().getData();

                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                RankingAdapter adapter = new RankingAdapter(inflater, ranking);

                rankingListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RankingRes> call, Throwable t) {
                Timber.d("Fail to get game ranking. Cause:" + t.getCause());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

