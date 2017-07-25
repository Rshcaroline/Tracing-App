package com.fdwireless.trace.ar;

import com.fdwireless.trace.mapmodule.R;
import com.unity3d.player.*;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.fdwireless.trace.mapmodule.R.string.info1;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    Context mContext = null;

    Activity mActivity = null;

    int infoData = 0;

    String returnedData = null;


    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        //setContentView(mUnityPlayer);
        setSystemUIVisible();
        setContentView(R.layout.ar_panel);
        setSystemUIVisible();
        LinearLayout unityView = (LinearLayout) this.findViewById(R.id.unityViewLayout);
        unityView.addView(mUnityPlayer.getView());
        mUnityPlayer.requestFocus();
        mContext = this;
        mActivity = this;
        infoData = getIntent().getIntExtra("inform",0);
    }

    @Override
    public void onBackPressed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*
                if(returnedData != null){
                    Intent intent3 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别成功 //已阅-3-
                    intent3.putExtra("result", "success");
                    setResult(RESULT_OK,intent3);
                }
                else{
                    Intent intent3 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别fail
                    intent3.putExtra("result", "fail");
                    setResult(RESULT_OK,intent3);
                }
                */
                mUnityPlayer.quit();
                //finish();
            }
        });

        if(returnedData != null){
            Intent intent3 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别成功 //已阅-3-
            intent3.putExtra("result", "success");
            setResult(RESULT_OK,intent3);
        }
        else{
            Intent intent3 = new Intent(); //season baba看这里！！点击返回上一个活动AR识别fail
            intent3.putExtra("result", "fail");
            setResult(RESULT_OK,intent3);
        }
//        mUnityPlayer.quit();
        finish();
    }

    private void setSystemUIVisible() {
        View decorView = getWindow().getDecorView();
        int uiOptions=View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnedData = data.getStringExtra("success");//这一步是必须的，如果启动了信息界面，说明AR识别成功，将信息界面发来的成功信息保存在当前活动的全局字符串中
                    Toast.makeText(getApplicationContext(), "成功！按返回键返回地图", Toast.LENGTH_SHORT).show();//debug，可删除
                }
                break;
            default:
        }
    }


    public void ClickModel() {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("info2", infoData);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//防止线程响应慢弹出多个重复Activity
        mActivity.startActivityForResult(intent, 1);
    }


    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}
