package com.zennex.task.module.act_navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.Drawer;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseActivity;
import com.zennex.task.model.DrawerItem;
import com.zennex.task.model.ListItem;
import com.zennex.task.module.frg_list.ListFragment;
import com.zennex.task.module.frg_list.ListRouter;
import com.zennex.task.module.frg_list_dialog.ListDialogRouter;
import com.zennex.task.module.frg_list_edit.ListEditFragment;
import com.zennex.task.module.frg_list_edit.ListEditRouter;
import com.zennex.task.module.frg_main.MainFragment;
import com.zennex.task.module.frg_main.MainRouter;
import com.zennex.task.module.frg_map.MapFragment;
import com.zennex.task.module.frg_map.MapRouter;
import com.zennex.task.module.frg_parsing.ParsingFragment;
import com.zennex.task.module.frg_parsing.ParsingRouter;
import com.zennex.task.module.frg_scaling.ScalingMainFragment;
import com.zennex.task.module.frg_scaling.ScalingMainRouter;
import com.zennex.task.module.frg_scaling_detail.ScalingDetailFragment;
import com.zennex.task.module.frg_scaling_detail.ScalingDetailRouter;
import com.zennex.task.module.frg_settings.SettingsFragment;
import com.zennex.task.module.frg_settings.SettingsRouter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends BaseActivity implements NavigationContract.View,
        SettingsRouter,
        ScalingDetailRouter,
        ScalingMainRouter,
        ParsingRouter,
        MapRouter,
        MainRouter,
        ListRouter,
        ListDialogRouter,
        ListEditRouter {

    public static final String TAG = "tag_nav_act";
    public static final String IS_RESTART_FLAG_KEY = "is_restart_flag_key";

    @BindView(R.id.nav_view_list) ListView liNavigation;
    private ArrayList<DrawerItem> generateDrawerItems() {
        return new ArrayList<DrawerItem>() {
            {
                add(new DrawerItem(Drawer.MAP, getString(R.string.txt_map), R.mipmap.app_ic_map));
                add(new DrawerItem(Drawer.PARSING, getString(R.string.txt_parsing), R.mipmap.app_ic_parsing));
                add(new DrawerItem(Drawer.SCALIING, getString(R.string.txt_scaling), R.mipmap.app_ic_scaling));
                add(new DrawerItem(Drawer.LIST, getString(R.string.txt_list), R.mipmap.app_ic_list));
            }
        };
    }

    @Inject NavigationPresenter presenter;

    // region - Lifecycle -

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        presenter.attachView(this);

        setContentView(R.layout.act_navigation);

        ButterKnife.bind(this);

        initUix();

        if (savedInstanceState == null) {
            navigateToMainScreen();
        }

        if (getIntent().hasExtra(IS_RESTART_FLAG_KEY)) {
            boolean isRestart = getIntent().getBooleanExtra(IS_RESTART_FLAG_KEY, false);
            if (isRestart) navigateToSettingsScreen();
            getIntent().removeExtra(IS_RESTART_FLAG_KEY);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    // endregion


    // region - Event Menu -

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                navigateToSettingsScreen();
                return true;
            default:
                return false;
        }
    }

    // endregion


    // region - OnBackPressed -

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame);

        if (currentFragment instanceof OnBackPressedListener && currentFragment.isVisible()) {
            OnBackPressedListener backPressedListener = (OnBackPressedListener) currentFragment;
            backPressedListener.onBackPressed();
            return;
        }

        super.onBackPressed();
    }

    // endregion


    // region - Permission -

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null)
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    // endregion


    // region - Navigation -

    @Override
    public void restart() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_RESTART_FLAG_KEY, true);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    @Override
    public void navigateToMainScreen() {
        Fragment frg = new MainFragment();
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToScalingMainScreen() {
        Fragment frg = new ScalingMainFragment();
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToScalingDetailScreen(Bitmap bitmap) {
        Fragment frg = ScalingDetailFragment.newInstance(bitmap);
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToMapScreen() {
        Fragment frg = new MapFragment();
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToParsingScreen() {
        Fragment frg = new ParsingFragment();
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToListScreen() {
        Fragment frg = new ListFragment();
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToListEditScreen() {
        Fragment frg = ListEditFragment.newInstance(null, true);
        navigateReplaceOnScreen(frg);
    }

    @Override
    public void navigateToListEditScreen(ListItem item) {
        Fragment frg = ListEditFragment.newInstance(item, false);
        navigateReplaceOnScreen(frg);
    }

    public void navigateToSettingsScreen() {
        Fragment frg = new SettingsFragment();
        navigateReplaceOnScreen(frg);
    }

    private void navigateReplaceOnScreen(Fragment frg) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, frg, frg.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    // endregion


    // region - Event handlers -

    // endregion


    // region - Contract -

    // endregion


    // region - Methods -

    private void initUix() {
        initToolbar();
        initDrawer();
    }

    private void initDrawer() {
        liNavigation.setAdapter(new NavigationAdapter(this, generateDrawerItems()));
        liNavigation.setOnItemClickListener(onItemClickListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);

            DrawerItem item = (DrawerItem) liNavigation.getItemAtPosition(position);
            switch (item.getId()) {
                case Drawer.MAP:
                    navigateToMapScreen();
                    break;
                case Drawer.PARSING:
                    navigateToParsingScreen();
                    break;
                case Drawer.SCALIING:
                    navigateToScalingMainScreen();
                    break;
                case Drawer.LIST:
                    navigateToListScreen();
                    break;
                default:
                    return;
            }
            ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        }
    };

    // endregion
}
