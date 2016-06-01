package com.festival.tacademy.festivalmate.Manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;


import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dongja94 on 2016-05-17.
 */
public class DataManager extends SQLiteOpenHelper {
    private static DataManager instance;
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    private static final String DB_NAME = "chat";
    private static final int DB_VERSION = 1;

    private DataManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DataConstant.ChatUserTable.TABLE_NAME + " (" +
                DataConstant.ChatUserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID + " INTEGER," +
                DataConstant.ChatUserTable.COLUMN_NAME + " TEXT," +
                DataConstant.ChatUserTable.COLUMN_EMAIL + " TEXT," +
                DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(query);

        query = "CREATE TABLE " + DataConstant.ChatTable.TABL_NAME + "(" +
                DataConstant.ChatTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataConstant.ChatTable.COLUMN_USER_ID + " INTEGER," +
                DataConstant.ChatTable.COLUMN_TYPE + " INTEGER," +
                DataConstant.ChatTable.COLUMN_MESSAGE + " TEXT," +
                DataConstant.ChatTable.COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final long INVALID_ID = -1;
    public long getChatUserId(long senderid) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DataConstant.ChatUserTable._ID};
        String selection = DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID + " = ?";
        String[] selectionArgs = {"" + senderid};
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (!c.moveToNext()) {
            c.close();
            return INVALID_ID;
        }
        long id = c.getLong(c.getColumnIndex(DataConstant.ChatUserTable._ID));
        c.close();
        return id;
    }

    ContentValues values = new ContentValues();
    public long getUserTableId(User user) {
        long id = getChatUserId(user.mem_no);
        if (id != INVALID_ID) return id;

        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID, user.mem_no);
        values.put(DataConstant.ChatUserTable.COLUMN_NAME, user.mem_name);
        values.put(DataConstant.ChatUserTable.COLUMN_EMAIL, user.mem_id);
        return db.insert(DataConstant.ChatUserTable.TABLE_NAME, null, values);
    }

    public String getLastDate(long serverid) {
        long id = getChatUserId(serverid);
        if (id == INVALID_ID) return null;

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DataConstant.ChatTable.COLUMN_DATE};
        String selection = DataConstant.ChatTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {""+ id};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " DESC";
        String limit = "1";
        Cursor c = db.query(DataConstant.ChatTable.TABL_NAME, columns, selection, selectionArgs, null, null, orderBy, limit);
        if (!c.moveToNext()) {
            c.close();
            return null;
        }
        String date = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_DATE));
        c.close();
        return date;
    }

    public long addChatMessage(long uid, int type, String message, String date) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DataConstant.ChatTable.COLUMN_USER_ID, uid);
        values.put(DataConstant.ChatTable.COLUMN_TYPE, type);
        values.put(DataConstant.ChatTable.COLUMN_MESSAGE, message);
        if (!TextUtils.isEmpty(date)) {
            date = convertDateString(new Date());
        }
        values.put(DataConstant.ChatTable.COLUMN_DATE, date);
        return db.insert(DataConstant.ChatTable.TABL_NAME, null, values);
    }

    public Cursor getChatUserList() {
        String[] columns = {DataConstant.ChatUserTable._ID, DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID, DataConstant.ChatUserTable.COLUMN_NAME, DataConstant.ChatUserTable.COLUMN_EMAIL};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }

    public Cursor getChatList(long userid) {
        String[] columns = {DataConstant.ChatTable._ID, DataConstant.ChatTable.COLUMN_MESSAGE, DataConstant.ChatTable.COLUMN_TYPE, DataConstant.ChatTable.COLUMN_DATE};
        String selection = DataConstant.ChatTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {"" + userid};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DataConstant.ChatTable.TABL_NAME, columns, selection, selectionArgs, null, null, orderBy);
        return c;
    }

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public String convertDateString(Date date) {
        return dateFormat.format(date);
    }
}
