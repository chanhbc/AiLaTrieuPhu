package com.chanhbc.ailatrieuphu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanhbc.ailatrieuphu.App;
import com.chanhbc.ailatrieuphu.R;
import com.chanhbc.ailatrieuphu.adapter.DialogRank;
import com.chanhbc.ailatrieuphu.model.Rank;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameoverActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvStopQuestion;
    private TextView tvStopMoney;
    private TextView tvStopName;
    private Button btnLoginZalo;
    private ImageView ivBack;
    private ImageView ivRank;
    private String money;
    private String name;
    private String questionNumber;
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gameover);
        Intent intent = getIntent();
        money = intent.getStringExtra("MONEY");
        name = intent.getStringExtra("NAME");
        questionNumber = intent.getStringExtra("Q_NUMBER");
        addUser();
        initializeComponents();
    }

    private void addUser() {
        sp = (App.getContext()).getSharedPreferences("Rank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String q = sp.getString(name, "error");
        if (q != "error") {
            q = q.split("_")[0];
            if (Integer.parseInt(q) < Integer.parseInt(questionNumber)) {
                editor.remove(name);
                editor.commit();
                editor.putString(name, questionNumber + "_" + money);
                editor.apply();
            }
        } else {
            editor.putString(name, questionNumber + "_" + money);
            editor.apply();
        }
    }

    private void initializeComponents() {
        tvStopMoney = (TextView) findViewById(R.id.tv_stop_money);
        tvStopQuestion = (TextView) findViewById(R.id.tv_stop_question);
        tvStopName = (TextView) findViewById(R.id.tv_name);
        tvStopName.setText(name);
        tvStopMoney.setText(money + " VND");
        tvStopQuestion.setText("BẠN ĐÃ CÓ DỪNG CUỘC CHƠI Ở CÂU HỎI SỐ " + questionNumber);
        btnLoginZalo = (Button) findViewById(R.id.btn_zalo_login);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivRank = (ImageView) findViewById(R.id.iv_rank);
        btnLoginZalo.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivRank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zalo_login:
                Toast.makeText(this, "Chức năng đang xây dựng :))", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_back:
                Intent intent = new Intent(GameoverActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_rank:
                DialogRank dialogRank = new DialogRank(this, getRanks());
                dialogRank.show();
                break;
            default:
                break;
        }
    }

    private ArrayList<Rank> getRanks() {
        ArrayList<Rank> ranks = new ArrayList<>();
        sp = (App.getContext()).getSharedPreferences("Rank", Context.MODE_PRIVATE);
        for (String key1 : sp.getAll().keySet()) {
            String p = sp.getString(key1, "Error!");
            String q = p.split("_")[0];
            String m = p.split("_")[1];
            ranks.add(new Rank(key1, q, m));
        }
        Collections.sort(ranks, new Comparator<Rank>() {
            @Override
            public int compare(Rank rank1, Rank rank2) {
                return rank2.getQuestion().compareTo(rank1.getQuestion());
            }
        });
        return ranks;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameoverActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
