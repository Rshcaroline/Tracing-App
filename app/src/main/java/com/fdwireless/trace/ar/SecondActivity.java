package com.fdwireless.trace.ar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.fdwireless.trace.mapmodule.R;

public class SecondActivity extends Activity {

    int infodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_dialog);
        Button secondButton = (Button) findViewById(R.id.button2);
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别成功
                intent2.putExtra("success", "success");
                setResult(RESULT_OK,intent2);
                finish();
            }
        });
        TextView textView = (TextView) findViewById(R.id.information);
        infodata = getIntent().getIntExtra("info2",0);
        switch (infodata){
            case 9001:
                textView.setText(R.string.info1);
                break;
            case 9002:
                textView.setText(R.string.info2);
                break;
            case 9003:
                textView.setText(R.string.info3);
                break;
            case 9005:
                textView.setText(R.string.info4);
                break;
            case 9006:
                textView.setText(R.string.info5);
                break;
            case 9007:
                textView.setText(R.string.info6);
                break;
            case 9008:
                textView.setText(R.string.info7);
                break;
            case 9009:
                textView.setText(R.string.info8);
                break;
            case 9010:
                textView.setText(R.string.info9);
                break;
            default:
                break;
        }


    }

    @Override
    public void onBackPressed(){

        Intent intent2 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别成功
        intent2.putExtra("success", "success");
        setResult(RESULT_OK,intent2);
        finish();
    }
}
