package com.ak17apps.roaring20s.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ak17apps.roaring20s.R;
import com.ak17apps.roaring20s.util.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class VerticalListViewActivity extends AppCompatActivity {
    protected int screenWidth;
    protected int screenHeight;
    private LinearLayout listLayout;
    protected TextView backButtonTV;
    protected TextView titleTV;
    private Typeface typeFacePopularCafeaa;
    protected LinearLayout sortByLayoutLL;
    protected String sortBy;
    protected LinearLayout outerLayout;
    private Resources res;
    private TextView sortByTextTV;
    private TextView sortByTV;

    public enum SortBy{TYPE_ASC, TYPE_DESC, NAME_ASC, NAME_DESC, SALARY_ASC, SALARY_DESC, ARRIVAL_ASC, ARRIVAL_DESC, WORKPLACE_ASC, WORKPLACE_DESC, STATUS_ASC, STATUS_DESC, SALE_ASC, SALE_DESC,
        SPEED_ASC, SPEED_DESC, CONSUMPTION_ASC, CONSUMPTION_DESC, CAPACITY_ASC, CAPACITY_DESC, LOAD_ASC, LOAD_DESC, PERSONNEL_ASC, PERSONNEL_DESC, SELLPRICE_ASC, SELLPRICE_DESC, BUYPRICE_ASC, BUYPRICE_DESC
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        res = getResources();
        setContentView(R.layout.activity_main);
        outerLayout = findViewById(R.id.outerLayout);

        RelativeLayout mainLayoutRL = findViewById(R.id.main_layout);

        AssetManager am = getApplicationContext().getAssets();
        typeFacePopularCafeaa = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", Settings.FONT_TYPE_POPULARCAFEAA_REGULAR));

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        LinearLayout topBarLL = new LinearLayout(this);
        int topBarWidth = (int) (screenWidth * 0.95);
        int topBarHeight = screenHeight / 8;
        int topPanelHorizontalMargin = (screenWidth - topBarWidth) / 2;
        RelativeLayout.LayoutParams topPar = new RelativeLayout.LayoutParams(topBarWidth, topBarHeight);
        topPar.setMargins(topPanelHorizontalMargin, screenHeight / 40, topPanelHorizontalMargin, 0);
        int topInsideMargin = (topBarWidth - screenWidth / 4 - screenWidth / 2 - screenWidth / 8) / 2;

        sortByLayoutLL = new LinearLayout(this);
        sortByLayoutLL.setOrientation(LinearLayout.HORIZONTAL);
        sortByLayoutLL.setGravity(Gravity.CENTER);
        sortByLayoutLL.setBackground(getResources().getDrawable(R.drawable.main_button));
        LinearLayout.LayoutParams orderByButtonPar = new LinearLayout.LayoutParams(screenWidth / 4, topBarHeight);

        sortByTextTV = new TextView(this);
        sortByTextTV.setTextColor(getResources().getColor(R.color.nicotine));
        sortByTextTV.setText("Sort by: ");
        sortByLayoutLL.addView(sortByTextTV);

        sortByTV = new TextView(this);
        sortByTV.setTextColor(getResources().getColor(R.color.nicotine));
        sortByLayoutLL.addView(sortByTV);
        topBarLL.addView(sortByLayoutLL, orderByButtonPar);

        titleTV = new TextView(this);
        titleTV.setGravity(Gravity.CENTER);
        titleTV.setTextColor(getResources().getColor(R.color.gold));
        titleTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        LinearLayout.LayoutParams titlePar = new LinearLayout.LayoutParams(screenWidth / 2, topBarHeight);
        titlePar.setMargins(topInsideMargin, 0, 0, 0);
        topBarLL.addView(titleTV, titlePar);

        backButtonTV = new TextView(this);
        backButtonTV.setGravity(Gravity.CENTER);
        backButtonTV.setTextColor(getResources().getColor(R.color.nicotine));
        backButtonTV.setText("Back");
        backButtonTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        LinearLayout.LayoutParams backButtonPar = new LinearLayout.LayoutParams(screenWidth / 8, topBarHeight);
        backButtonPar.setMargins(topInsideMargin, 0, 0, 0);
        topBarLL.addView(backButtonTV, backButtonPar);
        mainLayoutRL.addView(topBarLL, topPar);

        listLayout = new LinearLayout(this);
        listLayout.setOrientation(LinearLayout.VERTICAL);

        int scrollViewHeight = (int) (screenHeight * 0.8);
        RelativeLayout.LayoutParams scrollViewPar = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, scrollViewHeight);
        scrollViewPar.setMargins(0, screenHeight - scrollViewHeight, 0, 0);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(listLayout);
        mainLayoutRL.addView(scrollView, scrollViewPar);
    }

    protected void updateSortByTV(){
        sortByTV.setText(getSortByText(sortBy));
    }

    protected void setListItems(ListItem... items){
        for(ListItem item : items){
            listLayout.addView(item);
        }
    }

    protected void addListItem(ListItem item){
        listLayout.addView(item);
    }

    protected void clearList(){
        listLayout.removeAllViews();
    }

    //key, displayed text, is selected
    protected void showSortWindow(List<String> sortByList){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.sort_window, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, (int)(screenHeight * 0.7), (int)(screenHeight * 0.7), true);
        popupWindow.showAtLocation(outerLayout, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sortByLayoutLL.setBackgroundResource(R.drawable.main_button);
            }
        });

        final Map<Integer, String> keysSortBy = new HashMap<>();
        int key = 0;

        LinearLayout sortLL = popupView.findViewById(R.id.sortLL);
        for(String s : sortByList){
            keysSortBy.put(key, s);
            LinearLayout sortOptionLL = new LinearLayout(this);
            sortOptionLL.setId(key);
            key++;
            sortOptionLL.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams sortLLPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            sortLLPar.setMargins(40, 20, 0, 20);
            sortOptionLL.setOrientation(LinearLayout.HORIZONTAL);
            TextView textTV = new TextView(this);
            textTV.setText(getSortByText(s));
            textTV.setTextColor(sortBy.equals(s) ? getResources().getColor(R.color.gold) : getResources().getColor(R.color.nicotine));
            textTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            sortOptionLL.addView(textTV);
            sortOptionLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortBy = keysSortBy.get(v.getId());
                    updateSortByTV();
                    sortByLayoutLL.setBackgroundResource(R.drawable.main_button);
                    loadEntryList(sortBy);
                    popupWindow.dismiss();
                }
            });
            sortLL.addView(sortOptionLL, sortLLPar);
        }

        TextView closeTV = popupView.findViewById(R.id.closeTV);
        closeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByLayoutLL.setBackgroundResource(R.drawable.main_button);
                popupWindow.dismiss();
            }
        });
    }

    private String getSortByText(String sortBy){
        if(sortBy == null || sortBy.equals("")){
            return res.getString(R.string.none);
        }

        String arrow = "";
        if(sortBy.endsWith("ASC")){
            arrow = "▲";
        }else{
            arrow = "▼";
        }

        if(sortBy.equals(SortBy.TYPE_ASC.name()) || sortBy.equals(SortBy.TYPE_DESC.name())){
            return arrow + " " + res.getString(R.string.type);
        }else if(sortBy.equals(SortBy.NAME_ASC.name()) || sortBy.equals(SortBy.NAME_DESC.name())){
            return arrow + " " + res.getString(R.string.name);
        }else if(sortBy.equals(SortBy.SALARY_ASC.name()) || sortBy.equals(SortBy.SALARY_DESC.name())){
            return arrow + " " + res.getString(R.string.salary);
        }else if(sortBy.equals(SortBy.ARRIVAL_ASC.name()) || sortBy.equals(SortBy.ARRIVAL_DESC.name())){
            return arrow + " " + res.getString(R.string.arrival);
        }else if(sortBy.equals(SortBy.WORKPLACE_ASC.name()) || sortBy.equals(SortBy.WORKPLACE_DESC.name())){
            return arrow + " " + res.getString(R.string.workplace);
        }else if(sortBy.equals(SortBy.STATUS_ASC.name()) || sortBy.equals(SortBy.STATUS_DESC.name())){
            return arrow + " " + res.getString(R.string.status);
        }else if(sortBy.equals(SortBy.SPEED_ASC.name()) || sortBy.equals(SortBy.SPEED_DESC.name())){
            return arrow + " " + res.getString(R.string.speed);
        }else if(sortBy.equals(SortBy.CONSUMPTION_ASC.name()) || sortBy.equals(SortBy.CONSUMPTION_DESC.name())){
            return arrow + " " + res.getString(R.string.consumption);
        }else if(sortBy.equals(SortBy.CAPACITY_ASC.name()) || sortBy.equals(SortBy.CAPACITY_DESC.name())){
            return arrow + " " + res.getString(R.string.capacity);
        }else if(sortBy.equals(SortBy.LOAD_ASC.name()) || sortBy.equals(SortBy.LOAD_DESC.name())){
            return arrow + " " + res.getString(R.string.load);
        }else if(sortBy.equals(SortBy.PERSONNEL_ASC.name()) || sortBy.equals(SortBy.PERSONNEL_DESC.name())){
            return arrow + " " + res.getString(R.string.personnel);
        }else if(sortBy.equals(SortBy.SELLPRICE_ASC.name()) || sortBy.equals(SortBy.SELLPRICE_DESC.name())){
            return arrow + " " + res.getString(R.string.sellprice);
        }else if(sortBy.equals(SortBy.BUYPRICE_ASC.name()) || sortBy.equals(SortBy.BUYPRICE_DESC.name())){
            return arrow + " " + res.getString(R.string.buyprice);
        }else if(sortBy.equals(SortBy.SALE_ASC.name()) || sortBy.equals(SortBy.SALE_DESC.name())){
            return arrow + " " + res.getString(R.string.sale);
        }
        return res.getString(R.string.none);
    }

    protected abstract void loadEntryList(String sortBy);

    protected class ListItem extends LinearLayout {
        private LinearLayout topRowLayout;
        private LinearLayout middleRowLayout;
        private LinearLayout bottomRowLayout;

        public ListItem(Context context){
            super(context);
            setOrientation(LinearLayout.VERTICAL);
            setBackground(getResources().getDrawable(R.drawable.main_indicator));
            setGravity(Gravity.CENTER);

            topRowLayout = new LinearLayout(context);
            topRowLayout.setOrientation(LinearLayout.HORIZONTAL);
            addView(topRowLayout);

            middleRowLayout = new LinearLayout(context);
            middleRowLayout.setOrientation(LinearLayout.HORIZONTAL);
            addView(middleRowLayout);

            bottomRowLayout = new LinearLayout(context);
            bottomRowLayout.setOrientation(LinearLayout.HORIZONTAL);
            addView(bottomRowLayout);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.setMargins((int) (screenWidth * 0.02), (int) (screenHeight * 0.01), (int) (screenWidth * 0.02), (int) (screenHeight * 0.01));
            setLayoutParams(params);
        }

        public void setTopRowItems(View... views){
            for(View v : views){
                topRowLayout.addView(v);
            }
        }

        public void setMiddleRowItems(View... views){
            for(View v : views){
                middleRowLayout.addView(v);
            }
        }

        public void setBottomRowItems(View... views){
            for(View v : views){
                bottomRowLayout.addView(v);
            }
        }
    }
}
