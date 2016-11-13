package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chanhbc.ailatrieuphu.R;

public class DialogOvertime extends Dialog implements View.OnClickListener {
    public static final String BTN_OK = "OK";
    private Button btnOk;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogOvertime(Context context) {
        super(context);
        setContentView(R.layout.dialog_overtime);
        initializeComponents();
    }

    private void initializeComponents() {
        btnOk = (Button) findViewById(R.id.btn_ready);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ready:
                onReceiveDataListener.onReceiveDataOvertime(BTN_OK);
                break;
            default:
                break;
        }
    }

    public interface OnReceiveDataListener {
        void onReceiveDataOvertime(String receiveData);
    }
}
