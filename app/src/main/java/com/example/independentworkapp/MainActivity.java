package com.example.independentworkapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.independentworkapp.Activity.login;
import com.example.independentworkapp.Fragment.AppliedEventFragment;
import com.example.independentworkapp.Fragment.EditProfileFragment;
import com.example.independentworkapp.Fragment.HomeFragment;
import com.example.independentworkapp.Fragment.PostedEventFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);
        navigationView=findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if(savedInstanceState==null)
        {
            navigationView.setCheckedItem(R.id.mhome);
            loadFrag(new HomeFragment(),true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id=item.getItemId();
                navigationView.setCheckedItem(id);
                switch (id)
                {
                    case R.id.mhome:
                    {
                        loadFrag(new HomeFragment(),false);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.Applied_Events:
                    {
                        loadFrag(new AppliedEventFragment(),false);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.Posted_Events:
                    {
                        loadFrag(new PostedEventFragment(),false);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.Edit_Profile:
                    {
                        loadFrag(new EditProfileFragment(),false);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.mlogout:
                    {
                        Intent BackToLogin = new Intent(getApplicationContext(), login.class);
                        startActivity(BackToLogin);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        finish();
                        Toast.makeText(MainActivity.this, "Logged Out Going to Login Page", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                    {
                        Toast.makeText(MainActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                }
                return true;
            }
        });
    }
    public void loadFrag(Fragment fragment, Boolean flag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag)
            ft.replace(R.id.framelayout,fragment);
        else
            ft.replace(R.id.framelayout,fragment);
        ft.commit();
        result=flag;
    }

    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (!result)
            {
                ft.replace(R.id.framelayout,new HomeFragment());
                ft.commit();
                navigationView.setCheckedItem(R.id.mhome);
                result=true;
            }
            else
            {
                finish();
            }
        }

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}