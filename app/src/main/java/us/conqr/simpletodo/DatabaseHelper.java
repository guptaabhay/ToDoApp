package us.conqr.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by abhaygupta on 2/19/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODO = "todoTable";

    // Post Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_TODO_ITEM = "item";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
            "(" +
            KEY_TODO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
            KEY_TODO_TODO_ITEM + " TEXT" +
            ")";

        db.execSQL(CREATE_TODO_TABLE);
    }

    // This method is called when database is upgraded like
    // modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);

        // Create tables again
        onCreate(db);
    }

    public void AddToDoItem(ToDoItem toDoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_TODO_TODO_ITEM, toDoItem.getItem());
        cv.put(KEY_TODO_ID, toDoItem.getID());

        db.insert(TABLE_TODO, null, cv);
        db.close();
    }

    ArrayList<ToDoItem> getAllToDoItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_TODO, null);

        ArrayList<ToDoItem> items = new ArrayList<>();

        if (cur.moveToFirst()) {
            while (cur.isAfterLast() == false) {
                Integer id = cur.getInt(cur.getColumnIndex(KEY_TODO_ID));
                String item = cur.getString(cur.getColumnIndex(KEY_TODO_TODO_ITEM));

                items.add(new ToDoItem(id, item));
                cur.moveToNext();
            }
        }

        return items;
    }

    public void UpdateItem(ToDoItem toDoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TODO_ID, toDoItem.getID());
        cv.put(KEY_TODO_TODO_ITEM, toDoItem.getItem());

        db.update(TABLE_TODO, cv, KEY_TODO_ID + "=" + toDoItem.getID().toString(), null);
        db.close();
    }

    public void DeleteItem(ToDoItem toDoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_TODO_ID + "=" + toDoItem.getID().toString(), null);
        db.close();
    }
}