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
import com.ak17apps.roaring20s.entity.BuyProduce;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.ak17apps.roaring20s.util.DataHandler;
import com.ak17apps.roaring20s.util.Type;

public class BuyProduceActivity extends VerticalListViewActivity {
    SharedPreferences shPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shPref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        sortBy = DataHandler.getBuyProduceSortBy(shPref);
        updateSortByTV();

        titleTV.setText(getResources().getString(R.string.buy_produce));
        sortByLayoutLL.setVisibility(View.INVISIBLE);

        backButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(BuyProduceActivity.this, MainActivity.class);
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
        BuyProduce buyProduce = DataHandler.getBuyProduceList(shPref);

        for (Place p: buyProduce.getPlaceList()) {
            if(p.getType().equals(Type.PLACE_HOUSE) && p.isOwned()){
                ListItem item = new ListItem(this);
                String completion = "-";
                String sale = "-";

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

                item.setTopRowItems(getIndicator(p.getType().substring(p.getType().indexOf("_") + 1).replace("_", " ")), getIndicator(p.getName()), getIndicator("Load: " + p.getLoad() + "/" + p.getCapacity() + " gal"));
                item.setMiddleRowItems(getIndicator("Buy price: $" + p.getBuyPrice()), getIndicator("Sell price: $" + p.getSellPrice()));
                item.setBottomRowItems(sellBuyBtn, getIndicator("Completion: " + (p.getCompletion() != null ? p.getCompletion() : completion)), getIndicator("Sale : " + (p.getSale() != null ? p.getSale() : sale)), materialManagementBtn);
                addListItem(item);
                break;
            }
        }

        for(Vehicle v : buyProduce.getVehicleList()){
            if(!v.isOwned() || v.getArrival() != null) {
                continue;
            }

            ListItem item = new ListItem(this);
            String consUnit = "";

            final TextView sellBuyBtn = getButton(v.isOwned() ? "Sell" : "Buy", narrowBtnWidth);
            sellBuyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sellBuyBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            final TextView goBtn = getButton("GO!", narrowBtnWidth);
            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            final TextView selectCrewBtn = getButton("Select", wideBtnWidth);
            selectCrewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCrewBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            if(v.getType().equals(Type.VEHICLE_SHIP_SMALL) || v.getType().equals(Type.VEHICLE_SHIP_BIG)
                    || v.getType().equals(Type.VEHICLE_TRAIN_SMALL) || v.getType().equals(Type.VEHICLE_TRAIN_BIG)){
                consUnit = "lbs/100m";
            }else{
                consUnit = "gal/100m";
            }

            if(v.getType().equals(Type.VEHICLE_CAR)){  //no crew, no station
                item.setTopRowItems(getIndicator(v.getType().replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal")/*, getIndicator("Status: " + status)*/);
                item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));
                item.setBottomRowItems(sellBuyBtn, goBtn);
            }else if(v.getType().equals(Type.VEHICLE_LORRY_BIG) || v.getType().equals(Type.VEHICLE_LORRY_SMALL) ||
                    v.getType().equals(Type.VEHICLE_TRUCK_BIG) || v.getType().equals(Type.VEHICLE_TRUCK_SMALL)) {     //driver, no station
                for(Person p : buyProduce.getPersonList()){
                    if(p.getVehicle().getId() == v.getId()){
                        item.setTopRowItems(getIndicator(v.getType().replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal")/*, getIndicator("Status: " + status)*/);
                        item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));
                        item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + p.getName()), selectCrewBtn, goBtn);

                        break;
                    }
                }
            }else if(v.getType().equals(Type.VEHICLE_PLANE_BIG) || v.getType().equals(Type.VEHICLE_PLANE_SMALL)){ //pilot, airport
                boolean airport = false;
                String pilotName = null;

                for (Place p : buyProduce.getPlaceList()){
                    if(p.getType().equals(Type.PLACE_AIRPORT) && p.isOwned()){
                        airport = true;
                        break;
                    }
                }

                for(Person p : buyProduce.getPersonList()){
                    if(p.getVehicle().getId() == v.getId()){
                        pilotName = p.getName();
                        break;
                    }
                }

                if(airport && pilotName != null){
                    item.setTopRowItems(getIndicator(v.getType().replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal")/*, getIndicator("Status: " + status)*/);
                    item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));
                    item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + pilotName), selectCrewBtn, goBtn);
                }
            }else if(v.getType().equals(Type.VEHICLE_SHIP_SMALL) || v.getType().equals(Type.VEHICLE_SHIP_BIG)){ //sailor, stoker, port
                String sailor = null;
                String stoker = null;
                boolean port = false;

                for(Person p : buyProduce.getPersonList()){
                    if(sailor == null && p.getType().equals(Type.PERSON_SAILOR) && p.getVehicle().getId() == v.getId()){
                        sailor = p.getName();
                    }
                    if(stoker == null && p.getType().equals(Type.PERSON_STOKER) && p.getVehicle().getId() == v.getId()){
                        stoker = p.getName();
                    }
                    if(sailor != null && stoker != null){
                        break;
                    }
                }

                for(Place p : buyProduce.getPlaceList()){
                    if(p.getType().equals(Type.PLACE_PORT) && p.isOwned()){
                        port = true;
                        break;
                    }
                }

                if(sailor != null && stoker != null && port){
                    item.setTopRowItems(getIndicator(v.getType().replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal")/*, getIndicator("Status: " + status)*/);
                    item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));
                    item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + sailor + ", " + stoker), selectCrewBtn, goBtn);
                }
            }else if(v.getType().equals(Type.VEHICLE_TRAIN_SMALL) || v.getType().equals(Type.VEHICLE_TRAIN_BIG)){ //train driver, stoker, railway station
                String trainDriver = null;
                String stoker = null;
                boolean railwayStation = false;

                for(Person p : buyProduce.getPersonList()){
                    if(trainDriver == null && p.getType().equals(Type.PERSON_TRAIN_DRIVER) && p.getVehicle().getId() == v.getId()){
                        trainDriver = p.getName();
                    }
                    if(stoker == null && p.getType().equals(Type.PERSON_STOKER) && p.getVehicle().getId() == v.getId()){
                        stoker = p.getName();
                    }
                    if(trainDriver != null && stoker != null){
                        break;
                    }
                }

                for(Place p : buyProduce.getPlaceList()){
                    if(p.getType().equals(Type.PLACE_RAILWAY_STATION) && p.isOwned()){
                        railwayStation = true;
                        break;
                    }
                }

                if(trainDriver != null && stoker != null && railwayStation){
                    item.setTopRowItems(getIndicator(v.getType().replace("_", " ")), getIndicator(v.getName()), getIndicator("Speed: " + v.getSpeed() + " m/h"), getIndicator("Cons.: " + v.getConsumption() + " " + consUnit), getIndicator("Load: " + v.getLoad() + "/" + v.getCapacity() + " gal")/*, getIndicator("Status: " + status)*/);
                    item.setMiddleRowItems(getIndicator("Buy price: $" + v.getBuyPrice()), getIndicator("Sell price: $" + v.getSellPrice()));
                    item.setBottomRowItems(sellBuyBtn, getIndicator("Crew: " + trainDriver + ", " + stoker), selectCrewBtn, goBtn);
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
