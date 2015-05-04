package todaysmenu.pondar.org.todaysmenu;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    //The database file.
	private static final String DATABASE = "menu.db";

    //Version 2, version 1 was a test version.
	private static final int VERSION = 2;
	

	public Database(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public Database(Context context)
	{
		super(context,DATABASE,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//starting database
		db.execSQL("CREATE TABLE menu ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT, weekday INTEGER);");
		db.execSQL("CREATE TABLE choices ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT);");				
	}

    //we add a new choice - so now it's possible to extend the list of choices.
	public void addChoice(String choice) {
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("INSERT INTO choices (name) VALUES ('"+choice+"')");
		db.close();
	}

    /*
     * Reading the user-defined choices from the database
     */
	public String[] readChoices()
	{
	
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.rawQuery("SELECT name FROM choices ORDER BY id",null);
		//first test if there is anything in the choices table
		int count = cursor.getCount();
		if (count==0) //there is nothing, so first time we start the app.
		{
			//if empty then put all elements from constants into database there
            //that means: no extra choices was input by the user (or they were cleared)
			for (String choice : Choices.ELEMENTS_RESET)
				database.execSQL("INSERT INTO choices (name) VALUES ('"+choice+"')");
            //make a copy of our choices for use in the UI
            Choices.ELEMENTS = new String[Choices.ELEMENTS_RESET.length];
            System.arraycopy(Choices.ELEMENTS_RESET, 0, Choices.ELEMENTS, 0, Choices.ELEMENTS_RESET.length);
			return Choices.ELEMENTS_RESET; //we just have default values.
		}
		else
		{
			//if not empty, then read the extra choices from the table and redefine the elements
			String[] elements = new String[count];
			int index = 0;
			while (cursor.moveToNext())
			{ 					
				elements[index] = cursor.getString(0);
				index++;
			}
			Choices.ELEMENTS = elements; 	//overwrite the elements array
            return elements;
        }

    }

    //Add a new item into the database - to track what we had today for lunch!
	public void addFood(String menu) {
		Calendar calendar = Calendar.getInstance();
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		SQLiteDatabase db = getWritableDatabase();
		//System.out.println("Adding food: "+menu+" at day: "+weekday);
        //We are also saving the current day - maybe for future uses.
		db.execSQL("INSERT INTO menu (name,weekday) VALUES ('"+menu+"',"+weekday+")");
		db.close();
	}

    //clear all menu data
	public void clearData()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM menu"); //clear all
		db.close();
	}

    //clear all extra added choices from the database
	public void clearChoices()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM choices");
		db.close();
	}

    //This gets all the starts from the database as an arraylist.
	public ArrayList<Item> getStats()
	{
		ArrayList<Item> stats = new ArrayList<Item>();
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.rawQuery("SELECT name,weekday FROM menu ORDER BY name",null);
		int total = 0; 
		HashMap<String, Integer> map = new HashMap<String, Integer>();
        //simple loop for calculating the frequencies of an item.
		while (cursor.moveToNext())
		{ 
			total++;
			String name = cursor.getString(0);
			if (map.containsKey(name)) //update frequency
			{
				int freq = map.get(name)+1;
				map.put(name, freq);
			}
			else //new item
			{
				map.put(name, 1);
			}
			int day = cursor.getInt(1);						
		}
		System.out.println("TOTAL ROWS = "+total);
		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			stats.add(new Item(entry.getKey(), entry.getValue(), total));
		}
		cursor.close();
		database.close();
		return stats;
	}

    /*
      This was an upgrade method, the old version had no user-added choices.
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion==1 && newVersion==2)
			db.execSQL("CREATE TABLE choices ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT);");
	}
	

}
