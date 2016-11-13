package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.ailatrieuphu.R;

public class DialogAudience extends Dialog implements View.OnClickListener {
    public static final String BTN_OK = "OK";
    private TextView tvA;
    private TextView tvB;
    private TextView tvC;
    private TextView tvD;
    private Button btnOk;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogAudience(Context context) {
        super(context);
        setContentView(R.layout.dialog_audience);
        initializeComponents();
    }

    private void initializeComponents() {
        btnOk = (Button) findViewById(R.id.btn_close);
        btnOk.setOnClickListener(this);
        tvA = (TextView) findViewById(R.id.tv_answer_a);
        tvB = (TextView) findViewById(R.id.tv_answer_b);
        tvC = (TextView) findViewById(R.id.tv_answer_c);
        tvD = (TextView) findViewById(R.id.tv_answer_d);
    }

    public TextView getTvA() {
        return tvA;
    }

    public TextView getTvB() {
        return tvB;
    }

    public TextView getTvC() {
        return tvC;
    }

    public TextView getTvD() {
        return tvD;
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
                onReceiveDataListener.onReceiveDataAudience(BTN_OK);
                break;
            default:
                break;
        }
    }

    public interface OnReceiveDataListener {
        void onReceiveDataAudience(String receiveData);
    }
}
