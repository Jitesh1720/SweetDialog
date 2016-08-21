package com.example.err0r.sweetdialogsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.pcyan.sweetdialog.SweetDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1,btn2,btn3,btn4,btn5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                SweetDialog.build()
                        .setCancelAble(false)
                        .autoCancel(true)
                        .setTitle("Normal Dialog")
                        .setContent("This is a normal dialog")
                        .setOnConfirmListener(new SweetDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetDialog sweetDialog) {
                                Toast.makeText(MainActivity.this, "click confirm", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnCancelListener(new SweetDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetDialog sweetDialog) {
                                Toast.makeText(MainActivity.this, "click cancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .showDialog(getSupportFragmentManager(),"normal_dialog");

                break;
            case R.id.btn2:
                SweetDialog.build()
                        .setTitle("Custom Image")
                        .setCustomImage(R.drawable.github)
                        .showDialog(getSupportFragmentManager(),"custom_image_dialog");

                break;
            case R.id.btn3:
                SweetDialog.build()
                        .setCustomView(R.layout.dialog_test, new SweetDialog.CustomViewCallBack() {
                            @Override
                            public void onViewCreated(View view) {
                                TextView text = (TextView) view.findViewById(R.id.text);
                                text.setText("This is a custom View dialog.");
                            }
                        })
                        .showDialog(getSupportFragmentManager(),"custom_view_dialog");

                break;
            case R.id.btn4:
                SweetDialog.build()
                        .setGravity(Gravity.CENTER)
                        .isLoading(true,"加载中...")
                        .showDialog(getSupportFragmentManager(),"progress_dialog");

                break;
            case R.id.btn5:
                SweetDialog.build()
                        .autoCancel(true)
                        .setGravity(Gravity.BOTTOM)
                        .setCustomView(R.layout.dialog_bottom)
                        .showDialog(getSupportFragmentManager(),"progress_dialog");

                break;

            default:break;
        }
    }
}
