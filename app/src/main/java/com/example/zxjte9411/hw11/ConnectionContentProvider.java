package com.example.zxjte9411.hw11;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ConnectionContentProvider extends ContentProvider {
    public static final String DB_FILE = "contact.db", DB_TABLE = "contact";
    public static final int URI_ROOT = 0;
    public static final int DB_TABLE_connection = 1;
    private static final String AUTHORITY = "com.example.ConnectionContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DB_TABLE);
    private static final UriMatcher sUriMatcher = new UriMatcher(URI_ROOT);
    public static SQLiteDatabase sdb;
    static {
        sUriMatcher.addURI(AUTHORITY, DB_TABLE, DB_TABLE_connection);
    }

    @Override
    public boolean onCreate() {
        DBOpenHelper contactDBOpenHelper = new DBOpenHelper(getContext(), DB_FILE, null, 1);
        sdb = contactDBOpenHelper.getWritableDatabase();

        Cursor cursor = sdb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if(cursor != null) {
            if(cursor.getCount() == 0)	// DB Table not exist, therefore to construct a new one.
                sdb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "phoneNumber TEXT," +
                        "phoneType TEXT);");
            cursor.close();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (sUriMatcher.match(uri) != DB_TABLE_connection) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = sdb.query(true, DB_TABLE, strings, s, null, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (sUriMatcher.match(uri) != DB_TABLE_connection) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        long rowId = sdb.insert(DB_TABLE, null, contentValues);
        Uri insertedRowUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(insertedRowUri, null);
        return insertedRowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return sdb.delete(DB_TABLE, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return sdb.update(DB_TABLE, contentValues, s, strings);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if (sdb.isOpen())
            sdb.close();
    }
}
