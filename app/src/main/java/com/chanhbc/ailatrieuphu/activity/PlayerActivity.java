package com.chanhbc.ailatrieuphu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanhbc.ailatrieuphu.R;
import com.chanhbc.ailatrieuphu.adapter.DialogAudience;
import com.chanhbc.ailatrieuphu.adapter.DialogCFStop;
import com.chanhbc.ailatrieuphu.adapter.DialogCall;
import com.chanhbc.ailatrieuphu.adapter.DialogConfirm;
import com.chanhbc.ailatrieuphu.adapter.DialogOvertime;
import com.chanhbc.ailatrieuphu.manager.DatabaseManager;
import com.chanhbc.ailatrieuphu.model.Question;

import java.util.Random;

import at.grabner.circleprogress.CircleProgressView;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, DialogConfirm.OnReceiveDataListener, DialogOvertime.OnReceiveDataListener, DialogCFStop.OnReceiveDataListener, DialogAudience.OnReceiveDataListener, DialogCall.OnReceiveDataListener {
    private static final int UPDATE_TEXT_TIME = 0;
    private static final int MSG_GAME_OVER = 1;
    private static final int MSG_CHECK_ANSWER = 2;
    private static final int START_GAME = 3;
    private static final int NEXT_QUESTION = 4;
    private static final int UPDATE_TEXT_VIEW_TRUE = 5;
    private static final int LOAD_QUESTION = 6;
    private static final int UPDATE_TEXT_VIEW_ANSWER = 7;
    private static final int UPDATE_TEXT_VIEW_ANSWER2 = 8;
    private static final int MSG_GAME_OVER2 = 9;
    private TextView tvCancel;
    private DrawerLayout dlActivityPlayer;
    private LinearLayout llPlayerActivity;
    private Animation anim_demo;
    private Animation anim_demo_1;
    //    private TextView tvTime;
    private TextView tvMoney;
    private ImageView ivAvatar;
    private RelativeLayout rlNav;
    private TextView tvQ1;
    private TextView tvQ2;
    private TextView tvQ3;
    private TextView tvQ4;
    private TextView tvQ5;
    private TextView tvQ6;
    private TextView tvQ7;
    private TextView tvQ8;
    private TextView tvQ9;
    private TextView tvQ10;
    private TextView tvQ11;
    private TextView tvQ12;
    private TextView tvQ13;
    private TextView tvQ14;
    private TextView tvQ15;
    private TextView[] tvQ;
    private Button btnHelpStop;
    private Button btnHelpCall;
    private Button btnHelpAudience;
    private Button btnHelp5050;
    private Button btnHelpChangeQuestion;
    private TextView tvQuestionNumber;
    private TextView tvQuestionContent;
    private TextView tvAnswerA;
    private TextView tvAnswerB;
    private TextView tvAnswerC;
    private TextView tvAnswerD;
    private int trueCase;
    private int questionNumber;
    private DialogConfirm dialogConfirm;
    private DialogOvertime dialogOvertime;
    private DialogCFStop dialogCFStop;
    private DialogAudience dialogAudience;
    private DialogCall dialogCall;
    private LinearLayout llPlayGameCenter;
    private DatabaseManager databaseManager;
    private String[] money;
    private int time;
    private int timeCheckAnswer = 15;
    private int timeStartGame = 2;
    private int timeNextQuestion = 15;
    private int timeNewQuestion = 2;
    private int timeCheckAnswer2 = 15;
    private Handler handler;
    private boolean isChooseAnswer;
    private int isTrue = 0;
    private int isTrue1 = 0;
    private int isTrue2 = 0;
    private boolean is5050 = true;
    private boolean isCall = true;
    private boolean isChange = true;
    private boolean isAudience = true;
    private Thread threadTime;
    private CircleProgressView cpv;
    private String name;
    private TextView tvName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        name = getIntent().getStringExtra("Name");
        if (name == null || name.length() == 0)
            name = "Player";
        initializeComponents();
    }

    private void initializeComponents() {
        isChooseAnswer = false;
        databaseManager = new DatabaseManager(this);
        questionNumber = 1;
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(name);
        rlNav = (RelativeLayout) findViewById(R.id.rl_nav);
//        tvTime = (TextView) findViewById(R.id.tv_time);
        cpv = (CircleProgressView) findViewById(R.id.cpv);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        tvMoney.setVisibility(View.GONE);
//        tvTime.setVisibility(View.GONE);
        cpv.setVisibility(View.GONE);
        tvQ1 = (TextView) findViewById(R.id.tv_q1);
        tvQ2 = (TextView) findViewById(R.id.tv_q2);
        tvQ3 = (TextView) findViewById(R.id.tv_q3);
        tvQ4 = (TextView) findViewById(R.id.tv_q4);
        tvQ5 = (TextView) findViewById(R.id.tv_q5);
        tvQ6 = (TextView) findViewById(R.id.tv_q6);
        tvQ7 = (TextView) findViewById(R.id.tv_q7);
        tvQ8 = (TextView) findViewById(R.id.tv_q8);
        tvQ9 = (TextView) findViewById(R.id.tv_q9);
        tvQ10 = (TextView) findViewById(R.id.tv_q10);
        tvQ11 = (TextView) findViewById(R.id.tv_q11);
        tvQ12 = (TextView) findViewById(R.id.tv_q12);
        tvQ13 = (TextView) findViewById(R.id.tv_q13);
        tvQ14 = (TextView) findViewById(R.id.tv_q14);
        tvQ15 = (TextView) findViewById(R.id.tv_q15);
        btnHelp5050 = (Button) findViewById(R.id.btn_help_5050);
        btnHelpStop = (Button) findViewById(R.id.btn_help_stop);
        btnHelpAudience = (Button) findViewById(R.id.btn_help_audience);
        btnHelpCall = (Button) findViewById(R.id.btn_help_call);
        btnHelpChangeQuestion = (Button) findViewById(R.id.btn_help_change_question);
        btnHelpAudience.setOnClickListener(this);
        btnHelpCall.setOnClickListener(this);
        btnHelpChangeQuestion.setOnClickListener(this);
        btnHelp5050.setOnClickListener(this);
        btnHelpStop.setOnClickListener(this);
        tvQ = new TextView[]{tvQ1, tvQ1, tvQ2, tvQ3, tvQ4, tvQ5, tvQ6, tvQ7, tvQ8, tvQ9, tvQ10, tvQ11, tvQ12, tvQ13, tvQ14, tvQ15, tvQ15};
        llPlayGameCenter = (LinearLayout) findViewById(R.id.ll_play_game_center);
        tvQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        tvQuestionContent = (TextView) findViewById(R.id.tv_question_content);
        tvAnswerA = (TextView) findViewById(R.id.tv_answer_a);
        tvAnswerB = (TextView) findViewById(R.id.tv_answer_b);
        tvAnswerC = (TextView) findViewById(R.id.tv_answer_c);
        tvAnswerD = (TextView) findViewById(R.id.tv_answer_d);
        tvAnswerA.setOnClickListener(this);
        tvAnswerB.setOnClickListener(this);
        tvAnswerC.setOnClickListener(this);
        tvAnswerD.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        llPlayGameCenter.setVisibility(View.GONE);
        money = new String[]{"0", "200.000", "400.000", "600.000", "1.000.000", "2.000.000"
                , "3.000.000", "6.000.000", "10.000.000", "14.000.000", "22.000.000"
                , "30.000.000", "40.000.000", "60.000.000", "85.000.000", "150.000.000"};
        dlActivityPlayer = (DrawerLayout) findViewById(R.id.dl_activity_player);
        dlActivityPlayer.setOnClickListener(this);
        llPlayerActivity = (LinearLayout) findViewById(R.id.ll_player_activity);
        llPlayerActivity.setOnClickListener(this);
        loadAnimations();
        myHandler();
    }

    private void myHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT_TIME:
                        cpv.setText(msg.arg1 + "");
                        cpv.setValue(30 - msg.arg1);
                        break;

                    case UPDATE_TEXT_VIEW_TRUE:
                        if (isTrue++ % 2 == 0) {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                default:
                                    break;
                            }
                        } else {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;

                    case UPDATE_TEXT_VIEW_ANSWER:
                        if (isTrue1++ % 2 == 1) {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                default:
                                    break;
                            }
                        } else {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;

                    case UPDATE_TEXT_VIEW_ANSWER2:
                        if (isTrue2++ % 2 == 1) {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                                    break;

                                default:
                                    break;
                            }
                        } else {
                            switch (msg.arg2) {
                                case 1:
                                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 2:
                                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 3:
                                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                case 4:
                                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;

                    case MSG_GAME_OVER:
                        dialogOvertime = new DialogOvertime(PlayerActivity.this);
                        dialogOvertime.show();
                        dialogOvertime.setOnReceiveDataListener(PlayerActivity.this);
                        break;

                    case MSG_GAME_OVER2:
                        Intent intent2 = new Intent();
                        if (questionNumber > 11) {
                            questionNumber = 11;
                        } else {
                            if (questionNumber > 6) {
                                questionNumber = 6;
                            } else {
                                questionNumber = 1;
                            }
                        }
                        intent2.setClass(PlayerActivity.this, GameoverActivity.class);
                        intent2.putExtra("MONEY", money[questionNumber - 1]);
                        intent2.putExtra("NAME", name);
                        intent2.putExtra("Q_NUMBER", "" + (questionNumber - 1));
                        startActivity(intent2);
                        break;

                    case MSG_CHECK_ANSWER:
                        int i = msg.arg2;
                        checkAnswer(i);
                        break;

                    case START_GAME:
                        dlActivityPlayer.closeDrawers();
                        loadQuestion(questionNumber);
//                        tvTime.setVisibility(View.VISIBLE);
                        cpv.setVisibility(View.VISIBLE);
                        tvMoney.setVisibility(View.VISIBLE);
                        llPlayGameCenter.setVisibility(View.VISIBLE);
                        break;

                    case NEXT_QUESTION:
                        if (questionNumber == 16) {
                            Intent intent = new Intent();
                            intent.setClass(PlayerActivity.this, GameoverActivity.class);
                            intent.putExtra("MONEY", money[questionNumber - 1]);
                            intent.putExtra("Q_NUMBER", "" + (questionNumber - 1));
                            startActivity(intent);
                            return;
                        }
                        dlActivityPlayer.openDrawer(GravityCompat.START);
                        tvQ[questionNumber - 1].setBackgroundResource(0);
                        tvQ[questionNumber].setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    timeNewQuestion--;
                                    if (timeNewQuestion == 0) {
                                        Message message = new Message();
                                        message.what = LOAD_QUESTION;
                                        handler.sendMessage(message);
                                        return;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        thread.start();
                        break;

                    case LOAD_QUESTION:
                        dlActivityPlayer.closeDrawers();
                        loadQuestion(questionNumber);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    private void checkAnswer(final int i) {
        if (trueCase == i) {
            tvMoney.setText(money[questionNumber]);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        timeNextQuestion--;
                        if (timeNextQuestion == 0) {
                            Message msg = new Message();
                            msg.what = NEXT_QUESTION;
                            msg.arg1 = questionNumber++;
                            handler.sendMessage(msg);
                            return;
                        }
                        Message msg1 = new Message();
                        msg1.what = UPDATE_TEXT_VIEW_TRUE;
                        msg1.arg2 = i;
                        handler.sendMessage(msg1);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } else { //wrong
            switch (i) {
                case 1:
                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    break;

                case 2:
                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    break;

                case 3:
                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    break;

                case 4:
                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    break;

                default:
                    break;
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        timeCheckAnswer2--;
                        if (timeCheckAnswer2 == 0) {
                            Message msg = new Message();
                            msg.what = MSG_GAME_OVER2;
                            handler.sendMessage(msg);
                            return;
                        }
                        Message msg1 = new Message();
                        msg1.what = UPDATE_TEXT_VIEW_ANSWER2;
                        msg1.arg2 = trueCase;
                        handler.sendMessage(msg1);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
    }

    private void loadAnimations() {
        anim_demo = AnimationUtils.loadAnimation(this, R.anim.anim_demo);
        ivAvatar.setAnimation(anim_demo);
        anim_demo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                dlActivityPlayer.openDrawer(GravityCompat.START);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dlActivityPlayer.closeDrawers();
                loadDialogConfirm();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void loadDialogConfirm() {
        if (dialogConfirm == null) {
            dialogConfirm = new DialogConfirm(this);
            dialogConfirm.show();
            dialogConfirm.setOnReceiveDataListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dlActivityPlayer.closeDrawers();
                loadDialogConfirm();
                break;

            case R.id.tv_answer_a:
                if (isChooseAnswer == false) {
                    tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                    isChooseAnswer = true;
                    timeCheckAnswer(1);
                    isEnableBTN(false);
                }
                break;

            case R.id.tv_answer_b:
                if (isChooseAnswer == false) {
                    tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                    isChooseAnswer = true;
                    timeCheckAnswer(2);
                    isEnableBTN(false);
                }
                break;

            case R.id.tv_answer_c:
                if (isChooseAnswer == false) {
                    tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                    isChooseAnswer = true;
                    timeCheckAnswer(3);
                    isEnableBTN(false);
                }
                break;

            case R.id.tv_answer_d:
                if (isChooseAnswer == false) {
                    tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
                    isChooseAnswer = true;
                    timeCheckAnswer(4);
                    isEnableBTN(false);
                }
                break;

            case R.id.dl_activity_player:
                break;

            case R.id.ll_player_activity:
                dlActivityPlayer.closeDrawers();
                break;

            case R.id.btn_help_stop:
                dialogCFStop = new DialogCFStop(this);
                dialogCFStop.show();
                dialogCFStop.setOnReceiveDataListener(this);
                break;

            case R.id.btn_help_change_question:
                loadQuestion(questionNumber);
                btnHelpChangeQuestion.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_change_question_x);
                btnHelpChangeQuestion.setEnabled(false);
                isChange = false;
                break;

            case R.id.btn_help_5050:
                is5050 = false;
                btnHelp5050.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050_x);
                btnHelp5050.setEnabled(false);
                Random random = new Random();
                int a = random.nextInt(4) + 1;
                int b = random.nextInt(4) + 1;
                while (a == trueCase) {
                    a = random.nextInt(4) + 1;
                }
                while (b == trueCase || b == a) {
                    b = random.nextInt(4) + 1;
                }
                switch (a) {
                    case 1:
                        tvAnswerA.setText("");
                        tvAnswerA.setEnabled(false);
                        break;

                    case 2:
                        tvAnswerB.setText("");
                        tvAnswerB.setEnabled(false);
                        break;

                    case 3:
                        tvAnswerC.setText("");
                        tvAnswerC.setEnabled(false);
                        break;

                    case 4:
                        tvAnswerD.setText("");
                        tvAnswerD.setEnabled(false);
                        break;

                    default:
                        break;
                }
                switch (b) {
                    case 1:
                        tvAnswerA.setText("");
                        tvAnswerA.setEnabled(false);
                        break;

                    case 2:
                        tvAnswerB.setText("");
                        tvAnswerB.setEnabled(false);
                        break;

                    case 3:
                        tvAnswerC.setText("");
                        tvAnswerC.setEnabled(false);
                        break;

                    case 4:
                        tvAnswerD.setText("");
                        tvAnswerD.setEnabled(false);
                        break;

                    default:
                        break;
                }
                break;

            case R.id.btn_help_audience:
                isChooseAnswer = true;
                btnHelpAudience.setEnabled(false);
                btnHelpAudience.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience_x);
                dialogAudience = new DialogAudience(this);
                dialogAudience.show();
                dialogAudience.setOnReceiveDataListener(this);
                Random random1 = new Random();
                int answer1 = random1.nextInt(81) + 20;
                int answer2 = 0;
                int answer3 = 0;
                int answer4 = 0;
                if (100 - answer1 > 0) {
                    answer2 = random1.nextInt(100 - answer1);
                }
                if (100 - answer1 - answer2 > 0) {
                    answer3 = random1.nextInt(100 - answer1 - answer2);
                }
                if (100 - answer1 - answer2 - answer3 > 0) {
                    answer4 = 100 - answer1 - answer2 - answer3;
                }
                switch (trueCase) {
                    case 1:
                        dialogAudience.getTvA().setText("A: " + answer1 + "%");
                        break;

                    case 2:
                        dialogAudience.getTvB().setText("B: " + answer1 + "%");
                        break;

                    case 3:
                        dialogAudience.getTvC().setText("C: " + answer1 + "%");
                        break;

                    case 4:
                        dialogAudience.getTvD().setText("D: " + answer1 + "%");
                        break;

                    default:
                        break;
                }
                int s = random1.nextInt(4) + 1;
                int s2 = random1.nextInt(4) + 1;
                int s3 = random1.nextInt(4) + 1;
                while (s == trueCase) {
                    s = random1.nextInt(4) + 1;
                }
                while (s2 == trueCase || s2 == s) {
                    s2 = random1.nextInt(4) + 1;
                }
                while (s3 == trueCase || s3 == s || s3 == s2) {
                    s3 = random1.nextInt(4) + 1;
                }
                randomAnswerAudience(s, answer2);
                randomAnswerAudience(s2, answer3);
                randomAnswerAudience(s3, answer4);
                isAudience = false;
                break;

            case R.id.btn_help_call:
                isChooseAnswer = true;
                btnHelpCall.setEnabled(false);
                btnHelpCall.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x);
                dialogCall = new DialogCall(this);
                dialogCall.show();
                dialogCall.getBtnOk().setVisibility(View.GONE);
                dialogCall.setOnReceiveDataListener(this);
                isCall = false;
                break;

            default:
                break;
        }
    }

    private void randomAnswerAudience(int s, int percent) {
        switch (s) {
            case 1:
                dialogAudience.getTvA().setText("A: " + percent + "%");
                break;
            case 2:
                dialogAudience.getTvB().setText("B: " + percent + "%");
                break;
            case 3:
                dialogAudience.getTvC().setText("C: " + percent + "%");
                break;
            case 4:
                dialogAudience.getTvD().setText("D: " + percent + "%");
                break;
            default:
                break;
        }
    }

    private void isEnableBTN(boolean b) {
        if (is5050)
            btnHelp5050.setEnabled(b);
        if (isChange)
            btnHelpChangeQuestion.setEnabled(b);
        if (isCall)
            btnHelpCall.setEnabled(b);
        if (isAudience)
            btnHelpAudience.setEnabled(b);
        btnHelpStop.setEnabled(b);
    }

    private void timeCheckAnswer(final int i) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    timeCheckAnswer--;
                    if (timeCheckAnswer == 0) {
                        Message msg = new Message();
                        msg.what = MSG_CHECK_ANSWER;
                        msg.arg2 = i;
                        handler.sendMessage(msg);
                        return;
                    }
                    Message msg1 = new Message();
                    msg1.what = UPDATE_TEXT_VIEW_ANSWER;
                    msg1.arg2 = i;
                    handler.sendMessage(msg1);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onReceiveDataConfirm(String receiveData) {
        switch (receiveData) {
            case DialogConfirm.BTN_CANCEL:
                dialogConfirm.dismiss();
                Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case DialogConfirm.BTN_OK:
                dialogConfirm.dismiss();
                dlActivityPlayer.openDrawer(GravityCompat.START);
                tvCancel.setVisibility(View.GONE);
                tvQ1.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            timeStartGame--;
                            if (timeStartGame == 0) {
                                Message msg = new Message();
                                msg.what = START_GAME;
                                handler.sendMessage(msg);
                                return;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
                break;

            default:
                break;
        }
    }

    private void loadQuestion(int i) {
        isEnableBTN(true);
        Log.d("LOAD_Q", "" + i);
        if (i > 15)
            return;
        time = 31;
        timeCheckAnswer = 15;
        timeCheckAnswer2 = 15;
        timeNextQuestion = 15;
        timeNewQuestion = 2;
        isChooseAnswer = false;
        tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        threadTime = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isChooseAnswer) {
                    time--;
                    Message msg = new Message();
                    msg.what = UPDATE_TEXT_TIME;
                    msg.arg1 = time;
                    handler.sendMessage(msg);
                    if (time == 0) {
                        Message msg1 = new Message();
                        msg1.what = MSG_GAME_OVER;
                        handler.sendMessage(msg1);
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadTime.start();
        tvQuestionNumber.setText("Câu " + i);
        Question question = databaseManager.getOneQuestion("Question" + i);
        trueCase = question.getTrueCase();
        tvQuestionNumber.setText("Câu " + i);
        tvQuestionContent.setText(question.getQuestion());
        tvAnswerA.setText("A: " + question.getCaseA());
        tvAnswerB.setText("B: " + question.getCaseB());
        tvAnswerC.setText("C: " + question.getCaseC());
        tvAnswerD.setText("D: " + question.getCaseD());
        tvAnswerA.setEnabled(true);
        tvAnswerB.setEnabled(true);
        tvAnswerC.setEnabled(true);
        tvAnswerD.setEnabled(true);
    }

    @Override
    public void onReceiveDataOvertime(String receiveData) {
        switch (receiveData) {
            case DialogOvertime.BTN_OK:
                if (questionNumber > 11) {
                    questionNumber = 11;
                } else {
                    if (questionNumber > 6) {
                        questionNumber = 6;
                    } else {
                        questionNumber = 1;
                    }
                }
                Intent intent = new Intent();
                intent.setClass(PlayerActivity.this, GameoverActivity.class);
                intent.putExtra("MONEY", money[questionNumber - 1]);
                intent.putExtra("Q_NUMBER", "" + (questionNumber - 1));
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onReceiveDataConfirmStop(String receiveData) {
        switch (receiveData) {
            case DialogCFStop.BTN_CANCEL:
                dialogCFStop.dismiss();
                break;

            case DialogCFStop.BTN_OK:
                dialogCFStop.dismiss();
                Intent intent = new Intent();
                intent.setClass(PlayerActivity.this, GameoverActivity.class);
                intent.putExtra("MONEY", money[questionNumber - 1]);
                intent.putExtra("Q_NUMBER", "" + (questionNumber - 1));
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onReceiveDataAudience(String receiveData) {
        switch (receiveData) {
            case DialogAudience.BTN_OK:
                isChooseAnswer = false;
                dialogAudience.dismiss();
                threadTime = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!isChooseAnswer) {
                            time--;
                            Message msg = new Message();
                            msg.what = UPDATE_TEXT_TIME;
                            msg.arg1 = time;
                            handler.sendMessage(msg);
                            if (time == 0) {
                                Message msg1 = new Message();
                                msg1.what = MSG_GAME_OVER;
                                handler.sendMessage(msg1);
                                return;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                threadTime.start();
                break;

            default:
                break;
        }
    }

    @Override
    public void onReceiveDataCall(String receiveData) {
        switch (receiveData) {
            case DialogCall.BTN_1:
                dialogCall.getBtnOk().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setText(randomAnswer());
                dialogCall.getLl2().setVisibility(View.GONE);
                dialogCall.getLl3().setVisibility(View.GONE);
                dialogCall.getLl4().setVisibility(View.GONE);
                break;

            case DialogCall.BTN_2:
                dialogCall.getBtnOk().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setText(randomAnswer());
                dialogCall.getLl1().setVisibility(View.GONE);
                dialogCall.getLl3().setVisibility(View.GONE);
                dialogCall.getLl4().setVisibility(View.GONE);
                break;

            case DialogCall.BTN_3:
                dialogCall.getBtnOk().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setText(randomAnswer());
                dialogCall.getLl2().setVisibility(View.GONE);
                dialogCall.getLl1().setVisibility(View.GONE);
                dialogCall.getLl4().setVisibility(View.GONE);
                break;

            case DialogCall.BTN_4:
                dialogCall.getBtnOk().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setVisibility(View.VISIBLE);
                dialogCall.getTvAnswer().setText(randomAnswer());
                dialogCall.getLl2().setVisibility(View.GONE);
                dialogCall.getLl3().setVisibility(View.GONE);
                dialogCall.getLl1().setVisibility(View.GONE);
                break;

            case DialogCall.BTN_OK:
                dialogCall.dismiss();
                isChooseAnswer = false;
                threadTime = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!isChooseAnswer) {
                            time--;
                            Message msg = new Message();
                            msg.what = UPDATE_TEXT_TIME;
                            msg.arg1 = time;
                            handler.sendMessage(msg);
                            if (time == 0) {
                                Message msg1 = new Message();
                                msg1.what = MSG_GAME_OVER;
                                handler.sendMessage(msg1);
                                return;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                threadTime.start();
                break;

            default:
                break;
        }
    }

    private String randomAnswer() {
        Random rd = new Random();
        String answer = "Theo tôi là đáp án ";
        int a = rd.nextInt(4);
        if (a != 0) {
            switch (trueCase) {
                case 1:
                    answer += "A";
                    break;

                case 2:
                    answer += "B";
                    break;

                case 3:
                    answer += "C";
                    break;

                case 4:
                    answer += "D";
                    break;

                default:
                    break;
            }
        } else {
            switch (rd.nextInt(4) + 1) {
                case 1:
                    answer += "A";
                    break;

                case 2:
                    answer += "B";
                    break;

                case 3:
                    answer += "C";
                    break;

                case 4:
                    answer += "D";
                    break;

                default:
                    break;
            }
        }
        return answer;
    }

}
