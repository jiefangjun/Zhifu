package fokia.gq.zhifu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fokia.gq.zhifu.Adapter.AddPageFragmentAdapter;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/14/17.
 */

public class AddActivity extends MainActivity {

    private AddPageFragmentAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerAdapter = new AddPageFragmentAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_income) {
            tabLayout.getTabAt(0).select();
        } else if (id == R.id.nav_outlay) {
            tabLayout.getTabAt(1).select();
        } else if (id == R.id.nav_overcome) {
        } else if (id == R.id.nav_note) {
            tabLayout.getTabAt(2).select();

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(AddActivity.this, SettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help){
            Intent intent = new Intent(AddActivity.this, HelpActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_exit) {
            ActivityCollector.finishAll();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
