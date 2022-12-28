package com.ak17apps.roaring20s.layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ak17apps.roaring20s.BuildConfig;
import com.ak17apps.roaring20s.R;
import com.ak17apps.roaring20s.util.DataHandler;
import com.ak17apps.roaring20s.util.DataLoader;
import com.ak17apps.roaring20s.util.Settings;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;
    private Typeface typeFacePopularCafeaa;
    private Typeface typeFaceMarlboro;
    private SharedPreferences shPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shPref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        DataLoader.checkData(getApplicationContext());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.main_layout);

        AssetManager am = getApplicationContext().getAssets();
        typeFaceMarlboro = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", Settings.FONT_TYPE_MARLBORO));
        typeFacePopularCafeaa = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", Settings.FONT_TYPE_POPULARCAFEAA_REGULAR));

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        LinearLayout topBarLL = new LinearLayout(this);
        int topBarWidth = (int) (screenWidth * 0.95);
        int topBarHeight = screenHeight / 15;
        int topPanelHorizontalMargin = (screenWidth - topBarWidth) / 2;
        RelativeLayout.LayoutParams topPar = new RelativeLayout.LayoutParams(topBarWidth, topBarHeight);
        topPar.setMargins(topPanelHorizontalMargin, screenHeight / 40, topPanelHorizontalMargin, 0);
        int topItemWidth = screenWidth / 8;

        SingleValueIndicator grainSVI = new SingleValueIndicator(this, Settings.getGrainPrice(), topItemWidth, topBarHeight, R.drawable.grain);
        SingleValueIndicator gasolineSVI = new SingleValueIndicator(this, Settings.getGasolinePrice(), topItemWidth, topBarHeight, R.drawable.jerrycan);
        SingleValueIndicator coalSVI = new SingleValueIndicator(this, Settings.getCoalPrice(), topItemWidth, topBarHeight, R.drawable.coal);
        ExchangeRateIndicator whiskeyERI = new ExchangeRateIndicator(this, "Whiskey", R.drawable.barrel, Settings.getWhiskeyBuyPrice(), Settings.getWhiskeySellPrice(), (int) (topItemWidth * 2.8), topBarHeight);
        int topInsideMargin = (topBarWidth - gasolineSVI.getLayoutWidth() - coalSVI.getLayoutWidth() - whiskeyERI.getLayoutWidth() - grainSVI.getLayoutWidth()) / 3;

        LinearLayout.LayoutParams grainPar = new LinearLayout.LayoutParams(grainSVI.getLayoutWidth(), topBarHeight);
        topBarLL.addView(grainSVI, grainPar);

        LinearLayout.LayoutParams gasolinePar = new LinearLayout.LayoutParams(gasolineSVI.getLayoutWidth(), topBarHeight);
        gasolinePar.setMargins(topInsideMargin, 0, 0, 0);
        topBarLL.addView(gasolineSVI, gasolinePar);

        LinearLayout.LayoutParams coalPar = new LinearLayout.LayoutParams(coalSVI.getLayoutWidth(), topBarHeight);
        coalPar.setMargins(topInsideMargin, 0, 0, 0);
        topBarLL.addView(coalSVI, coalPar);

        LinearLayout.LayoutParams whiskeyPar = new LinearLayout.LayoutParams(whiskeyERI.getLayoutWidth(), topBarHeight);
        whiskeyPar.setMargins(topInsideMargin, 0, 0, 0);
        topBarLL.addView(whiskeyERI, whiskeyPar);
        mainLayout.addView(topBarLL, topPar);

        int mainWidth = (int) (screenWidth * 0.3);
        int mainHeight = screenHeight / 4;
        int mainLeftMarginLeft = (int) (screenWidth * 0.3);
        int mainRightMarginLeft = (int) (screenWidth * 0.63);
        int mainTopMarginTop = (int) (screenHeight * 0.17);
        int mainBottomMarginTop = (int) (screenHeight * 0.47);

        int sideBarWidth = (int) (screenWidth * 0.2);
        int sideBarHeight = mainHeight * 2 + mainBottomMarginTop - mainHeight - mainTopMarginTop;
        RelativeLayout.LayoutParams sideBarPar = new RelativeLayout.LayoutParams(sideBarWidth, sideBarHeight);
        sideBarPar.setMargins(topPanelHorizontalMargin, mainTopMarginTop, 0, 0);
        LinearLayout sideBarLL = new LinearLayout(this);
        sideBarLL.setOrientation(LinearLayout.VERTICAL);
        sideBarLL.setGravity(Gravity.CENTER_HORIZONTAL);
        SingleValueIndicator moneySVI = new SingleValueIndicator(this, DataHandler.getMoney(shPref), sideBarWidth, topBarHeight, R.drawable.money);
        LinearLayout.LayoutParams moneyPar = new LinearLayout.LayoutParams(moneySVI.getLayoutWidth(), topBarHeight);
        sideBarLL.addView(moneySVI, moneyPar);

        TextView dailyGiftTV = new TextView(this);
        dailyGiftTV.setText("daily gift");
        dailyGiftTV.setBackgroundColor(Color.RED);
        dailyGiftTV.setGravity(Gravity.CENTER);
        dailyGiftTV.setVisibility(View.GONE);
        LinearLayout.LayoutParams dailyGiftPar = new LinearLayout.LayoutParams(moneySVI.getLayoutWidth() / 2, moneySVI.getLayoutWidth() / 2);
        dailyGiftPar.setMargins(0,sideBarHeight / 2 - dailyGiftPar.height, 0, 0);
        sideBarLL.addView(dailyGiftTV, dailyGiftPar);
        mainLayout.addView(sideBarLL, sideBarPar);

        final TextView topLeftTV = new TextView(this);
        topLeftTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topLeftTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, PersonnelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        topLeftTV.setText(getResources().getString(R.string.personnel));
        topLeftTV.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams topLeftPar = new RelativeLayout.LayoutParams(mainWidth, mainHeight);
        topLeftPar.setMargins(mainLeftMarginLeft, mainTopMarginTop, 0, 0);
        topLeftTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        topLeftTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        topLeftTV.setTextColor(getResources().getColor(R.color.gold));
        topLeftTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        mainLayout.addView(topLeftTV, topLeftPar);

        final TextView topRightTV = new TextView(this);
        topRightTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRightTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, FleetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        topRightTV.setText(getResources().getString(R.string.fleet));
        topRightTV.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams topRightPar = new RelativeLayout.LayoutParams(mainWidth, mainHeight);
        topRightPar.setMargins(mainRightMarginLeft, mainTopMarginTop, 0, 0);
        topRightTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        topRightTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        topRightTV.setTextColor(getResources().getColor(R.color.gold));
        topRightTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        mainLayout.addView(topRightTV, topRightPar);

        final TextView bottomLeftTV = new TextView(this);
        bottomLeftTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLeftTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, PortsStationsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        bottomLeftTV.setText(getResources().getString(R.string.ports_stations));
        bottomLeftTV.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams bottomLeftPar = new RelativeLayout.LayoutParams(mainWidth, mainHeight);
        bottomLeftPar.setMargins(mainLeftMarginLeft, mainBottomMarginTop, 0, 0);
        bottomLeftTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        bottomLeftTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        bottomLeftTV.setTextColor(getResources().getColor(R.color.gold));
        bottomLeftTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        mainLayout.addView(bottomLeftTV, bottomLeftPar);

        final TextView bottomRightTV = new TextView(this);
        bottomRightTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomRightTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, ManufactureStorageSaleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        bottomRightTV.setText(getResources().getString(R.string.manufacture_storage_sale));
        bottomRightTV.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams bottomRightPar = new RelativeLayout.LayoutParams(mainWidth, mainHeight);
        bottomRightPar.setMargins(mainRightMarginLeft, mainBottomMarginTop, 0, 0);
        bottomRightTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        bottomRightTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        bottomRightTV.setTextColor(getResources().getColor(R.color.gold));
        bottomRightTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        mainLayout.addView(bottomRightTV, bottomRightPar);

        int bottomBarHeight = screenHeight / 8;
        LinearLayout bottomBarLL = new LinearLayout(this);
        int panelWidth = (int) (screenWidth * 0.9);
        int panelHorizontalMargin = (screenWidth - panelWidth) / 2;
        RelativeLayout.LayoutParams bottomPar = new RelativeLayout.LayoutParams(panelWidth, bottomBarHeight);
        bottomPar.setMargins(panelHorizontalMargin, (int) (screenHeight * 0.8), panelHorizontalMargin, 0);

        int bottomItemWidth = screenWidth / 4;
        final TextView buyProduceTV = new TextView(this);
        buyProduceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyProduceTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, BuyProduceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        buyProduceTV.setWidth(bottomItemWidth);
        buyProduceTV.setHeight(bottomBarHeight);
        buyProduceTV.setGravity(Gravity.CENTER);
        buyProduceTV.setText(getResources().getString(R.string.buy_produce));
        buyProduceTV.setBackgroundColor(Color.BLACK);
        buyProduceTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        buyProduceTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        buyProduceTV.setTextColor(getResources().getColor(R.color.gold));
        buyProduceTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        bottomBarLL.addView(buyProduceTV);

        int bottomInsideMargin = (panelWidth - bottomItemWidth * 3) / 2;
        final TextView onWayTV = new TextView(this);
        onWayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWayTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, OnWayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        onWayTV.setWidth(bottomItemWidth);
        onWayTV.setHeight(bottomBarHeight);
        onWayTV.setGravity(Gravity.CENTER);
        onWayTV.setText(getResources().getString(R.string.on_way));
        LinearLayout.LayoutParams onWayPar = new LinearLayout.LayoutParams(bottomItemWidth, bottomBarHeight);
        onWayPar.setMargins(bottomInsideMargin, 0, 0, 0);
        onWayTV.setBackgroundColor(Color.BLACK);
        onWayTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        onWayTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        onWayTV.setTextColor(getResources().getColor(R.color.gold));
        onWayTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        bottomBarLL.addView(onWayTV, onWayPar);

        final TextView sellTV = new TextView(this);
        sellTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellTV.setBackgroundResource(R.drawable.main_button_pushed);
                Intent intent = new Intent(MainActivity.this, SellActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        sellTV.setWidth(bottomItemWidth);
        sellTV.setHeight(bottomBarHeight);
        sellTV.setGravity(Gravity.CENTER);
        sellTV.setText(getResources().getString(R.string.sell));
        LinearLayout.LayoutParams sellPar = new LinearLayout.LayoutParams(bottomItemWidth, bottomBarHeight);
        sellPar.setMargins(bottomInsideMargin, 0, 0, 0);
        sellTV.setBackgroundColor(Color.BLACK);
        sellTV.setTypeface(typeFacePopularCafeaa, Typeface.NORMAL);
        sellTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        sellTV.setTextColor(getResources().getColor(R.color.gold));
        sellTV.setBackground(getResources().getDrawable(R.drawable.main_button));
        bottomBarLL.addView(sellTV, sellPar);

        mainLayout.addView(bottomBarLL, bottomPar);

        /*final TextView hwTV = findViewById(R.id.hwTV);
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                hwTV.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hwTV.setText("done!");
            }
        }.start();*/
    }

    class SingleValueIndicator extends LinearLayout{
        private double amount;
        private int layoutWidth;

        public SingleValueIndicator(Context context, double amount, int layoutWidth, int layoutHeight, int iconId){
            super(context);
            this.amount = amount;
            this.layoutWidth = layoutWidth;
            setOrientation(LinearLayout.HORIZONTAL);
            setBackground(getResources().getDrawable(R.drawable.main_indicator));
            setGravity(Gravity.CENTER);

            int iconSize = (int) (layoutHeight * 0.8);
            ImageView symbolIV = new ImageView(context);
            symbolIV.setBackground(getResources().getDrawable(iconId));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iconSize, iconSize);
            layoutParams.setMargins(0, 0, screenWidth / 128, 0);
            symbolIV.setLayoutParams(layoutParams);
            addView(symbolIV);

            TextView amountTV = new TextView(context);
            amountTV.setTypeface(typeFaceMarlboro, Typeface.BOLD);
            amountTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            amountTV.setTextColor(getResources().getColor(R.color.nicotine));
            amountTV.setText("$" + Math.round(amount * 10) / 10F);
            addView(amountTV);
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getLayoutWidth() {
            return layoutWidth;
        }

        public void setLayoutWidth(int layoutWidth) {
            this.layoutWidth = layoutWidth;
        }
    }

    class ExchangeRateIndicator extends LinearLayout{
        private String name;
        private double buyPrice;
        private double sellPrice;
        private int layoutWidth;

        public ExchangeRateIndicator(Context context, String name, int iconId, double buyPrice, double sellPrice, int layoutWidth, int layoutHeight){
            super(context);
            this.name = name;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
            this.layoutWidth = layoutWidth;
            setOrientation(LinearLayout.HORIZONTAL);
            setBackground(getResources().getDrawable(R.drawable.main_indicator));
            setGravity(Gravity.CENTER);

            int iconSize = (int) (layoutHeight * 0.8);
            ImageView symbolIV = new ImageView(context);
            symbolIV.setBackground(getResources().getDrawable(iconId));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iconSize, iconSize);
            layoutParams.setMargins(0, 0, screenWidth / 128, 0);
            symbolIV.setLayoutParams(layoutParams);
            addView(symbolIV);

            TextView purchasePriceTV = new TextView(context);
            purchasePriceTV.setTypeface(typeFaceMarlboro, Typeface.BOLD);
            purchasePriceTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            purchasePriceTV.setTextColor(getResources().getColor(R.color.nicotine));
            purchasePriceTV.setText("BUY: $" + Math.round(buyPrice * 10) / 10F);
            addView(purchasePriceTV);

            TextView salePriceTV = new TextView(context);
            salePriceTV.setTypeface(typeFaceMarlboro, Typeface.BOLD);
            salePriceTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            salePriceTV.setTextColor(getResources().getColor(R.color.nicotine));
            salePriceTV.setText(" SELL: $" + Math.round(sellPrice * 10) / 10F);
            addView(salePriceTV);
        }

        public double getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(double buyPrice) {
            this.buyPrice = buyPrice;
        }

        public double getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(double sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLayoutWidth() {
            return layoutWidth;
        }

        public void setLayoutWidth(int layoutWidth) {
            this.layoutWidth = layoutWidth;
        }
    }
}
