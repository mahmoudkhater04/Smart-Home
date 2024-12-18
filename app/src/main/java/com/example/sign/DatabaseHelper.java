package com.example.sign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "UsersDatabase.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE = "image";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHelper instantiated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_IMAGE + " BLOB)";
        db.execSQL(createTable);
        Log.d(TAG, "Database created with table: " + TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String name, String username, String email, String password, byte[] imageBytes) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_IMAGE, imageBytes);

            long result = db.insertOrThrow(TABLE_USERS, null, values);
            if (result == -1) {
                Log.e(TAG, "Failed to insert user: " + username);
                return false;
            }
            Log.d(TAG, "User added successfully: " + username);
            return true;
        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "Constraint violation while adding user: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error while adding user: " + e.getMessage(), e);
            return false;
        } finally {
            if (db != null) {
                db.close();
                Log.d(TAG, "Database connection closed after addUser");
            }
        }
    }

    public boolean isMatch(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
            cursor = db.rawQuery(query, new String[]{username, password});
            boolean exists = cursor.moveToFirst();
            Log.d(TAG, "isMatch result for user " + username + ": " + exists);
            return exists;
        } catch (Exception e) {
            Log.e(TAG, "Error while checking match for user " + username + ": " + e.getMessage(), e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
                Log.d(TAG, "Cursor closed in isMatch for user " + username);
            }
            db.close();
            Log.d(TAG, "Database connection closed after isMatch for user " + username);
        }
    }

    public String[] getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        Log.d(TAG,"cursor created");
        String query = "SELECT * " + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
        cursor = db.rawQuery(query, new String[]{username});
        Log.d(TAG,"query done");
        if(cursor.moveToFirst()) {
            Log.d(TAG,"cursor move to first");
            String[] ret = new String[2];// name,email
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            Log.d(TAG,"name index : " + nameIndex);
            Log.d(TAG,"email index : " + emailIndex);

            ret[0] = cursor.getString(nameIndex);
            ret[1] = cursor.getString(emailIndex);
            Log.d(TAG,"ret created");
            cursor.close();
            db.close();
            return ret;
        }else{
            Log.d(TAG,"user not found");
        }
        return null;
    }

    public byte[] getImageBytes(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT " + COLUMN_IMAGE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
        cursor = db.rawQuery(query, new String[]{username});
        byte[] ret = null;
        if (cursor.moveToFirst()) {
            ret = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
        }
        cursor.close();
        db.close();
        return ret;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
            cursor = db.rawQuery(query, new String[]{username});
//            cursor.getCount();
            boolean exists = cursor.moveToFirst();
            Log.d(TAG, "isUsernameExists for " + username + ": " + exists);
            return exists;
        } catch (Exception e) {
            Log.e(TAG, "Error while checking if username exists: " + e.getMessage(), e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
                Log.d(TAG, "Cursor closed in isUsernameExists for " + username);
            }
            db.close();
            Log.d(TAG, "Database connection closed after isUsernameExists for " + username);
        }
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=?";
            cursor = db.rawQuery(query, new String[]{email});
            boolean exists = cursor.moveToFirst();
            Log.d(TAG, "isEmailExists for " + email + ": " + exists);
            cursor.close();
            return exists;
        } catch (Exception e) {
            Log.e(TAG, "Error while checking if email exists: " + e.getMessage(), e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
                Log.d(TAG, "Cursor closed in isEmailExists for " + email);
            }
            db.close();
            Log.d(TAG, "Database connection closed after isEmailExists for " + email);
        }
    }
}