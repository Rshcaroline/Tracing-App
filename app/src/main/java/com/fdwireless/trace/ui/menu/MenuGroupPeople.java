package com.fdwireless.trace.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.ui.team.GroupFour;

import java.util.ArrayList;
import java.util.List;


public class MenuGroupPeople extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;

    /** Called when the activity is first created. */
    private List<String> list_1 = new ArrayList<String>();
    private List<String> list_2 = new ArrayList<String>();
    private TextView myTextView_1,myTextView_2;
    private Spinner mySpinner_1,mySpinner_2;
    private ArrayAdapter<String> adapter_1,adapter_2;
    private Button group;
    private String route;
    private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_group_people);

        /*
        //标题栏的创建
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //侧滑菜单栏的创建
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setItemIconTintList(null); //让图标的颜色显示本身的颜色

        //侧滑菜单栏的item的点击事件 user friends team settings
        navView.setCheckedItem(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){
                    case R.id.user:
                        Intent intent1 = new Intent(MenuGroupPeople.this, NavUser.class);
                        startActivity(intent1);
                        break;
                    case R.id.friends:
                        Intent intent2 = new Intent(MenuGroupPeople.this, NavFriends.class);
                        startActivity(intent2);
                        break;
                    case R.id.team:
                        Intent intent3 = new Intent(MenuGroupPeople.this, NavTeam.class);
                        startActivity(intent3);
                        break;
                    case R.id.settings:
                        Intent intent4 = new Intent(MenuGroupPeople.this, NavSettings.class);
                        startActivity(intent4);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        */

        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list_2.add("   1");
        list_2.add("   4");
        list_2.add("   5");
        list_2.add("   6");
        list_2.add("   7");
        list_1.add("   邯郸");
        list_1.add("   张江");
        list_1.add("   枫林");
        list_1.add("   江湾");
        myTextView_1 = (TextView)findViewById(R.id.TextView_number);
        myTextView_2 = (TextView)findViewById(R.id.TextView_route);
        mySpinner_1 = (Spinner)findViewById(R.id.Spinner_number);
        mySpinner_2 = (Spinner)findViewById(R.id.Spinner_route);

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list_1);
        adapter_2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list_2);

        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //第四步：将适配器添加到下拉列表上
        mySpinner_1.setAdapter(adapter_1);
        mySpinner_2.setAdapter(adapter_2);

        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        mySpinner_1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                route = adapter_1.getItem(arg2);
                myTextView_1.setText("队伍路线：");
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);

                //myTextView_1.setTextColor(getResources().getColor(R.color.colorPrimary));    //设置颜色
                myTextView_1.setTextSize(18.0f);    //设置大小
                myTextView_1.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                myTextView_1.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        mySpinner_2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                number = adapter_2.getItem(arg2);
                myTextView_2.setText("队伍人数：");
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);

                //myTextView_2.setTextColor(getResources().getColor(R.color.colorPrimary));    //设置颜色
                myTextView_2.setTextSize(18.0f);    //设置大小
                myTextView_2.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                myTextView_2.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });

        /*下拉菜单弹出的内容选项触屏事件处理*/
        mySpinner_1.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        mySpinner_2.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        mySpinner_1.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
        mySpinner_2.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });

        group = (Button) findViewById(R.id.Button_team);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (number.equals("   4") && route.equals("   张江")) {
                    Intent intent = new Intent(MenuGroupPeople.this, GroupFour.class);
                    startActivity(intent);
                    finish();
                } else if(number.equals("   1") && route.equals("   张江")) {
                    Intent intent = new Intent();
                    intent.putExtra("route","ZJ");
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(MenuGroupPeople.this, "线路暂未开放",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //group.setBackgroundColor(Color.parseColor("#2196F3")); //设置按钮背景颜色
    }

    /*
    //标题栏菜单栏点开之后的按钮的创建 search group nearby
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent1 = new Intent(MenuGroupPeople.this, MenuSearch.class);
                startActivity(intent1);
                break;
            case R.id.group:
                Intent intent2 = new Intent(MenuGroupPeople.this, MenuGroupPeople.class);
                startActivity(intent2);
                break;
            case R.id.nearby:
                Intent intent3 = new Intent(MenuGroupPeople.this, NearbyActivity.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }
    */
}