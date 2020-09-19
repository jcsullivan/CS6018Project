package com.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MasterDetail extends AppCompatActivity implements NavPaneFragment.OnNavSelectedListener {
    private NavPaneFragment mMasterListNavFrag;
    private ProfilePageFragment profilePageFragment;
    private WeightManFragment weightManFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        mMasterListNavFrag = new NavPaneFragment();
        profilePageFragment = new ProfilePageFragment();
        weightManFragment = new WeightManFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if(isTablet()) {
            fTrans.replace(R.id.master_detail_nav_pane_tablet,mMasterListNavFrag,"frag_masterlist");
        }
        else {
            fTrans.replace(R.id.master_detail_pane_phone,mMasterListNavFrag,"frag_masterlist");
        }
        fTrans.commit();
    }


    boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onNavSelected(int navIndex) {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if(isTablet()){
            if(navIndex==mMasterListNavFrag.PROFILE_BUTTON_INDEX){
                fTrans.replace(R.id.master_detail_right_pane_tablet,profilePageFragment,"frag_myprof_tab");
            }else if (navIndex==mMasterListNavFrag.WEIGHT_BUTTON_INDEX){
                fTrans.replace(R.id.master_detail_right_pane_tablet,weightManFragment,"frag_weightman_tab");

            }
        }
        else{
            if(navIndex==mMasterListNavFrag.PROFILE_BUTTON_INDEX){
                fTrans.replace(R.id.master_detail_pane_phone,profilePageFragment,"frag_myprof_phone");
            }else if (navIndex==mMasterListNavFrag.WEIGHT_BUTTON_INDEX){
                fTrans.replace(R.id.master_detail_pane_phone,weightManFragment,"frag_weightman_phone");

            }
        }
        fTrans.commit();

    }
}