package com.chanhbc.ailatrieuphu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chanhbc.ailatrieuphu.R;

public class DialogName extends Dialog implements View.OnClickListener {
    private EditText etName;
    private Button btnCancel;
    private Button btnOk;
    public static final String BTN_CANCEL = "CANCEL";
    public static final String BTN_OK = "OK";
    private OnReceiveDataListener onReceiveDataListener;

    public DialogName(Context context) {
        super(context);
        setContentView(R.layout.dialog_input_name);
        initializeComponents();
    }

    private void initializeComponents() {
        etName = (EditText) findViewById(R.id.et_name);
        etName.setText("");
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    public EditText getEtName() {
        return etName;
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveDataName(String receiveData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                onReceiveDataListener.onReceiveDataName(BTN_CANCEL);
                break;
            case R.id.btnOk:
                onReceiveDataListener.onReceiveDataName(BTN_OK);
                break;
            default:
                break;
        }
    }
}
