package com.kl.tree.treeview.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.kl.tree.treeview.data.DBHelper;
import com.kl.tree.treeview.data.provider.TestContent.src;

import java.io.File;
import java.util.ArrayList;

/**
 * This class was generated by the ContentProviderCodeGenerator project made by Foxykeep
 * <p>
 * (More information available https://github.com/foxykeep/ContentProviderCodeGenerator)
 */
public final class TestProvider extends ContentProvider {

    private static final String LOG_TAG = TestProvider.class.getSimpleName();

    /* package */ static final boolean ACTIVATE_ALL_LOGS = false;

    protected static final String DATABASE_NAME = DBHelper.DB_NAME;

    public static final String AUTHORITY = "com.kl.tree.test.provider";

    static {
        Uri.parse("content://" + AUTHORITY + "/integrityCheck");
    }

    // Version 1 : Creation of the database
    public static final int DATABASE_VERSION = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private enum UriType {
        LEAF(TestContent.leaf.TABLE_NAME, TestContent.leaf.TABLE_NAME, TestContent.leaf.TYPE_ELEM_TYPE),
        LEAF_ID(TestContent.leaf.TABLE_NAME + "/#", TestContent.leaf.TABLE_NAME, TestContent.leaf.TYPE_DIR_TYPE),
        SRC(TestContent.src.TABLE_NAME, TestContent.src.TABLE_NAME, TestContent.src.TYPE_ELEM_TYPE),
        SRC_ID(TestContent.src.TABLE_NAME + "/#", src.TABLE_NAME, TestContent.src.TYPE_DIR_TYPE);

        private String mTableName;
        private String mType;

        UriType(String matchPath, String tableName, String type) {
            mTableName = tableName;
            mType = type;
            sUriMatcher.addURI(AUTHORITY, matchPath, ordinal());
        }

        String getTableName() {
            return mTableName;
        }

        String getType() {
            return mType;
        }
    }

    static {
        // Ensures UriType is initialized
        UriType.values();
    }

    private static UriType matchUri(Uri uri) {
        int match = sUriMatcher.match(uri);
        if (match < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return UriType.class.getEnumConstants()[match];
    }

    private SQLiteDatabase mDatabase;

    @SuppressWarnings("deprecation")
    public synchronized SQLiteDatabase getDatabase(Context context) {
        // Always return the cached database, if we've got one
        if (mDatabase == null || !mDatabase.isOpen()) {
            DatabaseHelper helper = new DatabaseHelper(context, DATABASE_NAME);
            mDatabase = helper.getWritableDatabase();
            if (mDatabase != null) {
                mDatabase.setLockingEnabled(true);
            }
        }

        return mDatabase;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);

            if(!new File(DBHelper.getInstance().getDbPath()).exists()){
                DBHelper.getInstance().formeDb();
            }

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "Creating TestProvider database");

            // Create all tables here; each class has its own method
            //leaf.createTable(db);
            //src.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // Upgrade all tables here; each class has its own method
            TestContent.leaf.upgradeTable(db, oldVersion, newVersion);
            TestContent.src.upgradeTable(db, oldVersion, newVersion);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        String id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "delete: uri=" + uri + ", match is " + uriType.name());
        }

        int result = -1;

        switch (uriType) {
            case LEAF_ID:
            case SRC_ID:
                id = uri.getPathSegments().get(1);
                result = db.delete(uriType.getTableName(), whereWithId(selection),
                        addIdToSelectionArgs(id, selectionArgs));
                break;
            case LEAF:
            case SRC:
                result = db.delete(uriType.getTableName(), selection, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public String getType(Uri uri) {
        return matchUri(uri).getType();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        long id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "insert: uri=" + uri + ", match is " + uriType.name());
        }

        Uri resultUri;

        switch (uriType) {
            case LEAF:
            case SRC:
                id = db.insert(uriType.getTableName(), "foo", values);
                resultUri = id == -1 ? null : ContentUris.withAppendedId(uri, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Notify load the base uri, not the new uri (nobody is watching a new
        // record)
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) 
            throws OperationApplicationException {
        SQLiteDatabase db = getDatabase(getContext());
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
                db.yieldIfContendedSafely();
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "bulkInsert: uri=" + uri + ", match is " + uriType.name());
        }

        int numberInserted = 0;
        SQLiteStatement insertStmt;

        db.beginTransaction();
        try {
            switch (uriType) {
                case LEAF:
                    insertStmt = db.compileStatement(TestContent.leaf.getBulkInsertString());
                    for (ContentValues value : values) {
                        TestContent.leaf.bindValuesInBulkInsert(insertStmt, value);
                        insertStmt.execute();
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }
                    break;

                case SRC:
                    insertStmt = db.compileStatement(TestContent.src.getBulkInsertString());
                    for (ContentValues value : values) {
                        TestContent.src.bindValuesInBulkInsert(insertStmt, value);
                        insertStmt.execute();
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
        } finally {
            db.endTransaction();
        }

        // Notify load the base uri, not the new uri (nobody is watching a new
        // record)
        context.getContentResolver().notifyChange(uri, null);
        return numberInserted;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, 
            String sortOrder) {

        Cursor c = null;
        UriType uriType = matchUri(uri);
        Context context = getContext();
        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        String id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "query: uri=" + uri + ", match is " + uriType.name());
        }

        switch (uriType) {
            case LEAF_ID:
            case SRC_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(uriType.getTableName(), projection, whereWithId(selection),
                        addIdToSelectionArgs(id, selectionArgs), null, null, sortOrder);
                break;
            case LEAF:
            case SRC:
                c = db.query(uriType.getTableName(), projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
        }

        if ((c != null) && !isTemporary()) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    private String whereWithId(String selection) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(BaseColumns._ID);
        sb.append(" = ?");
        if (selection != null) {
            sb.append(" AND (");
            sb.append(selection);
            sb.append(')');
        }
        return sb.toString();
    }

    private String[] addIdToSelectionArgs(String id, String[] selectionArgs) {

        if (selectionArgs == null) {
            return new String[] { id };
        }

        int length = selectionArgs.length;
        String[] newSelectionArgs = new String[length + 1];
        newSelectionArgs[0] = id;
        System.arraycopy(selectionArgs, 0, newSelectionArgs, 1, length);
        return newSelectionArgs;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "update: uri=" + uri + ", match is " + uriType.name());
        }

        int result = -1;

        switch (uriType) {
            case LEAF_ID:
            case SRC_ID:
                String id = uri.getPathSegments().get(1);
                result = db.update(uriType.getTableName(), values, whereWithId(selection),
                    addIdToSelectionArgs(id, selectionArgs));
                break;
            case LEAF:
            case SRC:
                result = db.update(uriType.getTableName(), values, selection, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public boolean onCreate() {
        return true;
    }
}
