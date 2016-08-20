package com.example.err0r.sweetdialogsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.pcyan.sweetdialog.SweetDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                SweetDialog.build()
                        .setCancelAble(true)
                        .autoCancel(true)
                        .setGravity(Gravity.CENTER)
                        .setTitle("Hi there")
                        .setCustomView(R.layout.dialog_test,new SweetDialog.CustomViewCallBack() {
                            @Override
                            public void onViewCreated(View view) {
                                TextView text = (TextView) view.findViewById(R.id.text);
                                text.setText("custom view");
                            }
                        })
                        .showDialog(getSupportFragmentManager(),"test_dialog");
                break;
            default:break;
        }
    }
}
