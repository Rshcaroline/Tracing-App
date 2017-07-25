package com.fdwireless.trace.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.ui.graph.DividerItemDecoration;
import com.fdwireless.trace.ui.menu.MenuGroupPeople;
import com.fdwireless.trace.ui.menu.MenuSearch;
import com.fdwireless.trace.ui.nav.NavFriends;
import com.fdwireless.trace.ui.nav.NavSettings;
import com.fdwireless.trace.ui.nav.NavTeam;
import com.fdwireless.trace.ui.nav.NavUser;
import com.fdwireless.trace.ui.friend_circle.NearbyActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupFour extends AppCompatActivity {

    private List<FourTeam> fourteamList = new ArrayList<FourTeam>();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fdwireless.trace.mapmodule.R.layout.activity_group_four);
        initFourTeams();
        RecyclerView recyclerView = (RecyclerView) findViewById(com.fdwireless.trace.mapmodule.R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FourTeamAdapter adapter = new FourTeamAdapter(fourteamList);
        recyclerView.setAdapter(adapter);

        //添加装饰类
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

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
                        Intent intent1 = new Intent(GroupFour.this, NavUser.class);
                        startActivity(intent1);
                        break;
                    case R.id.friends:
                        Intent intent2 = new Intent(GroupFour.this, NavFriends.class);
                        startActivity(intent2);
                        break;
                    case R.id.team:
                        Intent intent3 = new Intent(GroupFour.this, NavTeam.class);
                        startActivity(intent3);
                        break;
                    case R.id.settings:
                        Intent intent4 = new Intent(GroupFour.this, NavSettings.class);
                        startActivity(intent4);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    //标题栏的菜单的按钮的点击事件 search group nearby
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent1 = new Intent(GroupFour.this, MenuSearch.class);
                startActivity(intent1);
                break;
            case R.id.group:
                Intent intent2 = new Intent(GroupFour.this, MenuGroupPeople.class);
                startActivity(intent2);
                break;
            case R.id.nearby:
                Intent intent3 = new Intent(GroupFour.this, NearbyActivity.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }

    //标题栏菜单栏点开之后的按钮的创建 search group nearby
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    private void initFourTeams() {
        FourTeam one = new FourTeam("one", com.fdwireless.trace.mapmodule.R.drawable.one, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(one);
        FourTeam two = new FourTeam("two", com.fdwireless.trace.mapmodule.R.drawable.two, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(two);
        FourTeam three = new FourTeam("three", com.fdwireless.trace.mapmodule.R.drawable.three, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(three);
        FourTeam four = new FourTeam("four", com.fdwireless.trace.mapmodule.R.drawable.four, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(four);
        FourTeam five = new FourTeam("five", com.fdwireless.trace.mapmodule.R.drawable.five, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(five);
        FourTeam six = new FourTeam("six", com.fdwireless.trace.mapmodule.R.drawable.six, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(six);
        FourTeam seven = new FourTeam("seven", com.fdwireless.trace.mapmodule.R.drawable.seven, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(seven);
        FourTeam eight = new FourTeam("eight", com.fdwireless.trace.mapmodule.R.drawable.eight, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(eight);
        FourTeam nine = new FourTeam("nine", com.fdwireless.trace.mapmodule.R.drawable.nine, R.drawable.nav_icon, R.drawable.nav_icon,
                R.drawable.nav_icon, R.drawable.nav_icon, "0/4");
        fourteamList.add(nine);
    }
}