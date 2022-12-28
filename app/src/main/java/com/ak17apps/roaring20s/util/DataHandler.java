package com.ak17apps.roaring20s.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ak17apps.roaring20s.database.AppDatabase;
import com.ak17apps.roaring20s.database.DatabaseManager;
import com.ak17apps.roaring20s.database.PersonDao;
import com.ak17apps.roaring20s.database.PlaceDao;
import com.ak17apps.roaring20s.database.VehicleDao;
import com.ak17apps.roaring20s.entity.BuyProduce;
import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.ak17apps.roaring20s.layout.VerticalListViewActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataHandler {
    private static List<Person> personList;
    private static List<Vehicle> vehicleList;
    private static List<Place> portsStationsList;
    private static List<Place> manufactureStorageSaleList;
    private static List<Place> saleList;

    public static float getMoney(SharedPreferences shPref){
        return (float) Settings.getFloat(Settings.MONEY_KEY, shPref);
    }

    public static void setMoney(SharedPreferences shPref, float value){
        Settings.setFloat(Settings.MONEY_KEY, value, shPref);
    }

    public static String getPersonnelSortBy(SharedPreferences shPref){
        String stored = Settings.getString(Settings.PERSONNEL_SORT_BY_KEY, shPref);
        return stored != null ? stored : VerticalListViewActivity.SortBy.TYPE_ASC.name();
    }

    public static List<Person> getPersonList(Context context){
        return getPersonList(null, context);
    }

    public static List<Person> getPersonList(final String sortBy, final Context context){
        personList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = DatabaseManager.build(context);
                PersonDao personDao = db.personDao();
                personList = personDao.getPersonList();
            }
        });
        t.start();

        while(t.isAlive()){}

        if(sortBy.equals(VerticalListViewActivity.SortBy.SALARY_ASC.name())){
            Collections.sort(personList, new PersonSalaryAscComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.SALARY_DESC.name())){
            Collections.sort(personList, new PersonSalaryDescComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.TYPE_ASC.name())){
            Collections.sort(personList, new PersonTypeAscComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.TYPE_DESC.name())){
            Collections.sort(personList, new PersonTypeDescComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.NAME_ASC.name())){
            Collections.sort(personList, new PersonNameAscComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.NAME_DESC.name())){
            Collections.sort(personList, new PersonNameDescComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.ARRIVAL_ASC)){
            Collections.sort(personList, new PersonArrivalAscComparator());
        }else if(sortBy.equals(VerticalListViewActivity.SortBy.ARRIVAL_DESC)){
            Collections.sort(personList, new PersonArrivalDescComparator());
        }

        return personList;
    }

    public static String getVehicleListSortBy(SharedPreferences shPref){
        String stored = Settings.getString(Settings.FLEET_SORT_BY_KEY, shPref);
        return stored != null ? stored : VerticalListViewActivity.SortBy.TYPE_ASC.name();
    }

    public static List<Vehicle> getVehicleList(Context context){
        return getVehicleList(null, context);
    }

    public static List<Vehicle> getVehicleList(final String sortBy, final Context context){
        vehicleList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = DatabaseManager.build(context);
                VehicleDao vehicleDao = db.vehicleDao();
                vehicleList = vehicleDao.getVehicleList();
            }
        });
        t.start();

        while(t.isAlive()){}

        return vehicleList;
    }

    public static List<Place> getPortsStationsList(final Context context){
        portsStationsList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = DatabaseManager.build(context);
                PlaceDao placeDao = db.placeDao();
                portsStationsList = placeDao.getPortsStationsList();
            }
        });
        t.start();

        while(t.isAlive()){}

        return portsStationsList;
    }

    public static String getManufactureStorageSaleSortBy(SharedPreferences shPref){
        String stored = Settings.getString(Settings.MANUFACTURE_STORAGE_SALE_SORT_BY_KEY, shPref);
        return stored != null ? stored : VerticalListViewActivity.SortBy.TYPE_ASC.name();
    }

    public static List<Place> getManufactureStorageSaleList(Context context){
        return getManufactureStorageSaleList(null, context);
    }

    public static List<Place> getManufactureStorageSaleList(final String sortBy, final Context context){
        manufactureStorageSaleList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = DatabaseManager.build(context);
                PlaceDao placeDao = db.placeDao();
                manufactureStorageSaleList = placeDao.getManufactureStorageSaleList();
            }
        });
        t.start();

        while(t.isAlive()){}

        return manufactureStorageSaleList;
    }

    public static String getBuyProduceSortBy(SharedPreferences shPref){
        String stored = Settings.getString(Settings.BUY_PRODUCE_SORT_BY_KEY, shPref);
        return stored != null ? stored : VerticalListViewActivity.SortBy.TYPE_ASC.name();
    }

    public static BuyProduce getBuyProduceList(SharedPreferences shPref){
        return Settings.getBuyProduce(shPref);
    }

    public static List<Place> getSellList(){
        return getManufactureStorageSaleList(null);
    }

    public static List<Place> getSellList(final String sortBy, final Context context){
        saleList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = DatabaseManager.build(context);
                PlaceDao placeDao = db.placeDao();
                saleList = placeDao.getSaleList();
            }
        });
        t.start();

        while(t.isAlive()){}

        return saleList;
    }

    static class PersonSalaryAscComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            if(p1.getSalary() > p2.getSalary()){
                return 1;
            }else if(p2.getSalary() > p1.getSalary()){
                return -1;
            }else{
                return 0;
            }
        }
    }

    static class PersonSalaryDescComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            if(p2.getSalary() > p1.getSalary()){
                return 1;
            }else if(p1.getSalary() > p2.getSalary()){
                return -1;
            }else{
                return 0;
            }
        }
    }

    static class PersonTypeAscComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p1.getType().compareTo(p2.getType());
        }
    }

    static class PersonTypeDescComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p2.getType().compareTo(p1.getType());
        }
    }

    static class PersonNameAscComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    static class PersonNameDescComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p2.getName().compareTo(p1.getName());
        }
    }

    static class PersonArrivalDescComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            if(p1.getVehicle() == null || p1.getVehicle().getArrival() == null || p2.getVehicle() == null || p2.getVehicle().getArrival() == null){
                return 0;
            }

            if(p2.getVehicle().getArrival().getTimeInMillis() > p1.getVehicle().getArrival().getTimeInMillis()){
                return 1;
            }else if(p1.getVehicle().getArrival().getTimeInMillis() > p2.getVehicle().getArrival().getTimeInMillis()){
                return -1;
            }else{
                return 0;
            }
        }
    }

    static class PersonArrivalAscComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            if(p1.getVehicle() == null || p1.getVehicle().getArrival() == null || p2.getVehicle() == null || p2.getVehicle().getArrival() == null){
                return 0;
            }

            if(p1.getVehicle().getArrival().getTimeInMillis() > p2.getVehicle().getArrival().getTimeInMillis()){
                return 1;
            }else if(p2.getVehicle().getArrival().getTimeInMillis() > p1.getVehicle().getArrival().getTimeInMillis()){
                return -1;
            }else{
                return 0;
            }
        }
    }
}