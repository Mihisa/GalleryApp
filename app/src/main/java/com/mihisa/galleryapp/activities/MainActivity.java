package com.mihisa.galleryapp.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.mihisa.galleryapp.BuildConfig;
import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.activities.base.SharedMediaActivity;
import com.mihisa.galleryapp.data.Album;
import com.mihisa.galleryapp.data.Media;
import com.mihisa.galleryapp.fragments.AlbumsFragment;
import com.mihisa.galleryapp.fragments.BaseFragment;
import com.mihisa.galleryapp.fragments.EditModeListener;
import com.mihisa.galleryapp.fragments.NothingToShowListener;
import com.mihisa.galleryapp.fragments.RvMediaFragment;
import com.mihisa.galleryapp.util.AlertDialogsHelper;
import com.mihisa.galleryapp.util.LegacyCompatFileProvider;
import com.mihisa.galleryapp.util.Security;
import com.mihisa.galleryapp.util.StringUtils;
import com.mihisa.galleryapp.util.preferences.Prefs;
import com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.ItemListener;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_ABOUT;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_ALL_ALBUMS;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_ALL_MEDIA;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_DONATE;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_HIDDEN_FOLDERS;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_SETTINGS;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NAVIGATION_ITEM_WALLPAPERS;
import static com.mihisa.galleryapp.views.navigation_drawer.NavigationDrawer.NavigationItem;

public class MainActivity extends SharedMediaActivity implements RvMediaFragment.MediaClickListener,
        AlbumsFragment.AlbumClickListener, NothingToShowListener, EditModeListener, ItemListener {

    public static final String ARGS_PICK_MODE = "pick_mode";

    private static final String SAVE_ALBUM_MODE = "album_mode";

    @BindView(R.id.fab_camera) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout navigationDrawer;
    @BindView(R.id.home_navigation_drawer) NavigationDrawer navigationDrawerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinator_main_layout) CoordinatorLayout mainLayout;

    private AlbumsFragment albumsFragment;
    private RvMediaFragment rvMediaFragment;

    private boolean pickMode = false;
    private boolean albumsMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUi();
        pickMode = getIntent().getBooleanExtra(ARGS_PICK_MODE, false);

        if (savedInstanceState == null) {
            // Add AlbumsFragment to UI
            albumsMode = true;

            albumsFragment = new AlbumsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, albumsFragment, AlbumsFragment.TAG)
                    .addToBackStack(null)
                    .commit();

            albumsFragment.setListener(this);
            albumsFragment.setEditModeListener(this);
            albumsFragment.setNothingToShowListener(this);

            return;
        }

        restoreState(savedInstanceState);

        if (!albumsMode) {
            rvMediaFragment = (RvMediaFragment) getSupportFragmentManager().findFragmentByTag(RvMediaFragment.TAG);
            rvMediaFragment.setListener(this);
            rvMediaFragment.setEditModeListener(this);
            rvMediaFragment.setNothingToShowListener(this);
        }

        albumsFragment = (AlbumsFragment) getSupportFragmentManager().findFragmentByTag(AlbumsFragment.TAG);
        albumsFragment.setListener(this);
        albumsFragment.setEditModeListener(this);
        albumsFragment.setNothingToShowListener(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SAVE_ALBUM_MODE, albumsMode);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(@NonNull Bundle savedInstance) {
        albumsMode = savedInstance.getBoolean(SAVE_ALBUM_MODE, true);
    }

    private void displayAlbums(boolean hidden) {
        albumsMode = true;
        navigationDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        albumsFragment.displayAlbums(hidden);
    }

    public void displayMedia(Album album) {
        rvMediaFragment = RvMediaFragment.make(album);

        albumsMode = false;
        navigationDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        rvMediaFragment.setListener(this);
        rvMediaFragment.setEditModeListener(this);
        rvMediaFragment.setNothingToShowListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, rvMediaFragment, RvMediaFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMediaClick(Album album, ArrayList<Media> media, int position) {
        if(!pickMode) {
            Intent intent = new Intent(getApplicationContext(), SingleMediaActivity.class);
            intent.putExtra(SingleMediaActivity.EXTRA_ARGS_ALBUM, album);
            try {
            intent.setAction(SingleMediaActivity.ACTION_OPEN_ALBUM);
            intent.putExtra(SingleMediaActivity.EXTRA_ARGS_MEDIA, media);
            intent.putExtra(SingleMediaActivity.EXTRA_ARGS_POSITION, position);
            startActivity(intent);
            } catch (Exception e) { // Putting too much data into the Bundle
            // TODO: Find a better way to pass data between the activities - possibly a key to
            // access a HashMap or a unique value of a singleton Data Repository of some sort.
            intent.setAction(SingleMediaActivity.ACTION_OPEN_ALBUM_LAZY);
            intent.putExtra(SingleMediaActivity.EXTRA_ARGS_MEDIA, media.get(position));
            startActivity(intent);
        }

    } else {

        Media m = media.get(position);
        Uri uri = LegacyCompatFileProvider.getUri(getApplicationContext(), m.getFile());
        Intent res = new Intent();
        res.setData(uri);
        res.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        setResult(RESULT_OK, res);
        finish();
        }
    }

    @Override
    public void changedNothingToShow(boolean nothingToShow) {
        enableNothingToSHowPlaceHolder(nothingToShow);
    }

    @Override
    public void changedEditMode(boolean editMode, int selected, int total, @javax.annotation.Nullable View.OnClickListener listener, @javax.annotation.Nullable String title) {
        if (editMode) {
            updateToolbar(
                    String.format(Locale.ENGLISH, "%d/%d", selected, total),
                    GoogleMaterial.Icon.gmd_check, listener);
        } else {
            if (albumsMode) resetToolbar();
            else updateToolbar(title, GoogleMaterial.Icon.gmd_arrow_back, v -> goBackToAlbums());
        }
    }

    public void goBackToAlbums() {
        albumsMode = true;
        navigationDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportFragmentManager().popBackStack();
    }

    private void initUi() {
        setSupportActionBar(toolbar);
        setupNavigationDrawer();
        setupFAB();
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, navigationDrawer,
                toolbar, R.string.drawer_open, R.string.drawer_close);

        navigationDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setupFAB() {
        fab.setImageDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_camera_alt).color(Color.WHITE));
        fab.setOnClickListener(v -> startActivity(new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)));
    }

    private void closeDrawer() {
        navigationDrawer.closeDrawer(GravityCompat.START);
    }

    private void askPassword() {

        Security.authenticateUser(MainActivity.this, new Security.AuthCallBack() {
            @Override
            public void onAuthenticated() {
                closeDrawer();
                selectNavigationItem(NAVIGATION_ITEM_HIDDEN_FOLDERS);
                displayAlbums(true);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        new Thread(() -> {
            if(Prefs.getLastVersionCode() < BuildConfig.VERSION_CODE) {
                String titleHtml = String.format(Locale.ENGLISH, "<font color='%d'>%s<b>%s</b></font>", getTextColor(), getString(R.string.changelog), BuildConfig.VERSION_NAME),
                        buttonHtml = String.format(Locale.ENGLISH, "<font color='%d'>%s</font>", getAccentColor(), getString(R.string.view).toUpperCase());
                Snackbar snackbar = Snackbar
                        .make(mainLayout, StringUtils.html(titleHtml), Snackbar.LENGTH_LONG)
                        .setAction(StringUtils.html(buttonHtml), view -> AlertDialogsHelper.showChangelogDialog(MainActivity.this));
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getBackgroundColor());
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    snackbarView.setElevation(getResources().getDimension(R.dimen.snackbar_elevation));
                snackbar.show();
                Prefs.setLastVersionCode(BuildConfig.VERSION_CODE);
            }
        }).start();
    }

    @Override
    public void updateUiElements() {
        super.updateUiElements();

        toolbar.setPopupTheme(getPopupToolbarStyle());
        toolbar.setBackgroundColor(getPrimaryColor());

        setStatusBarColor();
        setNavBarColor();

        fab.setBackgroundTintList(ColorStateList.valueOf(getAccentColor()));
        fab.setVisibility(Hawk.get(getString(R.string.preference_show_fab), false) ? View.VISIBLE : View.GONE);
        mainLayout.setBackgroundColor(getBackgroundColor());

        setScrollViewColor(navigationDrawerView);
        setAllScrollbarsColor();

        navigationDrawerView.setTheme(getPrimaryColor(), getBackgroundColor(), getTextColor(), getIconColor());
        // TODO setRecentApp()
        setRecentApp(getString(R.string.app_name));
    }


    private void setAllScrollbarsColor() {
        Drawable drawableScrollBar = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_scrollbar);
        drawableScrollBar.setColorFilter(new PorterDuffColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_ATOP));
    }

    public void updateToolbar(String title, IIcon icon, View.OnClickListener onClickListener) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getToolbarIcon(icon));
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    private void resetToolbar() {
        updateToolbar(
                getString(R.string.app_name),
                GoogleMaterial.Icon.gmd_menu,
                v -> navigationDrawer.openDrawer(GravityCompat.START));
    }

    public void enableNothingToSHowPlaceHolder(boolean status) {
        findViewById(R.id.nothing_to_show_placeholder).setVisibility(status ? View.VISIBLE : View.GONE);
    }

    @Deprecated
    public void checkNothing(boolean status) {
        ((TextView) findViewById(R.id.emoji_easter_egg)).setTextColor(getSubTextColor());
        ((TextView) findViewById(R.id.nothing_to_show_text_emoji_easter_egg)).setTextColor(getSubTextColor());

        if (status && Prefs.showEasterEgg()) {
            findViewById(R.id.ll_emoji_easter_egg).setVisibility(View.VISIBLE);
            findViewById(R.id.nothing_to_show_placeholder).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ll_emoji_easter_egg).setVisibility(View.GONE);
            findViewById(R.id.nothing_to_show_placeholder).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.settings:
                SettingsActivity.startActivity(this);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (albumsMode) {
            if (!albumsFragment.onBackPressed()) {
                if (navigationDrawer.isDrawerOpen(GravityCompat.START))
                    closeDrawer();
                else finish();
            }
        } else {
            if (!((BaseFragment) getSupportFragmentManager().findFragmentByTag(RvMediaFragment.TAG)).onBackPressed())
                goBackToAlbums();
        }
    }

    @Override
    public void onAlbumClick(Album album) {
        displayMedia(album);
    }

    public void onItemSelected(@NavigationItem int navigationItemSelected) {
        closeDrawer();
        switch (navigationItemSelected) {

            case NAVIGATION_ITEM_ALL_ALBUMS:
                displayAlbums(false);
                selectNavigationItem(navigationItemSelected);
                break;

            case NAVIGATION_ITEM_ALL_MEDIA:
                displayMedia(Album.getAllMediaAlbum());
                break;

            case NAVIGATION_ITEM_HIDDEN_FOLDERS:
                if (Security.isPasswordOnHidden()) {
                    askPassword();
                } else {
                    selectNavigationItem(navigationItemSelected);
                    displayAlbums(true);
                }
                break;

            case NAVIGATION_ITEM_WALLPAPERS:
                Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;

            case NAVIGATION_ITEM_DONATE:
                //todo
//                DonateActivity.startActivity(this);
                break;

            case NAVIGATION_ITEM_SETTINGS:
                SettingsActivity.startActivity(this);
                break;

            case NAVIGATION_ITEM_ABOUT:
                //todo
//                AboutActivity.startActivity(this);
                break;
        }
    }

    private void selectNavigationItem(@NavigationItem int navItem) {
        navigationDrawerView.selectNavItem(navItem);
    }
}
