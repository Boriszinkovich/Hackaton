package com.example.home.hackathon;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.home.hackathon.api.Clothing;
import com.example.home.hackathon.api.ServerApi;
import com.google.gson.Gson;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private final static Random RANDOM = new Random();
    private final RestAdapter mRestAdapter = new RestAdapter.Builder()
            .setEndpoint("http://192.168.1.102:8000/")
            .build();
    private final ServerApi mServerApi = mRestAdapter.create(ServerApi.class);

    private ViewPager mShoesPager;
    private ViewPager mShirtsPager;
    private ViewPager mTrousersPager;
    private ViewPagerAdapter mShirtesAdapter;
    private ViewPagerAdapter mShirtesAdapter_2;
    private ViewPagerAdapter mShirtesAdapter_1;


    private static void toNext(ViewPager pager) {
        pager.setCurrentItem(nextRandomNotCheckedItemPosition(pager), true);
    }

    private static int nextRandomNotCheckedItemPosition(ViewPager pager) {
        int nextRandomItem;
        do {
            nextRandomItem = RANDOM.nextInt(pager.getAdapter().getCount());
        } while (((ViewPagerAdapter) pager.getAdapter()).isItemSelected(nextRandomItem));
        return nextRandomItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServerApi.listRepos(new Callback<List<Clothing>>() {
            @Override
            public void success(List<Clothing> clothings, Response response) {
                List<Clothing> trousers = new ArrayList<>();
                List<Clothing> shoes = new ArrayList<>();
                List<Clothing> shirts = new ArrayList<>();

                for (Clothing clothing : clothings) {
                    switch (clothing.category) {
                        case TROUSERS:
                            trousers.add(clothing);
                            break;
                        case SHIRT:
                            shirts.add(clothing);
                            break;
                        case SHOES:
                            shoes.add(clothing);
                            break;
                    }
                }

                mShirtesAdapter = new ViewPagerAdapter(shirts);
                mShirtsPager.setAdapter(mShirtesAdapter);
                mShirtsPager.setCurrentItem(0);

                mShirtesAdapter_1 = new ViewPagerAdapter(trousers);
                mTrousersPager.setAdapter(mShirtesAdapter_1);
                mTrousersPager.setCurrentItem(0);

                mShirtesAdapter_2 = new ViewPagerAdapter(shoes);
                mShoesPager.setAdapter(mShirtesAdapter_2);
                mShoesPager.setClipChildren(false);
                mShoesPager.setPageMargin(16);
                LinearLayout layout = (LinearLayout)findViewById(R.id.ddd);
                layout.setClipChildren(false);
                mShoesPager.setCurrentItem(0);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        mShirtsPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        mTrousersPager = (ViewPager) findViewById(R.id.myfivepanelpager_2);
        mShoesPager = (ViewPager) findViewById(R.id.myfivepanelpager_3);

        findViewById(R.id.main_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo start new activity

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("prise_1",new Gson().toJson(mShirtesAdapter.getSelectedItems()));
                intent.putExtra("prise_2",new Gson().toJson(mShirtesAdapter_1.getSelectedItems()));
                intent.putExtra("prise_3",new Gson().toJson(mShirtesAdapter_2.getSelectedItems()));

                startActivity(intent);
            }
        });


        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(new ShakeDetector.Listener() {
            @Override
            public void hearShake() {
                viewPagersToNext();
            }
        });
        sd.start(sensorManager);
    }

    private void viewPagersToNext() {
        toNext(mShirtsPager);
        toNext(mShoesPager);
        toNext(mTrousersPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            viewPagersToNext();
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        menu.add("Shirts");
//        menu.add("Jeans");
//        menu.add("Shoes");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.checkable_item_1:
            case R.id.checkable_item_2:
            case R.id.checkable_item_3:
                item.setChecked(!item.isChecked());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
