package com.ak17apps.roaring20s.layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ak17apps.roaring20s.BuildConfig;
import com.ak17apps.roaring20s.R;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.ak17apps.roaring20s.util.DataHandler;
import com.ak17apps.roaring20s.util.Type;

import java.util.ArrayList;
import java.util.List;

public class OnWayActivity extends VerticalListViewActivity {
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
                sortByList.add(SortBy.SPEED_ASC.name());
                sortByList.add(SortBy.SPEED_DESC.name());
                sortByList.add(SortBy.CONSUMPTION_ASC.name());
                sortByList.add(SortBy.CONSUMPTION_DESC.name());
                sortByList.add(SortBy.ARRIVAL_ASC.name());
                sortByList.add(SortBy.ARRIVAL_DESC.name());
                sortByList.add(SortBy.TYPE_ASC.name());
                sortByList.add(SortBy.TYPE_DESC.name());
                sortByList.add(SortBy.BUYPRICE_ASC.name());
                sortByList.add(SortBy.BUYPRICE_DESC.name());
                sortByList.add(SortBy.SELLPRICE_ASC.name());
                sortByList.add(SortBy.SELLPRICE_DESC.name());
                sortByList.add(SortBy.CAPACITY_ASC.name());
                sortByList.add(SortBy.CAPACITY_DESC.name());
                sortByList.add(SortBy.LOAD_ASC.name());
                sortByList.add(SortBy.LOAD_DESC.name());
                sortByList.add(SortBy.NAME_ASC.name());
                sortByList.add(SortBy.NAME_DESC.name());
                showSortWindow(sortByList);
            }
        });

        sortBy = DataHandler.getVehicleListSortBy(shPref);
        updateSortByTV();

        titleTV.setText(getResources().getString(R.string.on_way));

        backButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(OnWayActivity.this, MainActivity.class);
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

        clearList();
        List<Vehicle> vehicleList = DataHandler.getVehicleList(sortBy, getApplicationContext());
        List<Person> personList = DataHandler.getPersonList(getApplicationContext());

        for(Vehicle v : vehicleList){
            if(!v.isOwned() || v.getArrival() == null){
                continue;
            }

            ListItem item = new ListItem(this);

            String driver = "-";
            String stoker = "-";
            String consUnit = "";

            if(v.getType().equals(Type.VEHICLE_SHIP_SMALL) || v.getType().equals(Type.VEHICLE_SHIP_BIG)
                    || v.getType().equals(Type.VEHICLE_TRAIN_SMALL) || v.getType().equals(Type.VEHICLE_TRAIN_BIG)){
                for(Person p : personList) {
                    if(p.getVehicle() != null && p.getVehicle().getId() == v.getId()) {
                        if ((p.getType().equals(Type.PERSON_SAILOR) || p.getType().equals(Type.PERSON_TRAIN_DRIVER))) {
                            driver = p.getName();
                        }
                        if(p.getType().equals(Type.PERSON_STOKER)){
                            stoker = p.getName();
                        }
                    }
                }
            }else {
                for(Person p : personList) {
                    if (p.getVehicle() != null && p.getVehicle().getId() == v.getId()) {
                        driver = p.getName();
                    }
                }
            }

            if(v.getType().equals(Type.VEHICLE_SHIP_SMALL) || v.getType().equals(Type.VEHICLE_SHIP_BIG)
                    || v.getType().equals(Type.VEHICLE_TRAIN_SMALL) || v.getType().equals(Type.VEHICLE_TRAIN_BIG)){
                consUnit = "lbs/100m";
            }else{
                consUnit = "gal/100m";
            }

            item.setTopRowItems(getIndicator(v.getType().substring(v.getType().indexOf("_") + 1).replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal"));
            item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));

            if(v.getType().equals(Type.VEHICLE_CAR)){
                item.setBottomRowItems(getIndicator("Arrival: " + (v.getArrival() != null ? v.getArrival() : "-")));
            }else {
                item.setBottomRowItems(getIndicator("Crew: " + (v.getType().equals(Type.VEHICLE_SHIP_SMALL) || v.getType().equals(Type.VEHICLE_SHIP_BIG) || v.getType().equals(Type.VEHICLE_TRAIN_SMALL) || v.getType().equals(Type.VEHICLE_TRAIN_BIG) ? driver + ", " + stoker : driver)), getIndicator("Arrival: " + v.getArrival()));
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
