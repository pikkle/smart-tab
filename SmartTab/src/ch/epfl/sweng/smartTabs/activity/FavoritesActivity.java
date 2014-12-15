package ch.epfl.sweng.smartTabs.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * Implementation of the "Favorites" sliding menu
 * 
 */
public class FavoritesActivity extends Activity {

    private static final String PREFS_NAME = "MyPrefsFile";

    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences mSharedPrefs;
    private ArrayAdapter<String> mAdapter;
    private Map<String, ?> mFavs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initDrawerLayout();

        mListView = (ListView) findViewById(R.id.fav_list);

        mSharedPrefs = getSharedPreferences(PREFS_NAME, 0);

        refresh();

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tabName = (String) mListView.getAdapter().getItem(position);
                System.out.println(tabName);
                String tabSerial = mSharedPrefs.getString(tabName, "Error");
                Intent i = new Intent(FavoritesActivity.this, DisplayActivity.class);
                i.putExtra("tab", deserialize(tabSerial));
                startActivity(i);
            }

        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.fav_action_drawer:
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        mFavs = mSharedPrefs.getAll();
        mAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_layout);

        for (Map.Entry<String, ?> entry : mFavs.entrySet()) {
            mAdapter.add(entry.getKey());
        }

        mListView.setAdapter(mAdapter);
    }

    public Tab deserialize(String s) {
        try {
            byte[] b = Base64.decode(s.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return (Tab) si.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // a changer
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.fav_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.fav_right_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        String[] items = { "All Tabs", "Favorites", "Settings" };

        mAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_layout, items);

        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /**
     * Listener for the button showing the favs menu
     * 
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fav_activity_action_bar, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public void selectItem(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(FavoritesActivity.this, MainActivity.class));
                break;
            case 1:
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 2:
                startActivity(new Intent(FavoritesActivity.this, PreferencesActivity.class));
                break;
            default:
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
        }
    }

}
