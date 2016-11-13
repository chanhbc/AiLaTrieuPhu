package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.ailatrieuphu.R;

public class DialogCall extends Dialog implements View.OnClickListener {
    public static final String BTN_OK = "OK";
    public static final String BTN_1 = "OK1";
    public static final String BTN_2 = "OK2";
    public static final String BTN_3 = "OK3";
    public static final String BTN_4 = "OK4";
    private Button btnOk;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private TextView tvAnswer;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogCall(Context context) {
        super(context);
        setContentView(R.layout.dialog_call);
        initializeComponents();
    }

    private void initializeComponents() {
        btnOk = (Button) findViewById(R.id.btn_close);
        btnOk.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn_bacsi);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn_giaovien);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn_kisu);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn_phongvien);
        btn4.setOnClickListener(this);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        tvAnswer = (TextView) findViewById(R.id.tv_answer);
        tvAnswer.setVisibility(View.GONE);
    }

    public TextView getTvAnswer() {
        return tvAnswer;
    }

    public Button getBtnOk(){
        return btnOk;
    }

    public LinearLayout getLl1() {
        return ll1;
    }

    public LinearLayout getLl2() {
        return ll2;
    }

    public LinearLayout getLl3() {
        return ll3;
    }

    public LinearLayout getLl4() {
        return ll4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                onReceiveDataListener.onReceiveDataCall(BTN_OK);
                break;

            case R.id.btn_bacsi:
                onReceiveDataListener.onReceiveDataCall(BTN_1);
                break;

            case R.id.btn_giaovien:
                onReceiveDataListener.onReceiveDataCall(BTN_2);
                break;

            case R.id.btn_kisu:
                onReceiveDataListener.onReceiveDataCall(BTN_3);
                break;

            case R.id.btn_phongvien:
                onReceiveDataListener.onReceiveDataCall(BTN_4);
                break;

            default:
                break;
        }
    }

    public interface OnReceiveDataListener {
        void onReceiveDataCall(String receiveData);
    }
}
