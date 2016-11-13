package com.chanhbc.ailatrieuphu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chanhbc.ailatrieuphu.App;
import com.chanhbc.ailatrieuphu.R;
import com.chanhbc.ailatrieuphu.adapter.DialogName;
import com.chanhbc.ailatrieuphu.adapter.DialogRank;
import com.chanhbc.ailatrieuphu.model.Rank;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogName.OnReceiveDataListener {
    private ImageView ivGameCenter;
    private LinearLayout llHomeCenter;
    private Animation animBGGameCenter;
    private Animation animBGHomeCenter;
    private ImageView ivHome;
    private Button btnTrialLogin;
    private Button btnZaloLogin;
    private DialogName dialogName;
    private ImageView ivRank;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getIntent();
        initializeComponents();
    }

    private void initializeComponents() {
        ivGameCenter = (ImageView) findViewById(R.id.ivGameCenter);
        llHomeCenter = (LinearLayout) findViewById(R.id.llHomeCenter);
        ivHome = (ImageView) findViewById(R.id.ivHome);
        llHomeCenter.setVisibility(View.GONE);
        ivRank = (ImageView) findViewById(R.id.iv_rank);
        ivRank.setOnClickListener(this);
        loadAnimation();
        ivGameCenter.setAnimation(animBGGameCenter);
        btnTrialLogin = (Button) findViewById(R.id.btn_trial_login);
        btnZaloLogin = (Button) findViewById(R.id.btn_zalo_login);
        btnTrialLogin.setOnClickListener(this);
        btnZaloLogin.setOnClickListener(this);
    }

    private void loadAnimation() {
        animBGGameCenter = AnimationUtils.loadAnimation(this, R.anim.anim_bg_game_center);
        animBGHomeCenter = AnimationUtils.loadAnimation(this, R.anim.anim_bg_home_center);
        animBGGameCenter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivGameCenter.setVisibility(View.GONE);
                llHomeCenter.setVisibility(View.VISIBLE);
                ivHome.setAnimation(animBGHomeCenter);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trial_login:
                dialogName = new DialogName(this);
                dialogName.show();
                dialogName.setOnReceiveDataListener(this);
                break;

            case R.id.btn_zalo_login:
                Toast.makeText(this, "Chức năng đang xây dựng :))", Toast.LENGTH_SHORT).show();
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
    public void onReceiveDataName(String receiveData) {
        switch (receiveData) {
            case DialogName.BTN_CANCEL:
                dialogName.dismiss();
                break;
            case DialogName.BTN_OK:
                String name = dialogName.getEtName().getText().toString();
                dialogName.dismiss();
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("Name", name);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
