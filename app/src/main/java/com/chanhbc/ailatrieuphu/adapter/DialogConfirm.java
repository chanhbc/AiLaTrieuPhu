package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chanhbc.ailatrieuphu.R;

public class DialogConfirm extends Dialog implements View.OnClickListener {
    public static final String BTN_CANCEL = "CANCEL";
    public static final String BTN_OK = "OK";
    private Button btnCancel;
    private Button btnOk;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogConfirm(Context context) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        initializeComponents();
    }

    private void initializeComponents() {
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnOk = (Button) findViewById(R.id.btn_ready);
        btnCancel.setOnClickListener(this);
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
            case R.id.btn_cancel:
                onReceiveDataListener.onReceiveDataConfirm(BTN_CANCEL);
                break;
            case R.id.btn_ready:
                onReceiveDataListener.onReceiveDataConfirm(BTN_OK);
                break;
            default:
                break;
        }
    }

    public interface OnReceiveDataListener {
        void onReceiveDataConfirm(String receiveData);
    }
}
