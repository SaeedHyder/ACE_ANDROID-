package com.app.ace.activities;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;

import com.app.ace.BaseApplication;
import com.app.ace.R;
import com.app.ace.fragments.FriendsInfoFragment;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.SideMenuFragment;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.interfaces.LoadingListener;
import com.app.ace.ui.dialogs.DialogFactory;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import roboguice.activity.RoboFragmentActivity;

/**
 * This class is marked abstract so that it can pair with Dockable Fragments
 * only. All Classes extending this will inherit this functionality of
 * interaction with menus.
 */
public abstract class DockActivity extends RoboFragmentActivity implements
        LoadingListener {

    public static final String KEY_FRAG_FIRST = "firstFrag";
    public SideMenuFragment sideMenuFragment;

    @Inject
    protected BasePreferenceHelper prefHelper;
    //For side menu
    protected DrawerLayout drawerLayout;
    BaseFragment baseFragment;

    public abstract int getDockFrameLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    public void addDockableFragment(BaseFragment frag) {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(getDockFrameLayoutId(), frag);
        transaction
                .addToBackStack(
                        getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();


    }

    public void addDockableFragment(BaseFragment frag, String Tag) {

        try {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(getDockFrameLayoutId(), frag, frag.getClass().getSimpleName());
            transaction
                    .addToBackStack(
                            getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                    : null).commitAllowingStateLoss(); //ommit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getDate(String OurDate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);

            //Log.d("OurDate", OurDate);
        }
        catch (Exception e)
        {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    public void addDockableFragment(FriendsInfoFragment frag, String isAnimate) {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // if ( isAnimate )
        // if ( !(frag instanceof DashboardFragment) ) {
        // // transaction.setCustomAnimations( R.anim.push_right_in,
        // // R.anim.push_right_out, R.anim.push_left_in,
        // // R.anim.push_left_out );
        // }

        transaction.replace(getDockFrameLayoutId(), frag);
        transaction
                .addToBackStack(
                        getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();
    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();

    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void addAndShowDialogFragment(
            android.support.v4.app.DialogFragment dialog) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        dialog.show(transaction, "tag");

    }

    public void prepareAndShowDialog(DialogFragment frag, String TAG,
                                     BaseFragment fragment) {
        FragmentTransaction transaction = fragment.getChildFragmentManager()
                .beginTransaction();
        android.support.v4.app.Fragment prev = fragment.getChildFragmentManager().findFragmentByTag(
                TAG);

        if (prev != null)
            transaction.remove(prev);

        transaction.addToBackStack(null);
        frag.show(transaction, TAG);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        else
            DialogFactory.createQuitDialog(this,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DockActivity.this.finish();

                        }
                    }, R.string.message_quit).show();
    }

    public BaseFragment getLastAddedFragment() {
        return baseFragment;
    }

    public void emptyBackStack() {
        //popBackStackTillEntry( 0 );
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(i);
            if (entry != null && (!(entry instanceof HomeFragment)) && (!(entry instanceof SideMenuFragment))) {
                getSupportFragmentManager().popBackStack(entry.getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

        }
    }

    /**
     * @param entryIndex is the index of fragment to be popped to, for example the
     *                   first fragment will have a index 0;
     */
    public void popBackStackTillEntry(int entryIndex) {
        if (getSupportFragmentManager() == null)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex)
            return;
        BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                entryIndex);
        if (entry != null) {
            getSupportFragmentManager().popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void popFragment() {
        if (getSupportFragmentManager() == null)
            return;
        getSupportFragmentManager().popBackStack();

    }

    public abstract void onMenuItemActionCalled(int actionId, String data);

    public abstract void setSubHeading(String subHeadText);

    public abstract boolean isLoggedIn();

    public abstract void hideHeaderButtons(boolean leftBtn, boolean rightBtn);

    public BaseApplication getMainApplication() {
        return (BaseApplication) getApplication();
    }


}
