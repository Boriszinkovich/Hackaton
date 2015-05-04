package com.example.home.hackathon;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.home.hackathon.api.Clothing;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.util.Arrays.asList;


public class SecondActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Button button = (Button)findViewById(R.id.main_price);

        final ArrayList<Clothing> boughtClothings = new ArrayList<>();

        boughtClothings.addAll(asList(new Gson().fromJson(getIntent().getStringExtra("prise_1"), Clothing[].class)));
        boughtClothings.addAll(asList(new Gson().fromJson(getIntent().getStringExtra("prise_2"), Clothing[].class)));
        boughtClothings.addAll(asList(new Gson().fromJson(getIntent().getStringExtra("prise_3"), Clothing[].class)));

        ArrayAdapter<?> adapter = new ArrayAdapter<Clothing>(this, android.R.layout.simple_list_item_1, boughtClothings) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.part_bought_clothing,  parent, false);

                Clothing clothing = getItem(position);

                Picasso.with(parent.getContext())
                        .load(clothing.image)
                        .fit()
                        .centerInside()
                        .into(((ImageView) view.findViewById(R.id.bought_clothing_image)));

                ((TextView) view.findViewById(R.id.bought_clothing_name))
                        .setText(clothing.name);

                ((TextView) view.findViewById(R.id.bought_clothing_price))
                        .setText(((int) clothing.price) + " ₴");
                return view;
            }
        };
        int totalPrice = 0;
        for (Clothing boughtClothing : boughtClothings) {
            totalPrice += boughtClothing.price;
        }
        button.setText("" + totalPrice);

        ((ListView) findViewById(R.id.list_item)).setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
