package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.chanhbc.ailatrieuphu.R;
import com.chanhbc.ailatrieuphu.model.Rank;

import java.util.ArrayList;

public class DialogRank extends Dialog {
    private TextView tvSTT;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvMoney;
    private ArrayList<Rank> ranks;

    public DialogRank(Context context, ArrayList<Rank> ranks) {
        super(context);
        setContentView(R.layout.dialog_rank);
        this.ranks = ranks;
        initializeComponents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("BẢNG XẾP HẠNG");
    }

    private void initializeComponents() {
        tvSTT = (TextView) findViewById(R.id.tv_stt);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvNumber = (TextView) findViewById(R.id.tv_question_number);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        for (int i = 0; i < ranks.size(); i++) {
            if (i < 10) {
                Rank rank = ranks.get(i);
                tvSTT.setText(tvSTT.getText().toString() + "\n" + (i + 1));
                tvName.setText(tvName.getText().toString() + "\n" + rank.getName());
                tvNumber.setText(tvNumber.getText().toString() + "\n" + rank.getQuestion());
                tvMoney.setText(tvMoney.getText().toString() + "\n" + rank.getMoney());
            }
        }
    }


}
