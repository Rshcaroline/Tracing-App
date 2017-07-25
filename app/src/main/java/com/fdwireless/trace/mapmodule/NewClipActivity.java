package com.fdwireless.trace.mapmodule;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fdwireless.trace.httpclass.GlideImageLoader;
import com.fdwireless.trace.infoclass.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// Author: Jin Xisen
// Purpose: 新建兴趣点的对话框

public class NewClipActivity extends Activity {

    double newLon;
    double newLat;
    int imgnum;

    static final int REQUEST_CODE_GALLERY = 200;
    public User userData;
    private List<File> files = new ArrayList<File>();
    private List<PhotoInfo> mPhotoList = new ArrayList<PhotoInfo>();
    private ArrayList<ImageView> imgviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.userData=getIntent().getParcelableExtra("user_data");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clip);
        newLon = getIntent().getDoubleExtra("Lon", 0.0);
        newLat = getIntent().getDoubleExtra("Lat", 0.0);
        msgLogic();
        imgLogic();
    }

    private void msgLogic() {
        EditText intro = (EditText) findViewById(R.id.edit_intro);
        intro.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        intro.setSingleLine(false);
        intro.setHorizontallyScrolling(false);
        Button btn = (Button) findViewById(R.id.btn_new_clip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText intro = (EditText) findViewById(R.id.edit_intro);
                final String introText = intro.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(NewClipActivity.this, R.style.dialog_style);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(!(introText==null||introText.equals(""))) {
                        postClip(introText);

                        finish();}
                        else{
                            Toast.makeText(NewClipActivity.this, "请留下你的心情",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setTitle("新建足迹点").setMessage("确认新建足迹点？确认后不能修改哦~").show();
            }
        });
    }

    private void imgLogic() {
        imgnum = 0;

        imgviews.add((ImageView) findViewById(R.id.cache1));
        imgviews.add((ImageView) findViewById(R.id.cache2));
        imgviews.add((ImageView) findViewById(R.id.cache3));
        imgviews.add((ImageView) findViewById(R.id.cache4));
        imgviews.add((ImageView) findViewById(R.id.cache5));
        imgviews.add((ImageView) findViewById(R.id.cache6));
        imgviews.add((ImageView) findViewById(R.id.cache7));
        imgviews.add((ImageView) findViewById(R.id.cache8));
        imgviews.add((ImageView) findViewById(R.id.cache9));
        //初始化，每次启动activity时，imgview的src设置为null 不知道是不是这么写
        for (ImageView view : imgviews) {
            view.setImageURI(null);
        }
        //test
        // imgviews.get(0).setImageResource(R.drawable.nearby_haikou);
        // imgviews.get(1).setImageResource(R.drawable.nearby_nanjing);
        // imgviews.get(2).setImageResource(R.drawable.nearby_sanya);
        ImageButton btn = (ImageButton) findViewById(R.id.btn_select_img);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeConfig themeConfig = ThemeConfig.DEFAULT;
                FunctionConfig functionConfig = new FunctionConfig.Builder().setEnablePreview(true).setEnableEdit(true)
                        .setEnableCrop(true).setEnableCamera(true).setEnableRotate(true).setMutiSelectMaxSize(9).build();
                GlideImageLoader imageLoader = new GlideImageLoader();
                CoreConfig coreConfig = new CoreConfig.Builder(NewClipActivity.this, imageLoader, themeConfig).setFunctionConfig(functionConfig).build();
                GalleryFinal.init(coreConfig);
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                // GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY,functionConfig,mOnHanlderResultCallback);
                //此处载入图片
                //imgnum 变量指示当前图片顶到哪里了
            }
        });

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {

                mPhotoList.addAll(resultList);

                for (int i = 0; i < mPhotoList.size(); i++) {
                    files.add(new File(mPhotoList.get(i).getPhotoPath()));
                    Glide.with(NewClipActivity.this).load(mPhotoList.get(i).getPhotoPath()).into(imgviews.get(i));
                }

                //  Bitmap bm = BitmapFactory.decodeFile(mPhotoList.get(0).getPhotoPath());
                // pic.setImageBitmap(bm);
               // Glide.with(NewClipActivity.this).load(mPhotoList.get(0).getPhotoPath()).into();


            }

        }


        @Override
        public void onHanlderFailure(int requestCod, String errorMsg) {
            Toast.makeText(NewClipActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void postClip(String introText) {

            // Clip clip = new Clip(Clip.MSG, newLat, newLon, 1 + "");
            Map<String, String> params = new HashMap<>();
            params.put("owner",userData.getId()+"" );
            params.put("Lat", this.newLat+"");//
            params.put("Lon", this.newLon+"");
            params.put("text", introText);
            params.put("type","0");
            params.put("name",userData.getName());


//创建OkHttpClient实例
            final OkHttpClient client = new OkHttpClient();

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

//遍历map中所有参数到builder
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }

            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            for (File file : files) {

                builder.addFormDataPart("file[]", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }

            //构建请求体
            RequestBody requestBody = builder.build();

            //构建请求
            Request request = new Request.Builder()
                    .url("http://115.159.198.209/Tracing/upload.php")//地址
                    .post(requestBody)//添加请求体
                    .build();

            //发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bodyStr = response.body().string();

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(NewClipActivity.this, bodyStr,
                                    Toast.LENGTH_SHORT).show();
                          //  Log.e("test",bodyStr);


                        }
                    });
                }


            });

    }

    @Override
    public void finish() {
        super.finish();
    }
}
