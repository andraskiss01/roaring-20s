package com.ak17apps.roaring20s.layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ak17apps.roaring20s.BuildConfig;
import com.ak17apps.roaring20s.R;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.util.DataHandler;
import com.ak17apps.roaring20s.util.Type;

import java.util.ArrayList;
import java.util.List;

public class ManufactureStorageSaleActivity extends VerticalListViewActivity {
    SharedPreferences shPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shPref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        sortByLayoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByLayoutLL.setBackgroundResource(R.drawable.main_button_pushed);

                List<String> sortByList = new ArrayList<>();
                sortByList.add(SortBy.TYPE_ASC.name());
                sortByList.add(SortBy.TYPE_DESC.name());
                sortByList.add(SortBy.NAME_ASC.name());
                sortByList.add(SortBy.NAME_DESC.name());
                sortByList.add(SortBy.LOAD_ASC.name());
                sortByList.add(SortBy.LOAD_DESC.name());
                sortByList.add(SortBy.CAPACITY_ASC.name());
                sortByList.add(SortBy.CAPACITY_DESC.name());
                sortByList.add(SortBy.BUYPRICE_ASC.name());
                sortByList.add(SortBy.BUYPRICE_DESC.name());
                sortByList.add(SortBy.SELLPRICE_ASC.name());
                sortByList.add(SortBy.SELLPRICE_DESC.name());
                sortByList.add(SortBy.SALE_ASC.name());
                sortByList.add(SortBy.SALE_DESC.name());
                showSortWindow(sortByList);
            }
        });

        sortBy = DataHandler.getManufactureStorageSaleSortBy(shPref);
        updateSortByTV();

        titleTV.setText(getResources().getString(R.string.manufacture_and_storage_and_sale));
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        backButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(ManufactureStorageSaleActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        loadEntryList(sortBy);
    }

    @Override
    protected void loadEntryList(String sortBy) {
        int narrowBtnWidth = (int) (screenWidth * 0.09);
        int wideBtnWidth = (int) (screenWidth * 0.11);
        int veryWideBtnWidth = (int) (screenWidth * 0.14);

        clearList();
        List<Place> placeList = DataHandler.getManufactureStorageSaleList(sortBy, getApplicationContext());
        List<Person> personList = DataHandler.getPersonList(getApplicationContext());

        for(Place p : placeList){
            ListItem item = new ListItem(this);
            String status = "";
            String crew = "-";
            String completion = "-";
            String sale = "-";

            final TextView selectCrewBtn = getButton("Select", wideBtnWidth);
            selectCrewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCrewBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            final TextView sellBuyBtn = getButton(p.isOwned() ? "Sell" : "Buy", narrowBtnWidth);
            sellBuyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sellBuyBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            final TextView materialManagementBtn = getButton("Materials", veryWideBtnWidth);
            materialManagementBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialManagementBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            if(p.isOwned()){
                status = "Owned";
                for(Person prs : personList){
                    if(prs.getPlace() != null && p.getId() == prs.getPlace().getId()){
                        crew = prs.getName();
                    }
                }
            }else{
                status = "For sale";
            }

            item.setTopRowItems(getIndicator(p.getType().substring(p.getType().indexOf("_") + 1).replace("_", " ")), getIndicator(p.getName()), getIndicator("Load: " + p.getLoad() + "/" + p.getCapacity() + " gal"), getIndicator("Status: " + status));
            item.setMiddleRowItems(getIndicator("Buy price: $" + p.getBuyPrice()), getIndicator("Sell price: $" + p.getSellPrice()));

            if(p.getType().equals(Type.PLACE_HOUSE)) {
                item.setBottomRowItems(sellBuyBtn, getIndicator("Completion: " + (p.getCompletion() != null ? p.getCompletion() : completion)), getIndicator("Sale : " + (p.getSale() != null ? p.getSale() : sale)), materialManagementBtn);
            }else{
                if(p.getType().equals(Type.PLACE_BIG_PUB) || p.getType().equals(Type.PLACE_MEDIUM_PUB) || p.getType().equals(Type.PLACE_SMALL_PUB)){
                    item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + crew), selectCrewBtn, getIndicator("Sale: " + (p.getSale() != null ? p.getSale() : sale)), materialManagementBtn);
                }else {
                    item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + crew), selectCrewBtn, materialManagementBtn);
                }
            }

            addListItem(item);
        }
    }

    private TextView getIndicator(String str){
        int left = screenWidth / 64;
        int top = screenHeight / 108;
        int right = screenWidth / 96;
        int bottom = screenHeight / 108;

        TextView tv = new TextView(this);
        tv.setText(str);
        tv.setTextColor(getResources().getColor(R.color.nicotine));

        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        par.setMargins(left, top, right, bottom);
        tv.setLayoutParams(par);

        return tv;
    }

    private TextView getButton(String str, int width){
        int top = screenHeight / 108;
        int leftRightBottom = screenWidth / 96;

        final TextView btnTV = new TextView(this);
        btnTV.setText(str);
        btnTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        btnTV.setTextColor(getResources().getColor(R.color.nicotine));
        btnTV.setWidth(width);
        btnTV.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        par.setMargins(leftRightBottom, top, leftRightBottom, leftRightBottom);
        btnTV.setLayoutParams(par);

        return btnTV;
    }
}
