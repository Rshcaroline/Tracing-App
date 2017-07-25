package com.fdwireless.trace.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fdwireless.trace.httpclass.ImageRequest;
import com.fdwireless.trace.httpclass.my_CallBackListener;
import com.fdwireless.trace.httpclass.my_GsonRequest;
import com.fdwireless.trace.infoclass.User;
import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.ui.graph.CircleImageView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    static final String BASEURL="http://115.159.198.209/Tracing/login_check.php";
    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private Button register;

    private my_GsonRequest<User> request =new my_GsonRequest<User>(this);


    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;

   // private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
     //   imageView = (ImageView) findViewById(R.id.image_view);
        CircleImageView circleImageView=(CircleImageView)findViewById(R.id.image_view);
        circleImageView.setImageResource(R.drawable.add_icon);
        //imageView.setImageResource(R.drawable.nav_icon);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = accountEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                Map<String, String> token = new HashMap<String, String>();
                token.put("name", account);
                token.put("pass", password);

              request.post(BASEURL,User.class,new my_CallBackListener<User>() {
                        @Override
                    public void onSuccessResponse(User response) {

                        if (response!=null) {
                            editor = pref.edit();
                            if(rememberPass.isChecked()){//检查"记住密码"的复选框是否被选中

                                editor.putBoolean("remember_password",true);
                                editor.putString("account",account);
                                editor.putString("password",password);
                                editor.putString("icon_url","http://115.159.198.209/Tracing/img/usericon/"+response.getId()+".jpg");
                                editor.putInt("id",response.getId());

                            }else {
                                editor.clear();
                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user_data",response);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "account or password is invalid",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     Log.e("VolleyError",error.toString());
                    }
                },token);

            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //login.setBackgroundColor(Color.parseColor("#2196F3")); //设置按钮背景颜色
        //register.setBackgroundColor(Color.parseColor("#2196F3"));

        //记住密码
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            //将账号和密码都设置到文本框中
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            String iconUrl=pref.getString("icon_url","");


            accountEdit.setText(account);
            passwordEdit.setText(password);

          new ImageRequest(LoginActivity.this).get(circleImageView,iconUrl);

            rememberPass.setChecked(true);
        }
    }
}