package com.fdwireless.trace.ui.menu;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.ui.friend_circle.NearbyActivity;
import com.fdwireless.trace.ui.nav.NavFriends;
import com.fdwireless.trace.ui.nav.NavSettings;
import com.fdwireless.trace.ui.nav.NavTeam;
import com.fdwireless.trace.ui.nav.NavUser;

public class MenuSearch extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);
        search = (Button) findViewById(R.id.search);

        //search.setBackgroundColor(Color.parseColor("#2196F3"));
        //标题栏的创建
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
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
                        Intent intent1 = new Intent(MenuSearch.this, NavUser.class);
                        startActivity(intent1);
                        break;
                    case R.id.friends:
                        Intent intent2 = new Intent(MenuSearch.this, NavFriends.class);
                        startActivity(intent2);
                        break;
                    case R.id.team:
                        Intent intent3 = new Intent(MenuSearch.this, NavTeam.class);
                        startActivity(intent3);
                        break;
                    case R.id.settings:
                        Intent intent4 = new Intent(MenuSearch.this, NavSettings.class);
                        startActivity(intent4);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

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
                Intent intent1 = new Intent(MenuSearch.this, MenuSearch.class);
                startActivity(intent1);
                break;
            case R.id.group:
                Intent intent2 = new Intent(MenuSearch.this, MenuGroupPeople.class);
                startActivity(intent2);
                break;
            case R.id.nearby:
                Intent intent3 = new Intent(MenuSearch.this, NearbyActivity.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }
}
