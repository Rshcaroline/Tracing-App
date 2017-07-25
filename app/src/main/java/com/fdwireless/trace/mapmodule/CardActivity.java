package com.fdwireless.trace.mapmodule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fdwireless.trace.ar.UnityPlayerActivity;
import com.fdwireless.trace.httpclass.my_GsonRequest;
import com.fdwireless.trace.infoclass.Clip;
import com.fdwireless.trace.infoclass.User;
import com.fdwireless.trace.ui.friend_circle.NearbyActivity;

import java.util.ArrayList;

// Author: Jin Xisen
// Purpose: 从Map到AR或者信息界面的过渡界面
// 0221增加的注释：切换至这个活动使，要显示的Clip会全部通过intent传过来，
// 存在clip变量中，clip也是唯一的数据来源

public class CardActivity extends Activity{

    Clip clip = null;
    boolean reachable = false; //是否距离合适
    private final int MSG = 0; //留言点，在地图显示为[...]
    private final int DEST =1; //目标点（无AR）显示为[!]
    private final int AR = 2; //AR点，在地图显示为[AR]

    private my_GsonRequest<Clip> request =new my_GsonRequest<Clip>(this);
    final String BASEURL = "";
    public User userData;
    private ArrayList<Clip> clips=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userData=getIntent().getParcelableExtra("user_data");
        this.clips=this.getIntent().getParcelableArrayListExtra("clip_list");
        clip = (Clip)getIntent().getExtras().get("info");
//        clip = new Clip();
//        clip.setType(getIntent().getIntExtra("Type",-1));
//        clip.setId(getIntent().getStringExtra("Id"));
//        clip.setOwner(getIntent().getIntExtra("Owner",-1));
//        clip.setName(getIntent().getStringExtra("Name"));
//        clip.setLat(getIntent().getDoubleExtra("Lat",0.0));
//        clip.setLon(getIntent().getDoubleExtra("Lon",0.0));
//        clip.setQAD(getIntent().getStringExtra("Question"),getIntent().getStringExtra("Answer"),
//                getIntent().getStringExtra("Direction"));
//        clip.setText(getIntent().getStringExtra("Intro"));
//        clip.imgResource = getIntent().getIntExtra("ImgRes",-1);
        reachable = getIntent().getBooleanExtra("reachable",false);
        createImageCard();
    }

    private void createImageCard()
    {
        Log.d("create","created"+clip.type+","+clip.text+","+clip.getLat()+reachable);
        if(clip.type == Clip.AR)
        {
            Window window = getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.bottom_card_style);
            setContentView(R.layout.activity_transis_ar);
            TextView textView = (TextView)findViewById(R.id.cardText);
            if(clip.text!=null)
                textView.setText(clip.text);
            TextView help = (TextView)findViewById(R.id.help_msg);
            if(!reachable)
                help.setText("距离太远");
            else
                help.setText("点击通过AR扫描标志物");
            ImageView imageView = (ImageView)findViewById(R.id.cardImage);
            imageView.setImageResource(clip.getImg());

            FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_ar);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivityForResult...（进入AR界面）
                    Intent intent = new Intent(getApplicationContext(),UnityPlayerActivity.class);
                    int arid = Integer.parseInt(clip.getId());
                    intent.putExtra("inform",arid);//season baba 看这里！！！我这里只设置了固定的传入参数！//已阅，已修改
                    startActivityForResult(intent,1);
                }
            });
        }
        else if(clip.type == Clip.MSG)
        {
            Window window = getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.bottom_card_style);
            setContentView(R.layout.activity_trainsition_msg);
            TextView textView = (TextView)findViewById(R.id.cardText);
            if(clip.text!=null)
                textView.setText("“"+clip.text+"”");
            TextView help = (TextView)findViewById(R.id.help_msg);


            if(!reachable) {
                help.setText("（距离较远）某个人的足迹");
            }
            else {
                String s = clip.getName()+" 在这里说：";
                help.setText(s);
            }
            ImageView imageView =(ImageView)findViewById(R.id.cardImage);
            Glide.with(CardActivity.this).load("http://115.159.198.209/Tracing/img/items/"+clip.getId()+"_0.jpg").error(R.drawable.background_msg).centerCrop().
                    crossFade().into(imageView);

            FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_detail);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reachable)
                    {
                        Intent intent3 = new Intent(CardActivity.this, NearbyActivity.class);


                        intent3.putExtra("user_data",userData);

                        intent3.putParcelableArrayListExtra("clip_list",clips);
                        startActivity(intent3);
                    }
                }
            });
        }
        else if(clip.type == Clip.DEST)
        {
            Window window = getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.bottom_card_style);
            setContentView(R.layout.dialog_question);
            Button btn = (Button)findViewById(R.id.btn_answer);
            if(!reachable)
                btn.setText("距离太远");
            else
                btn.setText("提交答案！");
            TextView viewQuestion = (TextView)findViewById(R.id.text_question);
            viewQuestion.setText(clip.question);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reachable)
                    {
                        QAlogic();
                    }
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("result");
                    //Toast.makeText(CardActivity.this, returnedData, Toast.LENGTH_SHORT).show();
                    if(returnedData.equals("success"))
                        succeedAndFinish();
                }
                else
                    Log.d("result",""+resultCode);
                break;
            default:
        }
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(0,R.anim.dialog_bottom_quit);
    }

    private void QAlogic()
    {
        EditText edt = (EditText)findViewById(R.id.edit_answer);
        String usrAns = edt.getText().toString();
        if(usrAns.equals(clip.answer))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this,
                    R.style.dialog_style);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    succeedAndFinish(); //debug
                }
            });
            builder.setTitle("解锁成功!").setMessage(clip.direction).show();
        }
        else
        {
            Toast.makeText(CardActivity.this,"啊呀！解锁失败了",Toast.LENGTH_SHORT).show();
        }
    }

    public void succeedAndFinish()
    {
        Intent intent = new Intent();
        intent.putExtra("Success",true);
        setResult(RESULT_OK,intent);
        finish();
    }
}
