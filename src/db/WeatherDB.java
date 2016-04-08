package db;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Country;
import model.Province;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	Context context;
	public static final String DB_NAME = "sunshine_weather";
	public static final int VERSION = 1;
	private static WeatherDB weatherDB;
	private SQLiteDatabase database;

	public WeatherDB(Context context) {
		WeatherOpenHelper helper = new WeatherOpenHelper(context, DB_NAME,
				null, VERSION);
		database = helper.getWritableDatabase();
	}

	public synchronized static WeatherDB getInstance(Context context) {
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}

	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("provinceName", province.getProvinceName());
			values.put("provinceCode", province.getProvinceCode());
			database.insert("province", null, values);
		}
	}

	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = database.query("province", null, null, null, null,
				null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("provinceName")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("provinceCode")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("cityName", city.getCityName());
			values.put("cityCode", city.getCityCode());
			values.put("provinceId", city.getProvinceId());
			database.insert("city", null, values);
		}
	}

	public List<City> loadCity(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = database.query("city", null, "provinceId = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("cityName")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("cityCode")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	public void saveCountry(Country country) {
		if (country != null) {
			ContentValues values = new ContentValues();
			values.put("countryName", country.getCountryName());
			values.put("countryCode", country.getCountryCode());
			values.put("cityId", country.getCityId());
			database.insert("country", null, values);
		}
	}

	public List<Country> loadCountry(int cityId) {
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = database.query("city", null, "cityId = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor
						.getColumnIndex("cityName")));
				country.setCountryCode(cursor.getString(cursor
						.getColumnIndex("cityCode")));
				country.setCityId(cityId);
				list.add(country);
			} while (cursor.moveToNext());
		}
		return list;
	}
	

}
