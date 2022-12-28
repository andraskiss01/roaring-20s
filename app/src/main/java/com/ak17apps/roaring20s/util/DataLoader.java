package com.ak17apps.roaring20s.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.ak17apps.roaring20s.database.AppDatabase;
import com.ak17apps.roaring20s.database.DatabaseManager;
import com.ak17apps.roaring20s.database.PersonDao;
import com.ak17apps.roaring20s.database.PlaceDao;
import com.ak17apps.roaring20s.database.VehicleDao;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.ak17apps.roaring20s.layout.VerticalListViewActivity;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static void checkData(Context context) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = preference.getBoolean(Settings.FIRST_RUN, true);
        if (isFirstRun) {
            databaseSetup(context);

            SharedPreferences.Editor editor = preference.edit();
            editor.putBoolean(Settings.FIRST_RUN, false);
            editor.putFloat(Settings.MONEY_KEY, 6311863.9f);
            editor.putString(Settings.PERSONNEL_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.putString(Settings.FLEET_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.putString(Settings.MANUFACTURE_STORAGE_SALE_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.putString(Settings.BUY_PRODUCE_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.putString(Settings.ON_WAY_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.putString(Settings.SELL_SORT_BY_KEY, VerticalListViewActivity.SortBy.TYPE_ASC.name());
            editor.apply();
        }
    }

    private static void databaseSetup(final Context context) {
        final Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = DatabaseManager.build(context);

                        PersonDao personDao = db.personDao();
                        personDao.deleteAll();
                        personDao.insertAll(DataLoader.loadPerson());

                        VehicleDao vehicleDao = db.vehicleDao();
                        vehicleDao.deleteAll();
                        vehicleDao.insertAll(DataLoader.loadVehicle());

                        PlaceDao placeDao = db.placeDao();
                        placeDao.deleteAll();
                        placeDao.insertAll(DataLoader.loadPlace());

                        handler.removeCallbacks(this);

                        Looper looper = Looper.myLooper();
                        if(looper != null) {
                            looper.quit();
                        }
                    }
                }, 0);

                Looper.loop();
            }
        };
        thread.start();

        while (thread.isAlive()) {}

        return;
    }

    public static Person[] loadPerson(){
        List<Person> personList = new ArrayList<>();
        List<String> nameList = Settings.getNames(45);
        String[] names = nameList.toArray(new String[45]);

        personList.add(new Person(0, names[0],10, Type.PERSON_DRIVER));
        personList.add(new Person(1, names[1],10, Type.PERSON_DRIVER));
        personList.add(new Person(2, names[2], 10, Type.PERSON_DRIVER));
        personList.add(new Person(3, names[3], 10, Type.PERSON_DRIVER));
        personList.add(new Person(4, names[4], 10, Type.PERSON_DRIVER));
        personList.add(new Person(5, names[5], 10, Type.PERSON_DRIVER));
        personList.add(new Person(6, names[6], 10, Type.PERSON_DRIVER));
        personList.add(new Person(7, names[7], 10, Type.PERSON_DRIVER));
        personList.add(new Person(8, names[8], 10, Type.PERSON_DRIVER));
        personList.add(new Person(9, names[9], 10, Type.PERSON_DRIVER));
        personList.add(new Person(10, names[10], 10, Type.PERSON_DRIVER));
        personList.add(new Person(11, names[11], 10, Type.PERSON_DRIVER));
        personList.add(new Person(12, names[12], 15, Type.PERSON_SAILOR));
        personList.add(new Person(13, names[13], 15, Type.PERSON_SAILOR));
        personList.add(new Person(14, names[14], 15, Type.PERSON_SAILOR));
        personList.add(new Person(15, names[15], 30, Type.PERSON_PILOT));
        personList.add(new Person(16, names[16], 30, Type.PERSON_PILOT));
        personList.add(new Person(17, names[17], 30, Type.PERSON_PILOT));
        personList.add(new Person(18, names[18], 25, Type.PERSON_TRAIN_DRIVER));
        personList.add(new Person(19, names[19], 25, Type.PERSON_TRAIN_DRIVER));
        personList.add(new Person(20, names[20], 25, Type.PERSON_TRAIN_DRIVER));
        personList.add(new Person(21, names[21], 10, Type.PERSON_STOKER));
        personList.add(new Person(22, names[22], 10, Type.PERSON_STOKER));
        personList.add(new Person(23, names[23], 10, Type.PERSON_STOKER));
        personList.add(new Person(24, names[24], 10, Type.PERSON_STOKER));
        personList.add(new Person(25, names[25], 10, Type.PERSON_STOKER));
        personList.add(new Person(26, names[26], 10, Type.PERSON_STOKER));
        personList.add(new Person(27, names[27], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(28, names[28], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(29, names[29], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(30, names[30], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(31, names[31], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(32, names[32], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(33, names[33], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(34, names[34], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(35, names[35], 3, Type.PERSON_BARTENDER));
        personList.add(new Person(36, names[36], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(37, names[37], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(38, names[38], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(39, names[39], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(40, names[40], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(41, names[41], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(42, names[42], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(43, names[43], 5, Type.PERSON_STOREKEEPER));
        personList.add(new Person(44, names[44], 5, Type.PERSON_STOREKEEPER));

        Person[] personArray = new Person[personList.size()];
        personArray = personList.toArray(personArray);

        return personArray;
    }

    public static Vehicle[] loadVehicle(){
        List<Vehicle> vehicleList = new ArrayList<>();

        vehicleList.add(new Vehicle(0, "", 80, 50, 5));
        vehicleList.add(new Vehicle(1, "A", 5000, 3000, 75, 200, 5, Type.VEHICLE_LORRY_SMALL));
        vehicleList.add(new Vehicle(2, "B", 5000, 3000, 75, 200, 5, Type.VEHICLE_LORRY_SMALL));
        vehicleList.add(new Vehicle(3, "C", 5000, 3000, 75, 200, 5, Type.VEHICLE_LORRY_SMALL));
        vehicleList.add(new Vehicle(4, "A", 6000, 3700, 60, 300, 7, Type.VEHICLE_LORRY_BIG));
        vehicleList.add(new Vehicle(5, "B", 6000, 3700, 60, 300, 7, Type.VEHICLE_LORRY_BIG));
        vehicleList.add(new Vehicle(6, "C", 6000, 3700, 60, 300, 7, Type.VEHICLE_LORRY_BIG));
        vehicleList.add(new Vehicle(7, "A", 8000, 4900, 60, 500, 16, Type.VEHICLE_TRUCK_SMALL));
        vehicleList.add(new Vehicle(8, "B", 8000, 4900, 60, 500, 16, Type.VEHICLE_TRUCK_SMALL));
        vehicleList.add(new Vehicle(9, "C", 8000, 4900, 60, 500, 16, Type.VEHICLE_TRUCK_SMALL));
        vehicleList.add(new Vehicle(10, "A", 10000, 6200, 50, 700, 20, Type.VEHICLE_TRUCK_BIG));
        vehicleList.add(new Vehicle(11, "B", 10000, 6200, 50, 700, 20, Type.VEHICLE_TRUCK_BIG));
        vehicleList.add(new Vehicle(12, "C", 10000, 6200, 50, 700, 20, Type.VEHICLE_TRUCK_BIG));
        vehicleList.add(new Vehicle(13, "A", 15000, 9300, 20, 3200, 66, Type.VEHICLE_SHIP_SMALL));
        vehicleList.add(new Vehicle(14, "B", 15000, 9300, 20, 3200, 66, Type.VEHICLE_SHIP_SMALL));
        vehicleList.add(new Vehicle(15, "C", 15000, 9300, 20, 3200, 66, Type.VEHICLE_SHIP_SMALL));
        vehicleList.add(new Vehicle(16, "A", 20000, 12400, 15, 4200, 77, Type.VEHICLE_SHIP_BIG));
        vehicleList.add(new Vehicle(17, "B", 20000, 12400, 15, 4200, 77, Type.VEHICLE_SHIP_BIG));
        vehicleList.add(new Vehicle(18, "C", 20000, 12400, 15, 4200, 77, Type.VEHICLE_SHIP_BIG));
        vehicleList.add(new Vehicle(19, "A", 6000, 3700, 300, 80, 12, Type.VEHICLE_PLANE_SMALL));
        vehicleList.add(new Vehicle(20, "B", 6000, 3700, 300, 80, 12, Type.VEHICLE_PLANE_SMALL));
        vehicleList.add(new Vehicle(21, "C", 6000, 3700, 300, 80, 12, Type.VEHICLE_PLANE_SMALL));
        vehicleList.add(new Vehicle(22, "A", 8000, 4900, 250, 100, 16, Type.VEHICLE_PLANE_BIG));
        vehicleList.add(new Vehicle(23, "B", 8000, 4900, 250, 100, 16, Type.VEHICLE_PLANE_BIG));
        vehicleList.add(new Vehicle(24, "C", 8000, 4900, 250, 100, 16, Type.VEHICLE_PLANE_BIG));
        vehicleList.add(new Vehicle(25, "A", 30000, 18500, 60, 2200, 100, Type.VEHICLE_TRAIN_SMALL));
        vehicleList.add(new Vehicle(26, "B", 30000, 18500, 60, 2200, 100, Type.VEHICLE_TRAIN_SMALL));
        vehicleList.add(new Vehicle(27, "C", 30000, 18500, 60, 2200, 100, Type.VEHICLE_TRAIN_SMALL));
        vehicleList.add(new Vehicle(28, "A", 40000, 24700, 55, 2600, 100, Type.VEHICLE_TRAIN_BIG));
        vehicleList.add(new Vehicle(29, "B", 40000, 24700, 55, 2600, 100, Type.VEHICLE_TRAIN_BIG));
        vehicleList.add(new Vehicle(30, "C", 40000, 24700, 55, 2600, 100, Type.VEHICLE_TRAIN_BIG));

        Vehicle[] vehicleArray = new Vehicle[vehicleList.size()];
        vehicleArray = vehicleList.toArray(vehicleArray);

        return vehicleArray;
    }

    public static Place[] loadPlace() {
        List<Place> placeList = new ArrayList<>();

        placeList.add(new Place(0, "", 130, 80));
        placeList.add(new Place(1, "A", 2000, 1200, 260, 130, Type.PLACE_SMALL_PUB));
        placeList.add(new Place(2, "B", 2000, 1200, 260, 130, Type.PLACE_SMALL_PUB));
        placeList.add(new Place(3, "C", 2000, 1200, 260, 130, Type.PLACE_SMALL_PUB));
        placeList.add(new Place(4, "A", 3500, 2200, 530, 260, Type.PLACE_MEDIUM_PUB));
        placeList.add(new Place(5, "B", 3500, 2200, 530, 260, Type.PLACE_MEDIUM_PUB));
        placeList.add(new Place(6, "C", 3500, 2200, 530, 260, Type.PLACE_MEDIUM_PUB));
        placeList.add(new Place(7, "A", 4000, 2500, 660, 400, Type.PLACE_BIG_PUB));
        placeList.add(new Place(8, "B", 4000, 2500, 660, 400, Type.PLACE_BIG_PUB));
        placeList.add(new Place(9, "C", 4000, 2500, 660, 400, Type.PLACE_BIG_PUB));
        placeList.add(new Place(10, "A", 5000, 3100, 1300, 0, Type.PLACE_SMALL_STORE));
        placeList.add(new Place(11, "B", 5000, 3100, 1300, 0, Type.PLACE_SMALL_STORE));
        placeList.add(new Place(12, "C", 5000, 3100, 1300, 0, Type.PLACE_SMALL_STORE));
        placeList.add(new Place(13, "A", 8000, 4900, 2600, 0, Type.PLACE_MEDIUM_STORE));
        placeList.add(new Place(14, "B", 8000, 4900, 2600, 0, Type.PLACE_MEDIUM_STORE));
        placeList.add(new Place(15, "C", 8000, 4900, 2600, 0, Type.PLACE_MEDIUM_STORE));
        placeList.add(new Place(16, "A", 15000, 9300, 5300, 0, Type.PLACE_BIG_STORE));
        placeList.add(new Place(17, "B", 15000, 9300, 5300, 0, Type.PLACE_BIG_STORE));
        placeList.add(new Place(18, "C", 15000, 9300, 5300, 0, Type.PLACE_BIG_STORE));
        placeList.add(new Place(19, "", 20000, 12300, 0, 0, Type.PLACE_AIRPORT));
        placeList.add(new Place(20, "", 100000, 61800, 0, 0, Type.PLACE_RAILWAY_STATION));
        placeList.add(new Place(21, "", 50000, 30900, 0, 0, Type.PLACE_PORT));

        Place[] placeArray = new Place[placeList.size()];
        placeArray = placeList.toArray(placeArray);

        return placeArray;
    }
}
