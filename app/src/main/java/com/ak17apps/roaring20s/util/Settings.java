package com.ak17apps.roaring20s.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ak17apps.roaring20s.R;
import com.ak17apps.roaring20s.entity.BuyProduce;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Property;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Settings{
    public static final String FONT_TYPE_PARKLANE_REGULAR = "parklane_regular.ttf";
    public static final String FONT_TYPE_MARLBORO = "marlboro.ttf";
    public static final String FONT_TYPE_POPULARCAFEAA_REGULAR = "popularcafeaa_regular.ttf";
    private static final double kgToPound = 2.20462;
    private static final double literToGallon = 0.264172;
    private static final double grainMin = 12 / kgToPound;
    private static final double grainMax = 21 / kgToPound;
    private static final double gasolineMin = 1 / literToGallon;
    private static final double gasolineMax = 11 / literToGallon;
    private static final double coalMin = 4 / kgToPound;
    private static final double coalMax = 13 / kgToPound;
    private static final double whiskeyBuyMin = 4 / literToGallon;
    private static final double whiskeyBuyMax = 13 / literToGallon;
    private static final double whiskeySellMin = 15 / literToGallon;
    private static final double whiskeySellMax = 24 / literToGallon;
    public static final int DISTANCE = 600; //miles

    public static final String[] names = {"James", "Robert", "John", "Michael", "William", "David",
        "Richard", "Joseph", "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony",
        "Mark", "Donald", "Steven", "Paul", "Andrew", "Joshua", "Kenneth", "Kevin", "Brian", "George",
        "Edward", "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan", "Jacob", "Gary", "Nicholas",
        "Eric", "Jonathan", "Stephen", "Larry", "Justin", "Scott", "Brandon", "Benjamin", "Samuel",
        "Gregory", "Frank", "Alexander", "Raymond", "Patrick", "Jack", "Dennis", "Jerry", "Tyler",
        "Aaron", "Jose", "Adam", "Henry", "Nathan", "Douglas", "Zachary", "Peter", "Kyle"};
    public static final String PERSONNEL_KEY = "PERSONNEL_KEY";
    public static final String FLEET_KEY = "FLEET_KEY";
    public static final String PLACES_KEY = "PLACES_KEY";
    public static final String MONEY_KEY = "MONEY_KEY";
    public static final String PERSONNEL_SORT_BY_KEY = "PERSONNEL_SORT_BY_KEY";
    public static final String FLEET_SORT_BY_KEY = "FLEET_SORT_BY_KEY";
    public static final String MANUFACTURE_STORAGE_SALE_SORT_BY_KEY = "MANUFACTURE_STORAGE_SALE_SORT_BY_KEY";
    public static final String BUY_PRODUCE_SORT_BY_KEY = "BUY_PRODUCE_SORT_BY_KEY";
    public static final String ON_WAY_SORT_BY_KEY = "ON_WAY_SORT_BY_KEY";
    public static final String SELL_SORT_BY_KEY = "SELL_SORT_BY_KEY";
    public static final String FIRST_RUN = "FIRST_RUN";

    public static double getGrainPrice(){
        Calendar c = Calendar.getInstance();
        return grainMin + (grainMax - grainMin) / 10 * ((c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) * 2 + c.get(Calendar.HOUR_OF_DAY) * 3) % 10);
    }

    public static double getGasolinePrice(){
        Calendar c = Calendar.getInstance();
        return gasolineMin + (gasolineMax - gasolineMin) / 10 * ((c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) * 3 + c.get(Calendar.HOUR_OF_DAY) * 7) % 10);
    }

    public static double getCoalPrice(){
        Calendar c = Calendar.getInstance();
        return coalMin + (coalMax - coalMin) / 10 * ((c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) * 11 + c.get(Calendar.HOUR_OF_DAY) * 13) % 10);
    }

    public static double getWhiskeyBuyPrice(){
        Calendar c = Calendar.getInstance();
        return whiskeyBuyMin + (whiskeyBuyMax - whiskeyBuyMin) / 10 * ((c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) * 13 + c.get(Calendar.HOUR_OF_DAY) * 13) % 10);
    }

    public static double getWhiskeySellPrice(){
        Calendar c = Calendar.getInstance();
        return whiskeySellMin + (whiskeySellMax - whiskeySellMin) / 10 * ((c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY) * 7) % 10);
    }

    public static List<String> getNames(int amount){
        if(amount >= names.length){
            return Arrays.asList(names);
        }

        Random rnd = new Random();
        List<String> selectedNames = new ArrayList<>();

        while(selectedNames.size() < amount) {
            int rndNum = rnd.nextInt(names.length);
            boolean found = false;
            for (String name : selectedNames) {
                if(name.equals(names[rndNum])){
                    found = true;
                    break;
                }
            }
            if(!found){
                selectedNames.add(names[rndNum]);
            }
        }

        return selectedNames;
    }

    public static String getString(String key, SharedPreferences shPref){
        return shPref.getString(key, null);
    }

    public static List<Person> getPersonList(SharedPreferences shPref){
        List<Person> arrayItems = Collections.emptyList();
        String serializedObject = shPref.getString(PERSONNEL_KEY, null);

        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Person>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public static BuyProduce getBuyProduce(SharedPreferences shPref){
        List<Place> placeItems = Collections.emptyList();
        List<Vehicle> vehicleItems = Collections.emptyList();
        List<Person> personItems = Collections.emptyList();
        String serializedPlaceObject = shPref.getString(PLACES_KEY, null);
        String serializedVehicleObject = shPref.getString(FLEET_KEY, null);
        String serializedPersonObject = shPref.getString(PERSONNEL_KEY, null);

        if (serializedPlaceObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Place>>(){}.getType();
            placeItems = gson.fromJson(serializedPlaceObject, type);
        }

        if (serializedVehicleObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Vehicle>>(){}.getType();
            vehicleItems = gson.fromJson(serializedVehicleObject, type);
        }

        if (serializedPersonObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Person>>(){}.getType();
            personItems = gson.fromJson(serializedPersonObject, type);
        }

        return new BuyProduce(placeItems, vehicleItems, personItems);
    }

    public static void setFloat(String key, float value, SharedPreferences shPref){
        SharedPreferences.Editor editor = shPref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static Object getFloat(String key, SharedPreferences shPref){
        return shPref.getFloat(key, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void confirmWindow(final View view, final String text, final AppCompatActivity activity) {
        TextView textTV;
        final TextView yesTV;
        final TextView noTV;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.confirm_window, null);

        int viewWidth = view.getWidth();
        int width = (int) (viewWidth * 0.9);
        int height = (int) (width / 1.5);

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        textTV = popupView.findViewById(R.id.textTV);
        textTV.setTextColor(activity.getResources().getColor(R.color.nicotine));
        textTV.setText(text);

        yesTV = popupView.findViewById(R.id.yesTV);
        yesTV.setTextColor(activity.getResources().getColor(R.color.nicotine));
        yesTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    yesTV.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                }

                return false;
            }
        });

        yesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity.finish();

            }
        });

        noTV = popupView.findViewById(R.id.noTV);
        noTV.setTextColor(activity.getResources().getColor(R.color.nicotine));
        noTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    noTV.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                }

                return false;
            }
        });

        noTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}