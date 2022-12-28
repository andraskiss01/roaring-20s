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
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.ak17apps.roaring20s.util.DataHandler;
import com.ak17apps.roaring20s.util.Type;

import java.util.List;

public class PortsStationsActivity extends VerticalListViewActivity {
    SharedPreferences shPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shPref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        titleTV.setText(getResources().getString(R.string.ports_and_stations));
        sortByLayoutLL.setVisibility(View.INVISIBLE);

        backButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(PortsStationsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        loadEntryList(null);
    }

    @Override
    protected void loadEntryList(String sortBy) {
        int narrowBtnWidth = (int) (screenWidth * 0.09);
        int ships = 0;
        int trains = 0;
        int planes = 0;

        clearList();
        List<Place> postsStationsList = DataHandler.getPortsStationsList(getApplicationContext());
        List<Vehicle> vehicles = DataHandler.getVehicleList(getApplicationContext());

        for(Vehicle v : vehicles){
            if(v.isOwned() && (v.getType().equals(Type.VEHICLE_SHIP_BIG) || v.getType().equals(Type.VEHICLE_SHIP_SMALL))){
                ships++;
            }
            if(v.isOwned() && (v.getType().equals(Type.VEHICLE_TRAIN_BIG) || v.getType().equals(Type.VEHICLE_TRAIN_SMALL))){
                trains++;
            }
            if(v.isOwned() && (v.getType().equals(Type.VEHICLE_PLANE_BIG) || v.getType().equals(Type.VEHICLE_PLANE_SMALL))){
                planes++;
            }
        }

        for(Place p : postsStationsList){
            ListItem item = new ListItem(this);
            String status;

            final TextView sellBuyBtn = getButton(p.isOwned() ? "Sell" : "Buy", narrowBtnWidth);
            sellBuyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sellBuyBtn.setBackgroundResource(R.drawable.main_button_pushed);
                }
            });

            if(p.isOwned()){
                status = "Owned";
            }else{
                status = "For sale";
            }

            item.setTopRowItems(getIndicator(p.getType().substring(p.getType().indexOf("_") + 1).replace("_", " ")), getIndicator("Status: " + status));
            item.setMiddleRowItems(getIndicator("Buy price: $" + p.getBuyPrice()), getIndicator("Sell price: $" + p.getSellPrice()));

            if(p.getType().equals(Type.PLACE_PORT)) {
                item.setBottomRowItems(sellBuyBtn, getIndicator("Ships: " + ships));
            }
            if(p.getType().equals(Type.PLACE_AIRPORT)){
                item.setBottomRowItems(sellBuyBtn, getIndicator("Planes: " + planes));
            }
            if(p.getType().equals(Type.PLACE_RAILWAY_STATION)){
                item.setBottomRowItems(sellBuyBtn, getIndicator("Trains: " + trains));
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
