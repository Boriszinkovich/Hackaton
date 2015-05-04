package com.example.home.hackathon;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.hackathon.api.Clothing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<Clothing> mClothings;
    boolean[] mCheckedItems;

    public ViewPagerAdapter(List<Clothing> clothings) {
        mClothings = clothings;
        mCheckedItems = new boolean[clothings.size()];
    }

    public int getCount() {
        return mClothings.size();
    }

    public Object instantiateItem(View parent, final int position) {
        Clothing clothing = mClothings.get(position);
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_clothing, (ViewGroup) parent, false);
        Picasso.with(parent.getContext())
                .load(clothing.image)
                .fit()
                .centerInside()
                .into(((ImageView) view.findViewById(R.id.clothing_image)));
        ((TextView) view.findViewById(R.id.clothing_price))
                .setText(((int) clothing.price) + " ₴");
        ((ViewPager) parent).addView(view, 0);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setChecked(mCheckedItems[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!mCheckedItems[position]);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckedItems[position] = !mCheckedItems[position];
            }
        });
        return view;

    }


    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public ArrayList<Clothing> getSelectedItems() {
        ArrayList<Clothing> selectedItems = new ArrayList<>();
        for (int i = 0; i < mCheckedItems.length; i++) {
            if (mCheckedItems[i])
                selectedItems.add(mClothings.get(i));
        }
        return selectedItems;
    }

    public boolean isItemSelected(int position) {
        return mCheckedItems[position];
    }

}
