package com.kaifer.fasttwentyone.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaifer.fasttwentyone.R;
import com.kaifer.fasttwentyone.api.models.res.ScoreRes;

import java.util.List;

import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 21..
 */
public class RankingAdapter extends BaseAdapter {

    public List<ScoreRes> list;
    LayoutInflater inflater;

    public RankingAdapter(LayoutInflater inflater, List<ScoreRes> list) {
        super();
        this.inflater = inflater;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getUserId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.list_item_ranking, null);
            holder = new ViewHolder();
            holder.rankTxt = (TextView) view.findViewById(R.id.ranking_rank);
            holder.nameTxt = (TextView) view.findViewById(R.id.ranking_name);
            holder.stepTxt = (TextView) view.findViewById(R.id.ranking_step);
            holder.scoreTxt = (TextView) view.findViewById(R.id.ranking_score);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        ScoreRes map = list.get(i);
        holder.rankTxt.setText(i + 1 + "");
        holder.nameTxt.setText(map.getUserName());
        holder.stepTxt.setText(map.getStep() + "");
        holder.scoreTxt.setText(map.getScore() + "");

        return view;
    }

    private class ViewHolder {
        TextView rankTxt;
        TextView nameTxt;
        TextView stepTxt;
        TextView scoreTxt;
    }
}
