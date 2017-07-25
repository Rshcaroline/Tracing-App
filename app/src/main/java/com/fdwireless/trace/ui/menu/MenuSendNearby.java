package com.fdwireless.trace.ui.menu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fdwireless.trace.mapmodule.R;

public class MenuSendNearby extends AppCompatActivity {

    private EditText saysth;

    private Button reselect;
    private Button cancel;
    private Button send;

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_send_nearby);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        saysth = (EditText) findViewById(com.fdwireless.trace.mapmodule.R.id.saysth);

        cancel = (Button) findViewById(com.fdwireless.trace.mapmodule.R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send = (Button) findViewById(com.fdwireless.trace.mapmodule.R.id.send);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuSendNearby.this,"send",Toast.LENGTH_SHORT).show();
            }
        });

        reselect = (Button) findViewById(com.fdwireless.trace.mapmodule.R.id.reselect);
        reselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuSendNearby.this,"reselect",Toast.LENGTH_SHORT).show();
            }
        });

        //绑定九张图片
        iv1 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image1);
        iv2 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image2);
        iv3 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image3);
        iv4 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image4);
        iv5 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image5);
        iv6 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image6);
        iv7 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image7);
        iv8 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image8);
        iv9 = (ImageView) findViewById(com.fdwireless.trace.mapmodule.R.id.image9);

        //改变图片
        int n =3;

        switch (n){
            case 1:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 2:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 3:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 4:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 5:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv5.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 6:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv5.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv6.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 7:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv5.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv6.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv7.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 8:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv5.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv6.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv7.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv8.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            case 9:
                iv1.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv2.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv3.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv4.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv5.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv6.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv7.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv8.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                iv9.setImageResource(com.fdwireless.trace.mapmodule.R.drawable.nav_icon);
                break;
            default:
                break;
        }
    }
}