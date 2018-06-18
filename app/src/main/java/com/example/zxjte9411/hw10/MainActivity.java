package com.example.zxjte9411.hw10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Debug";
    public static final String DB_FILE = "contact.db", DB_TABLE = "contact";
    public static SQLiteDatabase sdb;
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private AddNewContact addNewContact;
    private SearchContact searchContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionPageAdapter);
        mTableLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Close keyboard when user click TabLayout
                InputMethodManager inputMethodManager = ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE));
                if(inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(MainActivity.this.getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                //inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);//
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        addNewContact = new AddNewContact();
        searchContact = new SearchContact();

        DBOpenHelper contactDBOpenHelper = new DBOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        sdb = contactDBOpenHelper.getWritableDatabase();


        Cursor cursor = sdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'"
                , null);

        if(cursor != null) {
            if(cursor.getCount() == 0)	// DB Table not exist, therefore to construct a new one.
                sdb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "phoneNumber TEXT," +
                        "phoneType TEXT);");

            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu, menu);
        android.widget.SearchView searchView = (android.widget.SearchView) menu.findItem(R.id.itemSearch).getActionView();
        searchView.setOnQueryTextListener(searchView_OnQuery);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        if (item.getItemId() == R.id.add_new_contact) {
            if(mViewPager.getCurrentItem()  !=0){
                mViewPager.setCurrentItem(0);
                return true;
            }
            ContentValues data = addNewContact.getContentValues();
            sdb.insert(DB_TABLE, null, data);
            searchContact.addDataToList(
                    "Name: " + data.getAsString("name") + "\n" +
                            "PhoneNumber: " + data.getAsString("phoneNumber") + "\n" +
                            "PhoneType: " + data.getAsString("phoneType"));

            Toast.makeText(this, "資料已成功加", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }


    private final SearchView.OnQueryTextListener searchView_OnQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if(mViewPager.getCurrentItem() != 1){
                mViewPager.setCurrentItem(1);
            }
            Cursor cursor = null;
            if (!query.equals("")) {
                cursor = sdb.query(true,
                        DB_TABLE,
                        new String[]{"name", "phoneNumber", "phoneType"},
                        "name=" + "\"" + query + "\"",
                        null, null, null, null, null);
            }

            if (cursor == null)
                return false;

            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "找不到目標資料！", Toast.LENGTH_LONG).show();
                searchContact.setListHighlight();
            }
            else {
                ArrayList<String> dataList = new ArrayList<>();
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    dataList.add(
                            "Name: " + cursor.getString(0) + "\n" +
                                    "PhoneNumber: " + cursor.getString(1) + "\n" +
                                    "PhoneType: " + cursor.getString(2));
                    cursor.moveToNext();
                }
                searchContact.setListHighlight(dataList);
            }

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) { return false; }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdb.close();
    }

    public class SectionPageAdapter extends FragmentPagerAdapter {
        public SectionPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = addNewContact;
                    break;
                case 1:
                    fragment = searchContact;
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "AddNewContact";
                case 1:
                    return "SearchContact";
                default:
                    return null;
            }
        }

    }
}
